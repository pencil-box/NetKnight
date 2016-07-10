package com.pencilbox.netknight.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.DairyImpl;
import com.pencilbox.netknight.presentor.IDairyPresenter;

import java.util.ArrayList;


public class NetCellularWifi extends Fragment implements IDairyView{
    private LineChart cwlineChart;

    private IDairyPresenter mIDairyPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.net_cellular_wifi, container, false);
        cwlineChart = (LineChart) view.findViewById(R.id.celluarwifi_chart);

        mIDairyPresenter = new DairyImpl(this);
        mIDairyPresenter.showUseOfWifiCelluar(cwlineChart);

        return view;

    }


    @Override
    public void onLoadDatachartList(BaseAdapter adapter) {

    }

    @Override
    public void getshowDataofWifi(long wifiSize) {

    }

    @Override
    public void getshowDataofCelluar(long mobileSize) {

    }

    @Override
    public void getshowDataofWifiCelluar(long wificelluarSize) {

    }

    @Override
    public void getDataOfWifi(ArrayList<Entry> yValue) {

    }

    @Override
    public void getDataOfCelluar(ArrayList<Entry> yValue) {

    }

    @Override
    public void getDataOfCelluarWifi(ArrayList<Entry> yValue) {

    }

    @Override
    public void onDatachartListRefresh() {

    }
}

