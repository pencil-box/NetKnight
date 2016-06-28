package com.pencilbox.netknight.presentor;

import android.widget.BaseAdapter;

/**
 * Created by pencil-box on 16/6/28.
 * 阻断IP添加,删除,修改等
 */
public interface IBlockingIpPresenter {

    //添加
    public void addBlockingIp(String originIp,String endIp);

    public void changeBlockingIp(long blockingIpId,String originIp,String endIp);

    public void deleteBlockingIp(long blockingIpId);


    //加载阻断Ip列表
    public void loadBlockingIpList();

}
