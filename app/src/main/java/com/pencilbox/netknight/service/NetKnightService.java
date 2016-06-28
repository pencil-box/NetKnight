package com.pencilbox.netknight.service;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.AsyncTask;
import android.os.Build;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ParcelFileDescriptor;

import com.pencilbox.netknight.net.ByteBufferPool;
import com.pencilbox.netknight.net.Packet;
import com.pencilbox.netknight.utils.EncodeUtils;
import com.pencilbox.netknight.utils.FileUtils;
import com.pencilbox.netknight.utils.MyLog;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by pencil-box on 16/6/17.
 * Vpnservice 核心类
 */
public class NetKnightService extends VpnService implements Runnable {


    private ParcelFileDescriptor mInterface;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Builder builder = new Builder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                mInterface = builder.setSession("NetKnight").setMtu(1500).setBlocking(false)
                        .addAddress("10.1.10.1", 32).addAllowedApplication("com.devjiang").addRoute("0.0.0.0", 0).establish();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
//        MyLog.logd(this,fd.toString());

        new Thread(this).start();


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void run() {


        System.out.println("线程动起来了");




        FileChannel vpnInput = new FileInputStream(mInterface.getFileDescriptor()).getChannel();
        FileChannel vpnOutput = new FileOutputStream(mInterface.getFileDescriptor()).getChannel();





        ByteBuffer buffer4Net = null;

        while (true) {
            ByteBuffer buffer2Net = ByteBufferPool.acquire();


            try {

                int inputSize = vpnInput.read(buffer2Net);
                MyLog.logd(this, "-----readData:-------size:" + inputSize);

                if(inputSize>0){

                    //flip是做啥子的呢
                    buffer2Net.flip();

                    //从应用中发送的包
                    Packet packet2net = new Packet(buffer2Net);
                    MyLog.logd(this,"--------data read----------");
                    MyLog.logd(this,packet2net.toString());


                    //TODO 执行握手咯

                    if(packet2net.isTCP()&&packet2net.tcpHeader.isSYN()){
                        packet2net.swapSourceAndDestination();
                        ByteBuffer bufferFromNet = ByteBufferPool.acquire();
//                        Packet packet4net = new Packet();

                        packet2net.updateTcpBufferByItself(bufferFromNet);

                        MyLog.logd(this,"--------data write-------\n"+packet2net.toString());



                    }




//                    Packet packet = Packet(buffer2Net);
//                    byte data = buffer2Net.get();

//                    MyLog.logd(this,"test 12:"+EncodeUtils.byte2bits((byte) 8));
//
//                    MyLog.logd(this,"data :"+EncodeUtils.byte2bits(data));
//                    MyLog.logd(this,"data1:"+EncodeUtils.byte2bits(buffer2Net.get()));
//                    MyLog.logd(this,"data2:"+EncodeUtils.byte2bits(buffer2Net.get()));
//                    MyLog.logd(this,"data3:"+EncodeUtils.byte2bits(buffer2Net.get()));


                }


                } catch (IOException e) {
                e.printStackTrace();
            }







//            try {
//                channel = DatagramChannel.open();
//                InetSocketAddress server = new InetSocketAddress("10.8.0.1",8082);
//
//
//                if (!protect(channel.socket())) {
//                    throw new IllegalStateException("Cannot protect the tunnel");
//                }
//
//                channel.connect(server);
//
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


//           StringBuffer buffer =  FileUtils.stream2sb(inputStream,"US-ASCII");
//            System.out.println("Out:"+buffer.toString());


//            ByteBuffer packet = ByteBuffer.allocate(32767);
//
//            try {
//                int length = inputStream.read(packet.array());
//
//                MyLog.logd(this,"length:"+length);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
