package com.pencilbox.netknight.net;

import android.net.VpnService;
import android.util.Log;

import com.pencilbox.netknight.Constants;
import com.pencilbox.netknight.model.App;
import com.pencilbox.netknight.model.BlockIp;
import com.pencilbox.netknight.model.BlockName;
import com.pencilbox.netknight.receiver.NetChangeReceiver;
import com.pencilbox.netknight.utils.MyLog;
import com.pencilbox.netknight.utils.NetUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by pencil-box on 16/6/30.
 * 发起实际的网络请求数据,tcp通过socketServer实现,udp待完成
 * 需要处理获取的虚拟网卡数据
 */
public class NetOutput extends Thread {


    private final static String TAG = "NetOutput";


    private volatile boolean mQuit = false;

    //从队列中读到的队列数据
    private BlockingQueue<Packet> mInputQueue;
    private BlockingQueue<ByteBuffer> mOutputQueue;


    private BlockingQueue<App> mAppCacheQueue;

    //这个是用来监视多个channel状态,即实际的网络访问信息
    private Selector mChannelSelector;


    private VpnService mVpnService;

    public NetOutput(BlockingQueue<Packet> inputQueue, BlockingQueue<ByteBuffer> outputQueue, VpnService vpnService, Selector selector, BlockingQueue<App> appCacheQueue) {
        mOutputQueue = outputQueue;
        mInputQueue = inputQueue;

        mVpnService = vpnService;
        mChannelSelector = selector;
        mAppCacheQueue = appCacheQueue;
    }


    public void quit() {
        mQuit = true;
        interrupt();
    }

    //采用NIO同步非阻塞模型

    /**
     * 需要获取相关的读到的数据,并做出数据反馈,还有就是outputQueue进行
     */
    @Override
    public void run() {

        Log.d(TAG, "start~");
        Packet currentPacket;
        while (true) {

            try {

                //阻塞等到有数据就处理
                currentPacket = mInputQueue.poll();
                Thread.sleep(15);

                if (currentPacket == null) {
                    continue;
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "Stop");

                if (mQuit)
                    return;
                continue;
            }


            //实际要传的数据哟
            ByteBuffer payloadBuffer = currentPacket.backingBuffer;
            //payloadBuffer 其实也是可复用的吧
            currentPacket.backingBuffer = null;
            ByteBuffer responseBuffer = ByteBufferPool.acquire();

            //重要的信息记录下来咯
            InetAddress desAddress = currentPacket.ip4Header.destinationAddress;

            int sourcePort = currentPacket.tcpHeader.sourcePort;
            int desPort = currentPacket.tcpHeader.destinationPort;


            String ipAndPort = desAddress.getHostAddress() + ":" + sourcePort + ":" + desPort;


            TCB tcb = TCBCachePool.getTCB(ipAndPort);

            if (tcb == null) {

                initTCB(ipAndPort, currentPacket, desAddress, desPort, responseBuffer);
            } else if (currentPacket.tcpHeader.isSYN()) {
                Log.d(TAG, "重复的SYN");

                dealDuplicatedSYN(tcb, ipAndPort, currentPacket, responseBuffer);

            } else if (currentPacket.tcpHeader.isFIN()) {
                Log.d(TAG, "---FIN---");

                //结束这条连接咯
                finishConnect(tcb, ipAndPort, currentPacket, responseBuffer);

                continue;
            } else if (currentPacket.tcpHeader.isACK()) {
                Log.d(TAG, "---ACK---");

                //传递数据咯,一般数据是带ACK的
                transData(ipAndPort, tcb, currentPacket, payloadBuffer, responseBuffer);
            }

            //复用传进来的ByteBuffer
            ByteBufferPool.release(payloadBuffer);

            Log.d(TAG, "tcpOutput执行一遍");


        }


    }


    //连接重置咯,即断开之前的连接
    private void sendRST(TCB tcb, String ipAndPort, int prevPayloadSize, ByteBuffer buffer) {
        Log.d(TAG, "sendRST");
        tcb.referencePacket.updateTCPBuffer(buffer, (byte) Packet.TCPHeader.RST, 0, tcb.myAcknowledgementNum + prevPayloadSize, 0);

        mOutputQueue.offer(buffer);
        TCBCachePool.closeTCB(ipAndPort);
    }

