package com.pencilbox.netknight;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by pencil-box on 16/7/8.
 */
public class NetKnightApp extends LitePalApplication {


    private static Context mContext;



    @Override
    public void onCreate() {
        initialize(this);
        super.onCreate();
        mContext = this;


    }

    public static Context getContext(){
        return mContext;
    }


}
