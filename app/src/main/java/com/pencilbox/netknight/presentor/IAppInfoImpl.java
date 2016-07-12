package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.pencilbox.netknight.NetKnightApp;
import com.pencilbox.netknight.model.App;
import com.pencilbox.netknight.utils.AppUtils;
import com.pencilbox.netknight.view.IAppInfoView;

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
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what ){
                case MSG_GET_APP_LIST:

                    mIAppInfoView.onLoadAppInfoList(mAppInfoAdapter);

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
    public void orderAppList(int orderType) {

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
