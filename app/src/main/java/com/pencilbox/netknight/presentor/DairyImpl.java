package com.pencilbox.netknight.presentor;

import android.graphics.Color;
import android.widget.BaseAdapter;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pencilbox.netknight.view.IDairyView;

import java.util.ArrayList;

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
    public void showUseOfWifi(LineChart lineChart) {
        /*
        XAxis 为 X轴的类
        Entry 为每个点的类
        DataSet 一组Y轴上面的数据
        Linedata 整个Y轴的数据
         */
        XAxis xAxis = lineChart.getXAxis();
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置描述文字
        lineChart.setDescription("WIFI流量");
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
        lineChart.setData(lineData);
    }

    @Override
    public void showUseOfCelluar(LineChart lineChart) {
        /*
        XAxis 为 X轴的类
        Entry 为每个点的类
        DataSet 一组Y轴上面的数据
        Linedata 整个Y轴的数据
         */
        XAxis xAxis = lineChart.getXAxis();
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置描述文字
        lineChart.setDescription("蜂窝移动流量");
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
        dataSet1.setColor(Color.argb(55,82,151,236));
        dataSet.setColor(Color.GRAY);
        //构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        //将数据加入dataSets
        dataSets.add(dataSet);
        dataSets.add(dataSet1);

        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(xValues, dataSets);

        //将数据插入
        lineChart.setData(lineData);

    }

    @Override
    public void showUseOfWifiCelluar(LineChart lineChart) {
         /*
        XAxis 为 X轴的类
        Entry 为每个点的类
        DataSet 一组Y轴上面的数据
        Linedata 整个Y轴的数据
         */
        XAxis xAxis = lineChart.getXAxis();
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置描述文字
        lineChart.setDescription("蜂窝移动+WIFI流量");
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
        lineChart.setData(lineData);
    }

    @Override
    public void loadDatachart() {

    }
}
