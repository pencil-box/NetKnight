package com.pencilbox.netknight.view;

import android.widget.BaseAdapter;

/**
 * Created by wu on 16/7/11.
 */

public interface IAppUseInfoView {
    //初始化数据的回调
    void onLoadAppInfoUseList(BaseAdapter adapter);

    //添加,删除,修改后的,list回调,数据的更新在adapter里操作就可以
    void onListRefresh();

    /**
     * 错误信息回调
     * @param optionId
     * @param msg
     */
    void onOptionFailed(int optionId,String msg);

}
