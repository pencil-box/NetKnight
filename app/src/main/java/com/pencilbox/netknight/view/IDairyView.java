package com.pencilbox.netknight.view;

import android.widget.BaseAdapter;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/6/28.
 */

public interface IDairyView {

    //初始化数据的回调
    void onLoadDatachartList(BaseAdapter adapter);

    void getshowDataofWifi(long wifiSize);

    void getshowDataofCelluar(long mobileSize);

    void getshowDataofWifiCelluar(long wificelluarSize);

    //获取表格流量数据
    void getDataOfWifi(ArrayList<Entry> yValue);

    void getDataOfCelluar(ArrayList<Entry> yValue);

    void getDataOfCelluarWifi(ArrayList<Entry> yValue);


    void onDatachartListRefresh();


}
