package com.pencilbox.netknight.presentor;

import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.pencilbox.netknight.model.BlockIp;
import com.pencilbox.netknight.view.IBlockingIpView;

/**
 * Created by pencil-box on 16/6/28.
 * Modified by su on 16/7/12
 */
public class BlockingIpImpl  implements IBlockingIpPresenter{

    private IBlockingIpView mBlockingIpView;

    public BlockingIpImpl(IBlockingIpView blockingIpView){


        mBlockingIpView = blockingIpView;
    }


    /**
     * 将输入的合法IP地址存入BlockIp表中
     * @param originIp
     * @param endIp
     */
    @Override
    public void addBlockingIp(String originIp, String endIp) {

        //执行相关操作
        BlockIp blockIp = new BlockIp();
        blockIp.setOriginIp(originIp);
        blockIp.setEndIp(endIp);
        blockIp.save();
        Logger.d("Here!");
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
