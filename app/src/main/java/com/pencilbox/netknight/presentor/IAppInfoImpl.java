package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.pencilbox.netknight.Constants;
import com.pencilbox.netknight.NetKnightApp;
import com.pencilbox.netknight.model.App;
import com.pencilbox.netknight.utils.AppUtils;
import com.pencilbox.netknight.view.IAppInfoView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pencil-box on 16/7/8.
 */
public class IAppInfoImpl implements IAppInfoPresenter {


    //listview的适配器
    public AppInfoAdapter mAppInfoAdapter;
    //回调的Iview接口
    public IAppInfoView mIAppInfoView;


    private final int MSG_GET_APP_LIST = 0;

    private final int MSG_ORDER =1;


//    private final int MSG_ORDER_BY_NAME =1;
//    private final int MSG_ORDER_BY_NET =2;
//    private final int MSG_ORDER_BY_NET_PERMIT =3;
//    private final int MSG_ORDER_BY_WIFI =4;
//    private final int MSG_ORDER_BY_WIFI_PERMIT =5;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what ){
                case MSG_GET_APP_LIST:

                    mIAppInfoView.onLoadAppInfoList(mAppInfoAdapter);

                    break;
                case MSG_ORDER:

                    mAppInfoAdapter.notifyDataSetChanged();

                    break;
                default:
                    break;

            }

        }
    };


    public IAppInfoImpl(Context context,IAppInfoView iAppInfoView){

        mAppInfoAdapter = new AppInfoAdapter(context);
        mIAppInfoView = iAppInfoView;

    }


    @Override
    public void loadAppList() {


        new Thread(new Runnable() {
            @Override
            public void run() {


                //加载信息咯
                List<App> appList = AppUtils.queryAppInfo(NetKnightApp.getContext());

                mAppInfoAdapter.addAll(appList);

                mHandler.sendEmptyMessage(MSG_GET_APP_LIST);

            }
        }).start();



//        mIAppInfoView.onLoadAppInfoList(mAppInfoAdapter);

    }

    @Override
    public void changeAppAccessPermission(int position, int typeId, boolean isWIFI) {




    }

    @Override
    public void orderAppList(final int orderType) {

        Log.d("IAppInfoImpl","orderAppList"+orderType);

        new Thread(new Runnable() {
            @Override
            public void run() {



                switch (orderType){

                    case IAppInfoPresenter.ORDER_BY_NAME:
                        orderByName();
                        break;
                    case IAppInfoPresenter.ORDER_BY_NET:
                        break;
                    case IAppInfoPresenter.ORDER_BY_NET_PERMISSION:

                        orderByNetPermission();

                        break;
                    case IAppInfoPresenter.ORDER_BY_WIFI:
                        break;
                    case IAppInfoPresenter.ORDER_BY_WIFI_PERMISSION:
                        orderByWifiPermission();


                        break;
                    default:
                        break;

                }
                mHandler.sendEmptyMessage(MSG_ORDER);
            }




        }).start();




    }

    private void orderByName() {

        Collections.sort(mAppInfoAdapter.getDatas(), new Comparator<App>() {
            @Override
            public int compare(App lhs, App rhs) {



                return lhs.getName().compareTo(rhs.getName());
            }
        });
    }

    private void orderByWifiPermission() {

        Collections.sort(mAppInfoAdapter.getDatas(), new Comparator<App>() {
            @Override
            public int compare(App lhs, App rhs) {

//                if(!rhs.isAccessVpn()||!lhs.isAccessVpn()){
//                    return 0;
//                }

                if(lhs.getWifiType()<rhs.getWifiType()){
                    return -1;
                }
                if(lhs.getWifiType()>rhs.getWifiType()){
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * 根据netPermission进行排序
     */
    private void orderByNetPermission() {


        Collections.sort(mAppInfoAdapter.getDatas(), new Comparator<App>() {
            @Override
            public int compare(App lhs, App rhs) {

//                if(!rhs.isAccessVpn()||!lhs.isAccessVpn()){
//                    return 0;
//                }


                if(lhs.getMobileDataType()<rhs.getMobileDataType()){
                    return -1;
                }
                if(lhs.getMobileDataType()>rhs.getMobileDataType()){
                    return 1;
                }

                return 0;
            }
        });


    }

    @Override
    public void changeAppIsVpnAccess(int position) {
        App app =   mAppInfoAdapter.getItem(position);

        app.setAccessVpn(!app.isAccessVpn());
        app.save();

        mAppInfoAdapter.notifyDataSetChanged();
        String msg;
        if(app.isAccessVpn()){

            msg = "成功设置该应用通过vpn";
        }else{
            msg = "成功设置该应用不通过vpn";
        }
        mIAppInfoView.onListRefresh(msg);
    }
}
