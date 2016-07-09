package com.pencilbox.netknight.net;


import android.text.TextUtils;
import android.util.Log;

import com.pencilbox.netknight.model.Traffic;
import com.pencilbox.netknight.utils.MyLog;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pencil-box on 16/7/1.
 * TCB缓存池,采用线程安全的concurrenthashmap实现
 */
public class TCBCachePool  {

    private static final String TAG = "TCBCachePool";

    private static ConcurrentHashMap<String,TCB> mPool;

    static{

        mPool = new ConcurrentHashMap<String,TCB>();

    }

    /**
     * 存储TCB
     * @param ipAndPort
     * @param tcb
     */
    public static void putTCB(String ipAndPort,TCB tcb){

        if(TextUtils.isEmpty(ipAndPort)||tcb ==null){
            Log.e(TAG,"Key or value is null.");
            return;
        }
        mPool.put(ipAndPort,tcb);



    }

    /**
     * 获取TCB咯
     * @param ipAndPort
     * @return
     */
    public static TCB getTCB(String ipAndPort){
        TCB tcb = mPool.get(ipAndPort);

        return tcb;
    }


    /**
     * 关闭TCB,移除it
     * @param ipAndPort
     */
    public static void closeTCB(String ipAndPort){




        saveTrafficInfo(mPool.get(ipAndPort));

        mPool.remove(ipAndPort);

//        try {
//            mPool.get(ipAndPort).channel.close();
//            mPool.remove(ipAndPort);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }



    /**
     * 移除所有,包括相关的channel
     */
    public static void closeAll() {

        Iterator iter = mPool.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            TCB val = (TCB) entry.getValue();
            saveTrafficInfo(val);
            try {
                val.selectionKey.channel().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("TCBCachePool","TCB Cache全部清理完毕");

        mPool.clear();
    }


    private static void saveTrafficInfo(TCB tcb){

        Traffic traffic = new Traffic();
        traffic.setAppId(tcb.getAppId());
        traffic.setMobileSize(tcb.getMobileBytes());
        traffic.setWifiSize(tcb.getWifiBytes());
        traffic.setRecordTime(System.currentTimeMillis());
        if(traffic.save()){
            Log.d(TAG,"保存流量成功");
        }else{
            Log.e(TAG,"保存流量失败");
        }

    }

}
