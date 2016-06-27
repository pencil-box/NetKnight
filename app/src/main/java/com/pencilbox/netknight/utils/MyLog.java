package com.pencilbox.netknight.utils;

import android.util.Log;

/**
 * Created by pencil-box on 16/6/27.
 * 日志记录类
 */
public class MyLog {

    private static final boolean DEBUG=true;

    //直接打印类名信息
    public static void logd(Object o ,String msg){


        if(DEBUG){
            String clazz = o.getClass().getName();

            Log.d(clazz.substring(clazz.lastIndexOf('.')+1),msg);

        }

    }

    public static void loge(Object o ,String msg){

        if(DEBUG){

            String clazz = o.getClass().getName();

            Log.e(clazz.substring(clazz.lastIndexOf('.')+1),msg);


        }
    }

}
