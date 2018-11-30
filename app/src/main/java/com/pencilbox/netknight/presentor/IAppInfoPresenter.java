package com.pencilbox.netknight.presentor;


/**
 * Created by pencil-box on 16/6/29.
 * applist展示界面
 */
public interface IAppInfoPresenter {
    int ORDER_BY_NAME = 1;
    int ORDER_BY_NET = 2;
    int ORDER_BY_NET_PERMISSION = 3;
    int ORDER_BY_WIFI = 4;
    int ORDER_BY_WIFI_PERMISSION = 5;

    //加载app列表数据
    void loadAppList();

    /**
     * 根据排序类型对列表进行排序
     */
    void orderAppList(int orderType);

    //修改app是否经过vpn信息
    void changeAppIsVpnAccess(int position);

}
