package com.pencilbox.netknight.presentor;

/**
 * Created by tang on 2016/6/28.
 * 开始抓包、停止抓包、显示抓包数据
 */
public interface GraspBagPresenter {

    //开始抓包
    void startGraspingBag(long id);

    //停止抓包
    void stopGraspingBag(long id);

    //加载抓包数据
    void loadGraspGagData();
}
