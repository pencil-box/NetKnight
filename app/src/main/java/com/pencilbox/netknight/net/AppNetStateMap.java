package com.pencilbox.netknight.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pencil-box on 16/7/9.
 * key = appId
 * value = net_access_type
 * 全局变量,包括wifi和mobile的流量的
 */
public class AppNetStateMap {


    private static Map<Long,Integer> mMobileMap;
    private static Map<Long,Integer> mWifiMap;



    static {
        mMobileMap = new HashMap<>();
        mWifiMap = new HashMap<>();
    }

    public static void putWifi(long appId,int accessState){

        mWifiMap.put(appId,accessState);
    }


    public static void putMobile(long appId,int accessState){
        mMobileMap.put(appId,accessState);
    }
    public static int getWifi(long appId){
        return mWifiMap.get(appId);
    }
    public static int getMobile(long appId){
        return mMobileMap.get(appId);
    }

    public static void removeMobile(long appId){
        mMobileMap.remove(appId);
    }
    public static void removeWifi(long appId){
        mWifiMap.remove(appId);
    }

}