    /**
     * 处理冗余的SYN
     */
    private void dealDuplicatedSYN(TCB tcb, String ipAndPort, Packet currentPacket, ByteBuffer responseBuffer) {
        synchronized (tcb) {
            if (tcb.tcbStatus == TCB.TCB_STATUS_SYN_SENT) {

                //如果是SYN发送的状态,即还在与远程服务器建立连接ing..
                tcb.myAcknowledgementNum = currentPacket.tcpHeader.sequenceNumber + 1;
                return;
            }
        }

        sendRST(tcb, ipAndPort, 1, responseBuffer);

    }

    /**
     * 关闭连接咯
     *
     * @param ipAndPort
     * @param currentPacket
     * @param responseBuffer
     */
    private void finishConnect(TCB tcb, String ipAndPort, Packet currentPacket, ByteBuffer responseBuffer) {

        synchronized (tcb) {

            //标识已经被改变咯
            tcb.myAcknowledgementNum = currentPacket.tcpHeader.sequenceNumber + 1;

            tcb.tcbStatus = TCB.TCB_STATUS_LAST_ACK;
            currentPacket.updateTCPBuffer(responseBuffer, (byte) (Packet.TCPHeader.FIN | Packet.TCPHeader.ACK),
                    tcb.mySequenceNum, tcb.myAcknowledgementNum, 0);
            tcb.mySequenceNum++;
            // FIN counts as a byte
        }
        TCBCachePool.closeTCB(ipAndPort);


        mOutputQueue.offer(responseBuffer);

    }

    /**
     * 传递实际的数据
     *
     * @param tcb
     */
    private void transData(String ipAndPort, TCB tcb, Packet currentPacket, ByteBuffer dataBuffer, ByteBuffer responseBuffer) {

        //1.发送ACK码 2.传递真实数据

        int payloadSize = dataBuffer.limit() - dataBuffer.position();

        //对tcb加锁,防止其有变动
        synchronized (tcb) {


            if (tcb.tcbStatus == TCB.TCB_STATUS_LAST_ACK) {
                //关闭通道
                MyLog.logd(this, "close channel");
                TCBCachePool.closeTCB(ipAndPort);
                return;
            }


            //无数据的直接ignore了
            if (payloadSize == 0) {

                MyLog.logd(this, "-------ack has no data-------");
                return;
            }


            MyLog.logd(this, "传递的payloadSize为:" + payloadSize);

            //发送完数据咯,那么就执行真正的数据访问

            SelectionKey outKey = tcb.selectionKey;
            if (outKey == null) {
                MyLog.logd(this, "outKey 为 null");
                return;
            }

            //监听读的状态咯
            if (tcb.tcbStatus == TCB.TCB_STATUS_SYN_RECEIVED) {
                tcb.tcbStatus = TCB.TCB_STATUS_ESTABLISHED;

            } else if (tcb.tcbStatus == TCB.TCB_STATUS_ESTABLISHED) {

                MyLog.logd(this, "establish ing");


            } else {

                MyLog.logd(this, "当前tcbStatus为" + tcb.tcbStatus);
                MyLog.logd(this, "连接还没建立好");
                return;
            }

            SocketChannel outChannel = (SocketChannel) outKey.channel();

            if (outChannel.isConnected() && outChannel.isOpen()) {
                MyLog.logd(this, "执行写channel操作");
                try {
                    while (dataBuffer.hasRemaining()) {

                        outChannel.write(dataBuffer);
                    }

                    //记录发送数据
                    tcb.calculateTransBytes(payloadSize);

                } catch (IOException e) {
                    e.printStackTrace();
                    MyLog.logd(this, "write data error");
                    //失败就告知连接中断
                    sendRST(tcb, ipAndPort, payloadSize, responseBuffer);

                }

            } else {
                MyLog.logd(this, "channel都没准备好");
            }

            currentPacket.swapSourceAndDestination();

            tcb.myAcknowledgementNum = currentPacket.tcpHeader.sequenceNumber + payloadSize;
            currentPacket.updateTCPBuffer(responseBuffer, (byte) Packet.TCPHeader.ACK, tcb.mySequenceNum, tcb.myAcknowledgementNum, 0);

            MyLog.logd(this, "transData responseBuffer limit:" + responseBuffer.limit() + " position:" + responseBuffer.position());

        }

        //ack码
        mOutputQueue.offer(responseBuffer);


    }

