package com.pencilbox.netknight.model;

/**
 * Created by pencil-box on 16/6/27.
 * 阻塞IP段
 */
public class BlockIp {
    //xxx.xxx.xxx.xxx格式
    private String originIp;
    private String endIp;
    private long id;

    public String getOriginIp() {
        return originIp;
    }

    public void setOriginIp(String originIp) {
        this.originIp = originIp;
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
