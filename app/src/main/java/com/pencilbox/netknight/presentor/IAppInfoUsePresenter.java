package com.pencilbox.netknight.presentor;

/**
 * Created by wu on 16/7/11.
 */

public interface IAppInfoUsePresenter {
    //加载app列表数据
    void loadAppList();

    void orderAppList(int orderType);
}
