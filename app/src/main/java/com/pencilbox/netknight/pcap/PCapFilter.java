package com.pencilbox.netknight.pcap;

import android.util.Log;

import com.pencilbox.netknight.model.App;
import com.pencilbox.netknight.model.PcapFile;
import com.pencilbox.netknight.service.NetKnightService;
import com.pencilbox.netknight.utils.SDCardUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pencil-box on 16/7/10.
 */
public class PCapFilter {


//    private static volatile boolean isPcap = false;

    //当appId为0时,全部数据都要抓取
    public static volatile long mCapAppId = -1;

    private static final Object lock = 1;

    //实施抓包咯,并设置appId,appId为0时,全部都要抓包
    public static boolean startCapPacket(long appId) {

        if(!NetKnightService.isRunning ){
            return false;
        }

//        isPcap = true;
        sPcapList = new ArrayList<>();
        mCapAppId = appId;
        return true;
    }

    /**
     * 停止抓包咯
     */
    public static String stopCapPacket() {


//        mCapAppId = -1;
        String dirName =  SDCardUtils.getSDCardPath()+"NetKnight/";

        String filePath = null;
        Log.d("PCapFilter","fileName is "+dirName);

        try {

            File file = new File(dirName);
            if(!file.exists()){
                file.mkdir();
            }

            File writeFile ;
            String time = (System.currentTimeMillis()+"").substring(5);

            if(mCapAppId == 0) {
                filePath =dirName+"NetKnight_"+time+".pcap";
                writeFile = new File(filePath);
            }else{

                App app = DataSupport.find(App.class,mCapAppId);
                filePath = dirName + app.getName() +"_"+time+".pcap";
                writeFile = new File(filePath);
            }



            mCapAppId = -1;


            writeFile.createNewFile();

            PCapFileWriter pcapWriter = new PCapFileWriter(writeFile);

            for(int i = 0;i<sPcapList.size();i++){
//                pcapWriter.addPacket(sPcapList.get(i).getData(),sPcapList.get(i).getRecordingTime());
                pcapWriter.addPacket(sPcapList.get(i).getData());

            }


            pcapWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            sPcapList.clear();
            sPcapList = null;
            return filePath;
        }


        //进行实际的写数据库操作

    }

    private static List<PcapFile> sPcapList;

    public static void filterPacket(ByteBuffer byteBuffer, long appId) {



        if (mCapAppId == -1) {
            return;
        }
        if (mCapAppId == 0||mCapAppId==appId) {

                Log.d("PCapFilter", "执行包过滤");

            synchronized (lock){
                PcapFile pcapFile = new PcapFile();


                Log.d("PCapFilter", "Before limit:" + byteBuffer.limit() + " position:" + byteBuffer.position());

                byteBuffer.flip();
                byte[] data = new byte[byteBuffer.limit()];
                byteBuffer.get(data);

                Log.d("PCapFilter", "After flip and get" + byteBuffer.limit() + " position" + byteBuffer.position());

                byteBuffer.limit(byteBuffer.capacity());


                Log.d("PCapFilter", "Finish flip" + byteBuffer.limit() + " position" + byteBuffer.position());

                pcapFile.setData(data);

                pcapFile.setRecordingTime(System.currentTimeMillis());
                sPcapList.add(pcapFile);
        }

    }


}}
