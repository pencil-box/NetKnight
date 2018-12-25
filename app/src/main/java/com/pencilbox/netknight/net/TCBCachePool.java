package com.pencilbox.netknight.net;


import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pencil-box on 16/7/1.
 * TCB缓存池,采用线程安全的 ConcurrentHashMap 实现
 */
public class TCBCachePool {
    private static final String TAG = "TCBCachePool";
    private static final ConcurrentHashMap<String, TCB> mPool = new ConcurrentHashMap<>();

    /**
     * 存储TCB
     */
    static void putTCB(String ipAndPort, TCB tcb) {
        if (TextUtils.isEmpty(ipAndPort) || tcb == null) {
            Log.e(TAG, "Key or value is null.");
            return;
        }
        mPool.put(ipAndPort, tcb);
    }

    /**
     * 获取TCB咯
     */
    public static TCB getTCB(String ipAndPort) {
        return mPool.get(ipAndPort);
    }

    /**
     * 关闭TCB,移除it
     */
    static void closeTCB(String ipAndPort) {
        mPool.remove(ipAndPort);
    }

    /**
     * 移除所有,包括相关的channel
     */
    public static void closeAll() {
        for (Object o : mPool.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            TCB val = (TCB) entry.getValue();
            try {
                val.selectionKey.channel().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("TCBCachePool", "TCB Cache全部清理完毕");
        mPool.clear();
    }
}
