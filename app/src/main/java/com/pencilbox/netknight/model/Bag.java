package com.pencilbox.netknight.model;

import org.litepal.crud.DataSupport;

/**
 * Created by AbelSu on 16/6/29.
 * 抓取流量包的实体类
 */
public class Bag extends DataSupport{

    private long id;//记录索引
    private long appId;//应用包名
    private long bagSize;//数据大小

    private String sIp;//源IP
    private String sPort;//源端口号
    private String dIp;//目的IP
    private String dPort;//目的端口
    private String protocol;//通讯协议

    //Get & Set methods
    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public long getBagSize() {
        return bagSize;
    }

    public void setBagSize(long bagSize) {
        this.bagSize = bagSize;
    }

    public String getsIp() {
        return sIp;
    }

    public void setsIp(String sIp) {
        this.sIp = sIp;
    }

    public String getsPort() {
        return sPort;
    }

    public void setsPort(String sPort) {
        this.sPort = sPort;
    }

    public String getdIp() {
        return dIp;
    }

    public void setdIp(String dIp) {
        this.dIp = dIp;
    }

    public String getdPort() {
        return dPort;
    }

    public void setdPort(String dPort) {
        this.dPort = dPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    //Methods to be complete
    public void graspBag(long appId) { }
    public void saveGraspedBag(long appId) { }
    public void showBagDetails() { }
}
