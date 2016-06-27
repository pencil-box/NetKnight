package com.pencilbox.netknight.model;

import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by pencil-box on 16/6/27.
 * 流量实体类
 */
public class Traffic {

    private long id;
    //以byte为单位
    private long wifiSize;
    private long mobileSize;

    //与App关联,appId
    private String appId;

    //记录的时间,
    // The value is the number of milliseconds since Jan. 1, 1970, midnight GMT.
    private long recordTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWifiSize() {
        return wifiSize;
    }

    public void setWifiSize(long wifiSize) {
        this.wifiSize = wifiSize;
    }

    public long getMobileSize() {
        return mobileSize;
    }

    public void setMobileSize(long mobileSize) {
        this.mobileSize = mobileSize;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getRecordTime() {
        return recordTime;
    }


    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }
}
