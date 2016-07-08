package com.pencilbox.netknight.model;

import org.litepal.crud.DataSupport;

/**
 * Created by pencil-box on 16/6/27.
 * 记录的IP信息
 */
public class AppIp extends DataSupport{

    private long id;
    private String ip;
    private long appId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
