package com.pencilbox.netknight.presentor;

import android.widget.BaseAdapter;

import com.pencilbox.netknight.view.IDairyView;

/**
 * Created by wu on 16/6/28.
 */

public class DairyImpl implements IDairyPresenter {
    private IDairyView mDairyView;

    public DairyImpl(IDairyView mDairyView) {

        this.mDairyView = mDairyView;
        mDairyView.onDatachartListRefresh();
    }


    @Override
    public void showUseOfWifi(long id, long recordTime) {
    }

    @Override
    public void showUseOfCelluar(long id, long recordTime) {

    }

    @Override
    public void showUseOfWifiCelluar(long id, long recordTime) {

    }

    @Override
    public void loadDatachart() {

    }
}
