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
    }

    @Override
    public void showUseOfWifi(long id, int time) {

    }

    @Override
    public void showUseOfCelluar(long id, int time) {

    }

    @Override
    public void showUseOfWifiCelluar(long id, int time) {

    }

    @Override
    public void loadDatachart() {

    }
}
