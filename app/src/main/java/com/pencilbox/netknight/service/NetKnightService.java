package com.pencilbox.netknight.service;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.AsyncTask;
import android.os.Build;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ParcelFileDescriptor;

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

        DatagramChannel channel = null;

        FileInputStream inputStream = new FileInputStream(mInterface.getFileDescriptor());


        FileChannel vpnInput = new FileInputStream(mInterface.getFileDescriptor()).getChannel();
        FileChannel vpnOutput = new FileOutputStream(mInterface.getFileDescriptor()).getChannel();

        FileInputStream fileInputStream = new FileInputStream(mInterface.getFileDescriptor());
        FileOutputStream fileOutputStream = new FileOutputStream(mInterface.getFileDescriptor());

        int bufferSize = 16384;


        //TODO 这里不能直接通过inputStream流读写是为何呢
        ByteBuffer buffer2Net = null;

        buffer2Net = ByteBuffer.allocateDirect(bufferSize);

        ByteBuffer buffer4Net = null;
        int i = 0;

        while (true) {


            try {



                int inputSize = vpnInput.read(buffer2Net);
                MyLog.logd(this, "-----readData:-------size:" + inputSize);


                if(inputSize>0) {
                    byte[] data = new byte[bufferSize];
                    fileInputStream.read(data);
                    MyLog.logd(this, "Soc:IP :" + new Byte(data[12]).intValue()+"."+(int)data[13]+"."+data[14]+"."+data[15]);


                    MyLog.logd(this, "De:IP :" + new Byte(data[16]).intValue()+"."+(int)data[17]+"."+data[18]+"."+data[19]);

//                    if(i==0) {
                    if(data[0]!=0) {

                        data[12] = -64;
                        data[13] = -88;
                        data[14]= 3;
                        data[15] = 66;

                        fileOutputStream.write(data);
                        MyLog.logd(this, "data has been writen");
                        i++;
//                    }
                    }

                }

//                if(inputSize>0&&buffer2Net!=null){
//
//            //flip是做啥子的呢
//                    buffer2Net.flip();
//                    MyLog.logd(this,"--------data read----------");
//
////                    Packet packet = Packet(buffer2Net);
//                    byte data = buffer2Net.get();
//
//                    MyLog.logd(this,"test 12:"+EncodeUtils.byte2bits((byte) 8));
//
//                    MyLog.logd(this,"data :"+EncodeUtils.byte2bits(data));
//                    MyLog.logd(this,"data1:"+EncodeUtils.byte2bits(buffer2Net.get()));
//                    MyLog.logd(this,"data2:"+EncodeUtils.byte2bits(buffer2Net.get()));
//                    MyLog.logd(this,"data3:"+EncodeUtils.byte2bits(buffer2Net.get()));
//
//
//                }


                ExecutorService executorService;

                ConcurrentLinkedQueue<String> a;

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
