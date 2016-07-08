package com.pencilbox.netknight.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.pencilbox.netknight.service.NetKnightService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pencil-box on 16/6/17.
 * 网络辅助类,临时用来请求网络用的
 */
public class NetUtils {



    /**
     * 读/proc/net/tcp6的数据咯
     * 根据指定的port,映射找到对应的uid
     */
    public static int readProcFile(int packetPort) {


        File readFile =  new File("/proc/net/tcp6");
        if(!readFile.exists()){
            Log.d("NetUtils","文件不存在");
            return -1;
        }


        int uid = -1;

        try {
            FileInputStream inputStream = new FileInputStream(readFile);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //ignore the first line
            String line = bufferedReader.readLine();


            while((line=bufferedReader.readLine())!=null){
//                System.out.println(line);
                uid= dealLine(line,packetPort);
                if(uid!=-1){
                    break;
                }
            }

            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return uid;
        }


    }

    /**
     * 处理某一行的数据
     * @param line
     */
    private static int  dealLine(String line,int reqPort){


//        return -1;
        String[] strs = line.split(" ");

        int localAddrIndex = 0 ;
        int i ;
        for(i=0;i<strs.length;i++){

            if(!strs[i].equals("")){
                localAddrIndex = i+1;
                break;
            }
        }

        //去除0000 0000 0000 0000 FFFF 0000这段 获取IP地址咯
        String[] localAddr = strs[localAddrIndex].substring(24).split(":");

        String ip = EncodeUtils.hex2int(localAddr[0].substring(6))+"."
                +EncodeUtils.hex2int(localAddr[0].substring(4,6))+"."
                +EncodeUtils.hex2int(localAddr[0].substring(2,4))+"."
                +EncodeUtils.hex2int(localAddr[0].substring(0,2));

        if(!ip.equals(NetKnightService.VPN_ADDRESS)){

            return -1;
        }


        int port = EncodeUtils.hex2int(localAddr[1]);

        if(port!=reqPort){
            return -1;
        }

        int uidIndex = localAddrIndex+6;

        //忽略空格咯
        if(strs[uidIndex].equals("")) {

            //那行数据有空格,怪我咯
            for (i = uidIndex; i < strs.length; i++) {

                if (!strs[i].equals("")) {
                    uidIndex = i;
                    break;
                }
            }
        }
//        System.out.println("切割的数据为:"+strs[localAddrIndex]+" uid:"+strs[uidIndex]);

        System.out.println("Ip:"+ip+ " Port:"+port+" Uid:"+strs[uidIndex]);

//        EncodeUtils.int2hex(1);
        return Integer.parseInt(strs[uidIndex]);
    }

}
