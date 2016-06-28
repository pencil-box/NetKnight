package com.pencilbox.netknight.view;

import android.widget.BaseAdapter;

/**
 * Created by pencil-box on 16/6/28.
 */
public interface IBlockingIpView {


    //初始化数据的回调
    public void onLoadBlockingList(BaseAdapter adapter);

    //添加,删除,修改后的,list回调,数据的更新在adapter里操作就可以
    public void onListRefresh();

    //操作类型和错误信息
    public void onOptionFailed(int typeId,String msg);




}
