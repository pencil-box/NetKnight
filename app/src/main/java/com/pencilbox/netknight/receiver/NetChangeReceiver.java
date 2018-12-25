package com.pencilbox.netknight.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


/**
 * Created by pencil-box on 16/7/8.
 */
public class NetChangeReceiver extends BroadcastReceiver {

    public final static int NET_STATE_NONE = 0;
    public final static int NET_STATE_MOBILE = 1;
    public final static int NET_STATE_WIFI = 2;

    //判断网络是何种状态
    public static volatile int sNetState;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //noinspection deprecation
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();

        if (isWifiConn) {
            sNetState = NET_STATE_WIFI;
            Log.d("NetChangeReceiver", "wifi连接上了");
            return;
        }

        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if (isMobileConn) {
            sNetState = NET_STATE_MOBILE;
            Log.d("NetChangeReceiver", "mobile连接上了");
            return;
        }

        sNetState = NET_STATE_NONE;
        Log.d("NetChangeReceiver", "无连接");
    }
}
