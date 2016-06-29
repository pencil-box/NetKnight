package com.pencilbox.netknight.presentor;

/**
 * Created by wu on 16/6/29.
 */

public interface IDairyPresenter {
    //选择显示的时间数据用量
    void showUseOfWifi(long id, int time);

    void showUseOfCelluar(long id, int time);

    void showUseOfWifiCelluar(long id, int time);

    //加载选择的表图
    void loadDatachart();
}
