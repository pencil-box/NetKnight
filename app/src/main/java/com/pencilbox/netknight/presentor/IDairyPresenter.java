package com.pencilbox.netknight.presentor;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by wu on 16/6/29.
 */

public interface IDairyPresenter {
    //选择显示的时间数据用量
    void showUseOfWifi(LineChart lineChart);

    void showUseOfCelluar(LineChart lineChart);

    void showUseOfWifiCelluar(LineChart lineChart);


    //加载选择的表图
    void loadDatachart();
}
