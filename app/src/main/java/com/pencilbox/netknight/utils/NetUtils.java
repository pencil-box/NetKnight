package com.pencilbox.netknight.utils;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pencil-box on 16/6/17.
 * 网络辅助类,临时用来请求网络用的
 */
public class NetUtils {


    private static final String HOST_IP = "192.168.0.105";
    //请求http
    public static void sendHttp(){


        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {


                MyLog.logd(this,"HTTP请求:"+HOST_IP);


                HttpURLConnection connection;
                InputStream in;
                try {
                    URL url = new URL("http://"+HOST_IP+":8080");

                     connection = (HttpURLConnection) url.openConnection();

                    in = new BufferedInputStream(connection.getInputStream());

                    while(in.read()!=-1){

                        System.out.print(in.read());
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();

    }

}
