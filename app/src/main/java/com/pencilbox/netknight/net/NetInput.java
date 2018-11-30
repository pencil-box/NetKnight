package com.pencilbox.netknight.net;

import android.util.Log;

import com.pencilbox.netknight.utils.MyLog;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Created by pencil-box on 16/6/30.
 * 监听来自网络的数据,并将相关数据解析丢回给虚拟网卡去处理
 */
public class NetInput extends Thread {
    private final static String TAG = "NetInput";

    //使线程退出
    private volatile boolean mQuit = false;
    private Selector mChannelSelector;
    private BlockingQueue<ByteBuffer> mOutputQueue;

    public NetInput(BlockingQueue<ByteBuffer> queue, Selector channelSelector) {
        mOutputQueue = queue;
        mChannelSelector = channelSelector;
    }

    /**
     * 标志位退出,即使调用interrupt不一定会退出,双重保证
     */
    public void quit() {
        mQuit = true;
        interrupt();

    }

    /**
     * 需要获取实际网络数据,并把相关的数据写入outputQueue
     */
    @Override
    public void run() {
        Log.d(TAG, "NetInput start");
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                Log.d(TAG, "Stop");
                if (mQuit)
                    return;
                continue;
            }

            try {
                int readyChannels = mChannelSelector.select();

//                Log.d(TAG,"完成select");
                if (readyChannels == 0) {
                    continue;
                }

                Set<SelectionKey> selectionKeys = mChannelSelector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    //将ipAndPort从队列中取出来,若不存在,说明该调度已经结束啦
                    String ipAndPort = (String) key.attachment();
                    //直接将
                    TCB tcb = TCBCachePool.getTCB(ipAndPort);
                    if (tcb == null) {
                        //通道关闭咯
                        Log.d(TAG, "channel is closed:" + ipAndPort);
                        key.channel().close();
                        keyIterator.remove();
                        continue;
                    }
                    if (key.isConnectable()) {
                        Log.d(TAG, "channel is connectable");
                        buildConnection(tcb, key);
//                    } else if (key.isAcceptable()) {

                    } else if (key.isReadable()) {
                        //感兴趣是这里才有咯?
                        transData(tcb, key);
//                    } else if (key.isWritable()) {
                    }
                    keyIterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 传输数据咯,写回实际返回数据到虚拟网卡
     */
    private void transData(TCB tcb, SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();

        ByteBuffer responseBuffer = ByteBufferPool.acquire();
        int readBytes;

        //这样实际数据就能写道里面去了
        responseBuffer.position(Packet.IP4_HEADER_SIZE + Packet.TCP_HEADER_SIZE);

        try {
            readBytes = channel.read(responseBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            ByteBufferPool.release(responseBuffer);
            throw new RuntimeException(e);
        }

        synchronized (NetInput.class) {
            Packet responsePacket = tcb.referencePacket;

            MyLog.logd(this, "获取回来的数据大小为" + readBytes);
            if (readBytes == -1) {
                //执行完咯,发送完成的包咯
                key.interestOps(0);//不感兴趣咯
                tcb.tcbStatus = TCB.TCB_STATUS_LAST_ACK;
                responsePacket.updateTCPBuffer(responseBuffer, (byte) (Packet.TCPHeader.FIN | Packet.TCPHeader.ACK), tcb.mySequenceNum, tcb.myAcknowledgementNum, 0);
                tcb.mySequenceNum++;
                mOutputQueue.offer(responseBuffer);
                MyLog.logd(this, "数据读取完毕");
                return;
            }

            tcb.calculateTransBytes(readBytes);
            MyLog.logd(this, "responseBuffer:Before Limit:" + responseBuffer.limit() + " position:" + responseBuffer.position());
            Log.d(TAG, "sequenceNum" + tcb.mySequenceNum);
            MyLog.logd(this, "responseBuffer:After Limit:" + responseBuffer.limit() + " position:" + responseBuffer.position());
            responsePacket.updateTCPBuffer(responseBuffer, (byte) (Packet.TCPHeader.ACK | Packet.TCPHeader.PSH), tcb.mySequenceNum, tcb.myAcknowledgementNum, readBytes);
            tcb.mySequenceNum = tcb.mySequenceNum + readBytes;
            //TODO 这个真让人疑惑
            //之前position之后就不会移动了么,真是神奇~
            responseBuffer.position(Packet.IP4_HEADER_SIZE + Packet.TCP_HEADER_SIZE + readBytes);
        }
        mOutputQueue.offer(responseBuffer);
    }

    /**
     * 非阻塞状态时,实际的channel建立不是立马完成的
     * 虚拟网卡的握手必须等实际channel建立完成
     */
    private void buildConnection(TCB tcb, SelectionKey key) {
        try {
            if (!((SocketChannel) key.channel()).finishConnect()) {
                MyLog.logd(this, "onConnectState 未建立完成");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读数据咯
        key.interestOps(SelectionKey.OP_READ);

        Packet responsePacket = tcb.referencePacket;

        ByteBuffer responseBuffer = ByteBufferPool.acquire();
        responsePacket.updateTCPBuffer(responseBuffer, (byte) (Packet.TCPHeader.SYN | Packet.TCPHeader.ACK), tcb.mySequenceNum, tcb.myAcknowledgementNum, 0);
        //TODO mySequenceNum 并发操作会发生诡异的事情么??

        tcb.tcbStatus = TCB.TCB_STATUS_SYN_RECEIVED;
        tcb.mySequenceNum++;

        mOutputQueue.offer(responseBuffer);

    }

}
