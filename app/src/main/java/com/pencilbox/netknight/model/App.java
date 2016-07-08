package com.pencilbox.netknight.model;

import org.litepal.crud.DataSupport;

/**
 * Created by pencil-box on 16/6/27.
 * 应用信息表
 */
public class App extends DataSupport{

    private long id;
    private String name;
    //存储的时app icon的路径信息
    private String icon;

    //wifi类型,移动数据访问类型,具体存储什么查看Constants
    private int wifiType;
    private int mobileDataType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getWifiType() {
        return wifiType;
    }

    public void setWifiType(int wifiType) {
        this.wifiType = wifiType;
    }

    public int getMobileDataType() {
        return mobileDataType;
    }

    public void setMobileDataType(int mobileDataType) {
        this.mobileDataType = mobileDataType;
    }
}
