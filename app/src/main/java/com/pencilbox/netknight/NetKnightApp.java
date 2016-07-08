package com.pencilbox.netknight;

import android.app.Application;
import android.content.Context;

/**
 * Created by pencil-box on 16/7/8.
 */
public class NetKnightApp extends Application {


    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }


}
