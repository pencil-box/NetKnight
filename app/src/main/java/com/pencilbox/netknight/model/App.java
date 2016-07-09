package com.pencilbox.netknight.model;

import android.graphics.drawable.Drawable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by pencil-box on 16/6/27.
 * 应用信息表
 */
public class App extends DataSupport{

    private long id;
    //app的label名称
    private String name;

    //pkgname的信息
    private String pkgname;


    //存储的时app icon的drawable信息
    @Column(ignore=true)
    private Drawable icon;

    //uid  userId每一个app对应的唯一uid
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



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

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
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

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }
}
