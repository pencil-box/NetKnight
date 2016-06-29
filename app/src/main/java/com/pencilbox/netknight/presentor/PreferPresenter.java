package com.pencilbox.netknight.presentor;

/**
 * Created by tang on 2016/6/29.
 */
public interface PreferPresenter {

    //加载权限列表
    void loadPermission();



    //防火墙生效网络
    int ON_WIFI = 1;
    int ON_CELLULAR = 2;
    int ON_NET = 3;
    int ON_ALWAYS = 4;
    void changRootWay(int rId);


    /**
     * 移动、WIFI网络下默认权限
     * @param defaultId
     * @param permissionId
     */
    int PROMT = 1;
    int PERMIT =2;
    int FORBID = 3;
    int FORBID_BACK = 4;

    void changDefaultPermission(int defaultId,int permissionId);
}
