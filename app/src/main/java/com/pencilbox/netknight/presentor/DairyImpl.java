package com.pencilbox.netknight.presentor;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pencilbox.netknight.view.IDairyView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by wu on 16/6/28.
 * Modified by su on 16/7/11
 */

public class DairyImpl implements IDairyPresenter {
    private IDairyView mDairyView;

    public DairyImpl(IDairyView mDairyView) {

        this.mDairyView = mDairyView;
        mDairyView.onDatachartListRefresh();
    }

    @Override
    public void showUseOfWifi(LineChart lineChart) {
        /**
         * XAxis 为 X轴的类
         * Entry 为每个点的类
         * DataSet 一组Y轴上面的数据
         * Linedata 整个Y轴的数据
         */
        XAxis xAxis = lineChart.getXAxis();
        /**
         * 设置X轴的文字在底部
         */
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        /**
         * 设置描述文字
         */
        Description desc = new Description();
        desc.setText("WIFI流量/KB");
        lineChart.setDescription(desc);
        /**
         * 获取今日日期
         */
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DATE);
        /**
         * 生成X轴日期
         */
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            xValues.add((month + 1) + "/" + (day - i));
        }
        /**
         * 生成Y轴数据
         */
        Calendar pre = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        pre.set(year, month, day - 6, 0, 0);
        next.set(year, month, day - 5, 0, 0);

        ArrayList<Entry> yValue = new ArrayList<>();
        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024, 0));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024, 1));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024, 2));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024, 3));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024, 4));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024, 5));
        /**
         * 插入当日流量数据
         */
        yValue.add(new Entry(DataSupport.where("recordTime >= ?",
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024, 6));
        /**
         * 构建一个LineDataSet 代表一组Y轴数据
         */
        LineDataSet dataSet = new LineDataSet(yValue, "WIFI流量/KB");
        dataSet.setLineWidth(1.75f);
        dataSet.setColor(Color.YELLOW);
        /**
         * 构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
         */
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        /**
         * 将数据加入dataSets
         */
        dataSets.add(dataSet);
        /**
         * 构建一个LineData  将dataSets放入
         */
        LineData lineData = new LineData(dataSets);
        /**
         * 将数据插入
         */
        lineChart.setData(lineData);
    }

    @Override
    public void showUseOfCelluar(LineChart lineChart) {
        /**
         * XAxis 为 X轴的类
         * Entry 为每个点的类
         * DataSet 一组Y轴上面的数据
         * Linedata 整个Y轴的数据
         */
        XAxis xAxis = lineChart.getXAxis();
        /**
         * 设置X轴的文字在底部
         */
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        /**
         * 设置描述文字
         */
        Description desc = new Description();
        desc.setText("移动流量/KB");
        lineChart.setDescription(desc);
        /**
         * 获取今日日期
         */
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DATE);
        /**
         * 生成X轴日期
         */
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            xValues.add((month + 1) + "/" + (day - i));
        }
        /**
         * 生成Y轴数据
         */
        Calendar pre = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        pre.set(year, month, day - 6, 0, 0);
        next.set(year, month, day - 5, 0, 0);

        ArrayList<Entry> yValue = new ArrayList<>();
        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024, 0));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024, 1));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024, 2));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024, 3));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024, 4));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024, 5));
        /**
         * 插入当日流量数据
         */
        yValue.add(new Entry(DataSupport.where("recordTime >= ?",
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024, 6));
        /**
         * 构建一个LineDataSet 代表一组Y轴数据
         */
        LineDataSet dataSet = new LineDataSet(yValue, "移动流量/KB");
        dataSet.setLineWidth(1.75f);
        dataSet.setColor(Color.YELLOW);
        /**
         * 构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
         */
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        /**
         * 将数据加入dataSets
         */
        dataSets.add(dataSet);
        /**
         * 构建一个LineData  将dataSets放入
         */
        LineData lineData = new LineData(dataSets);
        /**
         * 将数据插入
         */
        lineChart.setData(lineData);
    }

    @Override
    public void showUseOfWifiCelluar(LineChart lineChart) {
        /**
         * XAxis 为 X轴的类
         * Entry 为每个点的类
         * DataSet 一组Y轴上面的数据
         * Linedata 整个Y轴的数据
         */
        XAxis xAxis = lineChart.getXAxis();
        /**
         * 设置X轴的文字在底部
         */
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        /**
         * 设置描述文字
         */
        Description desc = new Description();
        desc.setText("WIFI+移动流量/KB");
        lineChart.setDescription(desc);
        /**
         * 获取今日日期
         */
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DATE);
        /**
         * 生成X轴日期
         */
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            xValues.add((month + 1) + "/" + (day - i));
        }
        /**
         * 生成Y轴数据
         */
        Calendar pre = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        pre.set(year, month, day - 6, 0, 0);
        next.set(year, month, day - 5, 0, 0);

        ArrayList<Entry> yValue = new ArrayList<>();
        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024
                + DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024
                , 0));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024
                + DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024
                , 1));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024
                + DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024
                , 2));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024
                + DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024
                , 3));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024
                + DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024
                , 4));
        pre.add(Calendar.HOUR, 24);
        next.add(Calendar.HOUR, 24);

        yValue.add(new Entry(DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024
                + DataSupport.where("recordTime >= ? and recordTime < ?",
                String.valueOf(pre.getTimeInMillis()),
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024
                , 5));
        /**
         * 插入当日流量数据
         */
        yValue.add(new Entry(DataSupport.where("recordTime >= ?",
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "mobileSize", Long.TYPE) / 1024
                + DataSupport.where("recordTime >= ?",
                String.valueOf(next.getTimeInMillis()))
                .sum("Traffic", "wifiSize", Long.TYPE) / 1024
                , 6));
        /**
         * 构建一个LineDataSet 代表一组Y轴数据
         */
        LineDataSet dataSet = new LineDataSet(yValue, "WIFI+移动流量/KB");
        dataSet.setLineWidth(1.75f);
        dataSet.setColor(Color.YELLOW);
        /**
         * 构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
         */
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        /**
         * 将数据加入dataSets
         */
        dataSets.add(dataSet);
        /**
         * 构建一个LineData  将dataSets放入
         */
        LineData lineData = new LineData(dataSets);
        /**
         * 将数据插入
         */
        lineChart.setData(lineData);
    }

    @Override
    public void loadDatachart() {

    }
}
