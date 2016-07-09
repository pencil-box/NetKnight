package com.pencilbox.netknight.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import org.litepal.crud.DataSupport;

/**
 * Created by wu on 16/6/27.
 */

public class AppInfo extends DataSupport{
    private String appLabel;
    private Drawable appIcon;
    private Intent intent;
    private String pkgName;

    public AppInfo() {
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appName) {
        this.appLabel = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
