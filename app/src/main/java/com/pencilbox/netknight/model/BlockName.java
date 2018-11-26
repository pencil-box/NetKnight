package com.pencilbox.netknight.model;

import org.litepal.crud.LitePalSupport;

/**
 * Created by pencil-box on 16/6/27.
 * 阻断的域名
 */
public class BlockName extends LitePalSupport {

    //域名信息
    private String cName;
    private String ip;
    private long id;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
