package com.pencilbox.netknight.service;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;

import com.pencilbox.netknight.net.ByteBufferPool;
import com.pencilbox.netknight.net.NetInput;
import com.pencilbox.netknight.net.NetOutput;
import com.pencilbox.netknight.net.Packet;
import com.pencilbox.netknight.net.TCBCachePool;
import com.pencilbox.netknight.utils.MyLog;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by pencil-box on 16/6/17.
 * Vpnservice 核心类
 */
public class NetKnightService extends VpnService implements Runnable {

    //VPN转发的IP地址咯
    public static String  VPN_ADDRESS = "10.1.10.1";


    //从虚拟网卡拿到的文件描述符
    private ParcelFileDescriptor mInterface;

    //来自应用的请求的数据包
    private LinkedBlockingQueue<Packet> mInputQueue;

    //即将发送至应用的数据包
    private LinkedBlockingQueue<ByteBuffer> mOutputQueue;

    //网络输入输出
    private NetInput mNetInput;
    private NetOutput mNetOutput;

    private Selector mChannelSelector;


    public static volatile boolean isRunning = false;
    public static volatile boolean isCalledByUser = false;


    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.logd(this, "onCreate");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MyLog.logd(this,"onStartCommand");
        Builder builder = new Builder();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            try {
//                mInterface = builder.setSession("NetKnight").setBlocking(false)
//                        .addAddress(VPN_ADDRESS, 32).addAllowedApplication("com.devjiang").addRoute("0.0.0.0", 0).establish();
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mInterface = builder.setSession("NetKnight").setBlocking(false).addAddress(VPN_ADDRESS,32).addRoute("0.0.0.0",0).establish();
        }
        try {
            mChannelSelector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }




        mInputQueue = new LinkedBlockingQueue<>();
        mOutputQueue = new LinkedBlockingQueue<>();

        mNetInput = new NetInput(mOutputQueue, mChannelSelector);
        //这个传参要不要等init好再传呢
        mNetOutput = new NetOutput(mInputQueue, mOutputQueue, this, mChannelSelector);


        //还是直接start呢?
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        executorService.execute(mNetInput);
//        executorService.execute(mNetOutput);
//        executorService.execute(this);

//        MyLog.logd(this,fd.toString());

        mNetOutput.start();
        mNetInput.start();
        new Thread(this).start();

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void run() {


        MyLog.logd(this, "start");
        isRunning = true;


        FileChannel vpnInput = new FileInputStream(mInterface.getFileDescriptor()).getChannel();
        FileChannel vpnOutput = new FileOutputStream(mInterface.getFileDescriptor()).getChannel();

        ByteBuffer buffer4Net;
        ByteBuffer buffer2Net = null;
        boolean isDataSend = true;

//        int sleepTime = 1;

        try {

            while (true) {



                if(!isRunning){
                    Log.d("NetKnight","isRunning is false");
                    if(isCalledByUser){

//                        mHandler.sendEmptyMessage(MSG_STOP_VPN_SERVICE);

                        close();
                        Log.d("NetKnight","stopSelf");
                    }

                    break;
                }


                //数据发送出去了,就get 新的咯
                if(isDataSend ) {
                    buffer2Net = ByteBufferPool.acquire();
                }else {
                    //未有数据发送,据清空咯
                    buffer2Net.clear();
                }
                int inputSize = vpnInput.read(buffer2Net);

                if (inputSize > 0) {
                    MyLog.logd(this, "-----readData:-------size:" + inputSize);
                    //flip切换状态,由写状态转换成可读状态
                    buffer2Net.flip();

                    //从应用中发送的包
                    Packet packet2net = new Packet(buffer2Net);
                    MyLog.logd(this, "--------data read----------size:" + packet2net.getPayloadSize());
                    MyLog.logd(this, packet2net.toString());

                    if (packet2net.isTCP()) {
                        //目前支持TCP
                        mInputQueue.offer(packet2net);
                        isDataSend = true;
                    }else{
                        MyLog.logd(this,"暂时不支持其他类型数据!!");
                        isDataSend = false;
                    }
                }else{
                    //与其release 还不如直接复用
                    isDataSend = false;
//                    ByteBufferPool.release(buffer2Net);

                }


                //将数据返回到应用中
                buffer4Net = mOutputQueue.poll();
                if (buffer4Net != null) {

                    //将limit=position position = 0 开始读操作
                    buffer4Net.flip();



                    while (buffer4Net.hasRemaining()) {
                        vpnOutput.write(buffer4Net);
                    }
                    ByteBufferPool.release(buffer4Net);
                }


                //可减少内存抖动??
                if(!isDataSend) {
                    Thread.sleep(15);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {


            try {
                vpnInput.close();
                vpnOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    /**
     * 关闭相关资源
     */
    public void close() {


        isRunning = false;
        mNetInput.quit();
        mNetOutput.quit();



        try {
            mChannelSelector.close();
            mInterface.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        MyLog.logd(this,"等待线程结束..");
        TCBCachePool.closeAll();

        mInputQueue = null;
        mOutputQueue = null;
        ByteBufferPool.clear();
    }

    //TODO 应该广播给activity调整那个item键的呢
    @Override
    public void onDestroy() {
        super.onDestroy();
        close();

        MyLog.logd(this, "onDestroy");
    }




}
