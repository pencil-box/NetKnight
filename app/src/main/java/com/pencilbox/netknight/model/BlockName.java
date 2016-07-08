package com.pencilbox.netknight.model;

import org.litepal.crud.DataSupport;

/**
 * Created by pencil-box on 16/6/27.
 * 阻断的域名
 */
public class BlockName extends DataSupport{

    //域名信息
    private String cName;
    private String ip;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
