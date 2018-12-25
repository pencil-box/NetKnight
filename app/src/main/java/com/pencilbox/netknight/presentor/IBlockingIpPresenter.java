package com.pencilbox.netknight.presentor;


/**
 * Created by pencil-box on 16/6/28.
 * 阻断IP添加,删除,修改等
 */
public interface IBlockingIpPresenter {
    //添加
    void addBlockingIp(String originIp, String endIp);

    void deleteBlockingIp(int position);

    //加载阻断Ip列表
    void loadBlockingIpList();

}
