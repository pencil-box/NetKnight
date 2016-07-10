package com.pencilbox.netknight.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.DairyImpl;

/*
 * Created by wu on 16/7/11.
 */

public class NetAppUse extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.net_appuse, container, false);

        return view;

    }
}
