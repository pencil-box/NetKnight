package com.pencilbox.netknight.view;

import android.widget.BaseAdapter;

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


    void onDatachartListRefresh();


}