    /**
     * 初始化TCB以及相关连接
     */
    private void initTCB(String ipAndPort, Packet referencePacket, InetAddress desAddress, int desPort, ByteBuffer responseBuffer) {


        Log.d(TAG, "initTcb ...");

        //TODO 找到对应的uid咯

        long passAppId = filterPacket(referencePacket);
        if (passAppId == -1) {
            //TODO 主动断开连接,拒绝访问咯

            return;
        }

//        //TODO 抓包咯,这样很不妥呀,还要根据相关信息构建包orz
        ByteBuffer sourceBuffer = ByteBufferPool.acquire();
        referencePacket.updateTCPBuffer(sourceBuffer, (byte) Packet.TCPHeader.SYN, referencePacket.tcpHeader.sequenceNumber,
                referencePacket.tcpHeader.acknowledgementNumber, 0);

        ByteBufferPool.release(sourceBuffer);


        referencePacket.swapSourceAndDestination();

        TCB tcb = new TCB(ipAndPort, new Random().nextInt(Short.MAX_VALUE), referencePacket.tcpHeader.sequenceNumber,
                referencePacket.tcpHeader.sequenceNumber + 1, referencePacket.tcpHeader.acknowledgementNumber, referencePacket);

        //设置appId,方便记录流量包
        tcb.setAppId(passAppId);


        //存储起来先orz
        TCBCachePool.putTCB(ipAndPort, tcb);

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            //保护本应用建立的通道,防止死循环
            mVpnService.protect(socketChannel.socket());
            socketChannel.connect(new InetSocketAddress(desAddress, desPort));


            //非阻塞状态连接需要判断是否连接成功
            if (socketChannel.finishConnect()) {
                //连接成功,即已经实现握手了,此时虚拟网卡这边也要进行握手


                //速度很快,都不用监听就完成了
                Log.d(TAG, "socketChannel连接完成咯");
                tcb.tcbStatus = TCB.TCB_STATUS_SYN_RECEIVED;

                referencePacket.updateTCPBuffer(responseBuffer, (byte) (Packet.TCPHeader.SYN | Packet.TCPHeader.ACK), tcb.mySequenceNum, tcb.myAcknowledgementNum, 0);
                tcb.mySequenceNum++;
                //TODO 不管it
                mChannelSelector.wakeup();
                tcb.selectionKey = socketChannel.register(mChannelSelector, SelectionKey.OP_READ, ipAndPort);


            } else {
                //正在连接,发送了SYN

                tcb.tcbStatus = TCB.TCB_STATUS_SYN_SENT;
                mChannelSelector.wakeup();
                SelectionKey key = socketChannel.register(mChannelSelector, SelectionKey.OP_CONNECT, ipAndPort);
//TODO 将selectionKey存储起来咯
                tcb.selectionKey = key;

                Log.d(TAG, "socketChannel 注册ing");


                ByteBufferPool.release(responseBuffer);
                return;
                //还在连接咯,还没连接成功

            }


        } catch (IOException e) {
            e.printStackTrace();

            ByteBufferPool.release(responseBuffer);
            throw new RuntimeException(e);
        }

