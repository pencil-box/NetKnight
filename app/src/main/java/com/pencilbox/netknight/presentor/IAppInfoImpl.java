package com.pencilbox.netknight.presentor;

import android.content.Context;

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



    public IAppInfoImpl(Context context,IAppInfoView iAppInfoView){

        mAppInfoAdapter = new AppInfoAdapter(context);
        mIAppInfoView = iAppInfoView;

    }


    @Override
    public void loadAppList() {

        //加载信息咯
        List<App> appList = AppUtils.queryAppInfo(NetKnightApp.getContext());

        mAppInfoAdapter.addAll(appList);

        mIAppInfoView.onLoadAppInfoList(mAppInfoAdapter);

    }

    @Override
    public void changeAppAccessPermission(int position, int typeId, boolean isWIFI) {




    }

    @Override
    public void orderAppList(int orderType) {

    }
}
