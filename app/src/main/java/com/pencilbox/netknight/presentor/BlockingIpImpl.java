package com.pencilbox.netknight.presentor;

import com.pencilbox.netknight.view.IBlockingIpView;

/**
 * Created by pencil-box on 16/6/28.
 */
public class BlockingIpImpl  implements IBlockingIpPresenter{

    private IBlockingIpView mBlockingIpView;

    public BlockingIpImpl(IBlockingIpView blockingIpView){


        mBlockingIpView = blockingIpView;
    }


    @Override
    public void addBlockingIp(String originIp, String endIp) {

        //执行相关操作

        //执行完后,更新列表信息,这里持有adapter对象
        mBlockingIpView.onListRefresh();
    }

    @Override
    public void changeBlockingIp(long blockingIpId, String originIp, String endIp) {

    }

    @Override
    public void deleteBlockingIp(long blockingIpId) {

    }

    @Override
    public void loadBlockingIpList() {

    }
}