        mOutputQueue.offer(responseBuffer);

    }

    /**
     * 过滤相关的包,返回的为appId,如果为-1,则说明禁止访问
     * ip,domain name
     * uid
     */

    private long filterPacket(Packet transPacket) {


        //TODO 根据域名拦截

        if (BlockingPool.isBlockName) {

            if (filterByDomain(transPacket.ip4Header.destinationAddress.getHostName())) {
                Log.e(TAG, "数据包因为domainName段被拦截了");
                return -1;
            }

        }


        //TODO 根据ip段拦截
        if (BlockingPool.isBlockIp) {
            if (filterByIp(transPacket.ip4Header.destinationAddress.getHostAddress())) {
                Log.e(TAG, "数据包因为IP段被拦截了");
                return -1;
            }
        }

        int uid = NetUtils.readProcFile(transPacket.tcpHeader.sourcePort);
        MyLog.logd(this, "uid为:" + uid);

        if (uid < 10000) {

            Log.e(TAG, "连接失败");
//            sendRST();
            return -1;
        }
        //过滤暂且没做呢


        //TODO 通过UID查找appId

        List<App> appList = org.litepal.LitePal.where("uid = ?", String.valueOf(uid)).find(App.class);

        if (appList.size() != 1) {
            return -1;
        }
        Log.d(TAG, "app id 号为:" + appList.get(0).getId());


        //TODO 根据类型信息进行拦截


        boolean isPass = filterByAppSetting(appList.get(0));
        if (!isPass) {
            return -1;
        }


//        String name =   AppUtils.getPackageNameByUid(NetKnightApp.getContext(),uid);

//        Log.e(TAG,"包名为:"+name+" &&Uid:" +uid);

        return appList.get(0).getId();
    }


    /**
     * 根据app的设置信息进行拦截
     *
     * @param app
     * @return
     */
    private boolean filterByAppSetting(App app) {


        if (NetChangeReceiver.sNetState == NetChangeReceiver.NET_STATE_MOBILE) {


            switch (app.getMobileDataType()) {

                case Constants.ACCESS_TYPE_ALLOW:


                    return true;
                case Constants.ACCESS_TYPE_DISALLOW:

                    return false;
                case Constants.ACCESS_TYPE_DISALLOW_BACK:

                    //TODO 如何判断手机待机呢
                    break;
                case Constants.ACCESS_TYPE_REMIND:

                    //通知有网络访问信息咯

                    mAppCacheQueue.offer(app);

                    return false;
                default:
                    break;

            }


        }
        if (NetChangeReceiver.sNetState == NetChangeReceiver.NET_STATE_WIFI) {

            switch (app.getWifiType()) {

                case Constants.ACCESS_TYPE_ALLOW:


                    return true;
                case Constants.ACCESS_TYPE_DISALLOW:

                    return false;
                case Constants.ACCESS_TYPE_DISALLOW_BACK:

                    //TODO 如何判断手机待机呢
                    break;
                case Constants.ACCESS_TYPE_REMIND:

                    //通知有网络访问信息咯
                    mAppCacheQueue.offer(app);

                    return false;
                default:
                    break;

            }


        }


        return false;
    }


    /**
     * 域名信息
     *
     * @param domainName
     * @return true 拦截成功
     */
    private boolean filterByDomain(String domainName) {

        Log.d(TAG, "--------filterByDomain---------");
        Log.d(TAG, "DomainName:" + domainName);

        ArrayList<BlockName> blockNames = BlockingPool.getNameList();
        for (int i = 0; i < blockNames.size(); i++) {

            if (blockNames.get(i).getcName().startsWith(domainName)) {

                return true;
            }

        }


        return false;
    }

    /**
     * 过滤Ip信息
     * 遍历阻断Ip信息,并查看是否在集合内
     *
     * @param hostAddress xx.xx.xx.xx的结构
     * @return true为已经过滤掉
     * false为不用被过滤
     */
    private boolean filterByIp(String hostAddress) {
        String[] ip = hostAddress.split("\\.");

        Log.d(TAG, "------filterByIp---------");
        Log.d(TAG, "hostAddress:" + hostAddress);

        ArrayList<BlockIp> blockIps = BlockingPool.getIpList();

        for (int i = 0; i < blockIps.size(); i++) {
            BlockIp blockIp = blockIps.get(i);

            String[] beginIp = blockIp.getOriginIp().split("\\.");
            String[] endIp = blockIp.getEndIp().split("\\.");


            if (matchBlockIp(ip, beginIp, endIp)) {


                return true;
            }

        }

        return false;

    }

    /**
     * 看是否匹配,匹配返回true
     *
     * @param ip
     * @param beginIp
     * @param endIp
     * @return
     */
    private boolean matchBlockIp(String[] ip, String[] beginIp, String[] endIp) {

        int diffIndex = findDiffIndex(beginIp, endIp);

        for (int i = 0; i < diffIndex; i++) {

            if (!ip[i].equals(beginIp[i])) {
                return false;
            }

        }

        return Integer.parseInt(ip[diffIndex]) > Integer.parseInt(beginIp[diffIndex])
                && Integer.parseInt(ip[diffIndex]) < Integer.parseInt(endIp[diffIndex]);
    }

    //查找第几个不同的位,方便高位匹配
    private int findDiffIndex(String[] beginIp, String[] endIp) {

        for (int i = 0; i < 3; i++) {
            if (!beginIp[i].equals(endIp[i])) {
                return i;
            }
        }
        return 3;
    }
}
