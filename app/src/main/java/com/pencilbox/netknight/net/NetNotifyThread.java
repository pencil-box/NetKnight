package com.pencilbox.netknight.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.App;
import com.pencilbox.netknight.view.MainTabbed;


import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by pencil-box on 16/7/11.
 * 网络应用访问信息的线程
 */
public class NetNotifyThread extends  Thread{


    private final String TAG = "NetNotifyThread";
    /**
     * Notification构造器
     */
    NotificationCompat.Builder mBuilder;

    /**
     * Notification的ID
     */
    int notifyId = 1000;

    private Context mContext;

    //使线程退出
    private volatile boolean mQuit = false;

    //已经通知过访问的hashmap,就不通知啦
    //key为appId,value为通知的次数
    private HashMap<Long,Integer> mHasNotifiedAppMap;

    /**
     * 标志位退出,即使调用interrupt不一定会退出,双重保证
     */
    public void quit(){
        mQuit = true;
        interrupt();

    }


    //通知管理器咯
    private NotificationManager mNotificationManager;
    private LinkedBlockingQueue<App> mAccessApp ;

    public NetNotifyThread(Context context, BlockingQueue<App> accessApps){
        mAccessApp = (LinkedBlockingQueue<App>) accessApps;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mContext = context;
        mHasNotifiedAppMap = new HashMap<>();
    }

    @Override
    public void run() {
//        super.run();
        Log.d(TAG,"start");
        App app;
        while (true){


            try {

//                Thread.sleep(10);
              app =  mAccessApp.take();
            } catch (InterruptedException e) {
//                e.printStackTrace();
                Log.d(TAG,"Stop");
                if(mQuit)
                    return;
                continue;
            }

            if(app==null){
                continue;
            }


//            int courtTime = mHasNotifiedAppMap.get(app.getId());

            if(mHasNotifiedAppMap.containsKey(app.getId())){
                continue;
            }

            mHasNotifiedAppMap.put(app.getId(),1);
            showNotification(app.getName());


        }


    }

    public void showNotification(String appName){


        mBuilder = new NotificationCompat.Builder(mContext);

        mBuilder.setContentTitle("网络访问").setContentText(appName+"的访问已被拦截")
//                .setNumber(number)// 显示数量
                .setTicker(appName+"尝试访问网络..")// 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setAutoCancel(true)// 设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                // requires VIBRATE permission
                .setSmallIcon(R.mipmap.knight);

        // Notification.FLAG_ONGOING_EVENT --设置常驻
        // Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
        // notification.flags = Notification.FLAG_AUTO_CANCEL;
        // //在通知栏上点击此通知后自动清除此通知
        // 点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(mContext,MainTabbed.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(notifyId, mBuilder.build());

    }

}
