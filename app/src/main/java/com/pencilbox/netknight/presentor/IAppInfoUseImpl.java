package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.pencilbox.netknight.NetKnightApp;
import com.pencilbox.netknight.model.App;
import com.pencilbox.netknight.utils.AppUtils;
import com.pencilbox.netknight.view.IAppUseInfoView;

import java.util.List;

/**
 * Created by wu on 16/7/11.
 */

public class IAppInfoUseImpl implements IAppInfoUsePresenter {
    //listview的适配器
    public AppInfoUseAdapter mAppInfoUseAdapter;
    //回调的Iview接口
    public IAppUseInfoView mIAppUseInfoView;

    private final int MSG_GET_APP_LIST = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_GET_APP_LIST:

                    mIAppUseInfoView.onLoadAppInfoUseList(mAppInfoUseAdapter);

                    break;
                default:
                    break;

            }

        }
    };

    public IAppInfoUseImpl(Context context, IAppUseInfoView iAppInfoUseView) {

        mAppInfoUseAdapter = new AppInfoUseAdapter(context);
        mIAppUseInfoView = iAppInfoUseView;

    }

    @Override
    public void loadAppList() {
        new Thread(new Runnable() {
            @Override
            public void run() {


                //加载信息咯
                List<App> appList = AppUtils.queryAppInfo(NetKnightApp.getContext());

                mAppInfoUseAdapter.addAll(appList);

                mHandler.sendEmptyMessage(MSG_GET_APP_LIST);

            }
        }).start();


    }


    @Override
    public void orderAppList(int orderType) {

    }
}