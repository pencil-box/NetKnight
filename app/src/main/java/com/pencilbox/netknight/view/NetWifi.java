package com.pencilbox.netknight.view;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pencilbox.netknight.R;

import java.util.ArrayList;

public class NetWifi extends Fragment {
    private LineChart wifilineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.net_wifi, container, false);
        /*
        XAxis 为 X轴的类
        Entry 为每个点的类
        DataSet 一组Y轴上面的数据
        Linedata 整个Y轴的数据
         */
        wifilineChart = (LineChart) view.findViewById(R.id.wifi_chart);
        XAxis xAxis = wifilineChart.getXAxis();
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置描述文字
        wifilineChart.setDescription("WIFI流量");
        //模拟一个x轴的数据  6/1 6/2 ... 6/7
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            xValues.add("6/" + i);
        }
        //模拟一组y轴数据(存放y轴数据的是一个Entry的ArrayList) 他是构建LineDataSet的参数之一

        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            yValue.add(new Entry(i, i));
        }
        //构建一个LineDataSet 代表一组Y轴数据 （比如不同的彩票： 七星彩  双色球）

        LineDataSet dataSet = new LineDataSet(yValue, "前台流量");

        //模拟第二组组y轴数据(存放y轴数据的是一个Entry的ArrayList) 他是构建LineDataSet的参数之一

        ArrayList<Entry> yValue1 = new ArrayList<>();

        yValue1.add(new Entry(7, 0));
        yValue1.add(new Entry(17, 1));
        yValue1.add(new Entry(3, 2));
        yValue1.add(new Entry(5, 3));
        yValue1.add(new Entry(4, 4));
        yValue1.add(new Entry(3, 5));
        yValue1.add(new Entry(7, 6));


        LineDataSet dataSet1 = new LineDataSet(yValue1, "后台流量");
        dataSet.setLineWidth(1.75f);
        dataSet1.setLineWidth(1.75f);
        dataSet1.setColor(Color.argb(55, 82, 151, 236));
        dataSet.setColor(Color.GRAY);
        //构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        //将数据加入dataSets
        dataSets.add(dataSet);
        dataSets.add(dataSet1);

        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(xValues, dataSets);

        //将数据插入
        wifilineChart.setData(lineData);

        return view;


    }
}
