package com.pencilbox.netknight.view;

import android.widget.BaseAdapter;

/**
 * Created by pencil-box on 16/6/28.
 */
public interface IBlockingIpView {


    //初始化数据的回调
     void onLoadBlockingList(BaseAdapter adapter);

    //添加,删除,修改后的,list回调,数据的更新在adapter里操作就可以
     void onListRefresh();

}
