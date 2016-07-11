package com.pencilbox.netknight.presentor;

/**
 * Created by tang on 2016/6/28.
 * 开始抓包、停止抓包、显示抓包数据
 */
public interface IGraspBagPresenter {

    //开始抓包
    //position 为-1时,表示抓取所有包
    void startGraspingBag(int position);

    //停止抓包
    void stopGraspingBag();

    //加载抓包数据
    void loadGraspGagData();
}
