package com.pencilbox.netknight.utils;

import android.util.Log;

/**
 * Created by pencil-box on 16/6/17.
 */
public class MyLog {


    private static final boolean DEBUG=true;

    public static void logd(Object o ,String msg){

        String clazz = o.getClass().getName();

        if(DEBUG){
            Log.d(clazz.substring(clazz.lastIndexOf('.')+1),msg);

        }



    }

}
