package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.util.Log;

import com.pencilbox.netknight.model.BlockIp;
import com.pencilbox.netknight.view.IBlockingIpView;

import java.util.ArrayList;

/**
 * Created by pencil-box on 16/6/28.
 * Modified by su on 16/7/12
 */
public class BlockingIpImpl implements IBlockingIpPresenter {

    private IBlockingIpView mBlockingIpView;

    private ListAdapter listAdapter;
    private ArrayList<BlockIp> listIp;

    private Context mContext;

    public BlockingIpImpl(IBlockingIpView blockingIpView, Context context) {
        mBlockingIpView = blockingIpView;
        mContext = context;
    }

    /**
     * 将输入的合法IP地址存入BlockIp表中
     */
    @Override
    public void addBlockingIp(String originIp, String endIp) {
        //执行相关操作
        BlockIp blockIp = new BlockIp();
        blockIp.setOriginIp(originIp);
        blockIp.setEndIp(endIp);
        if (!blockIp.save()) {
            Log.e("BlockingIpImpl", "blockingIp 保存失败");
            return;
        }
        listIp.add(blockIp);
        listAdapter.notifyDataSetChanged();
        Log.d("BlockingImpl", "添加数据成功!");
        //执行完后,更新列表信息,这里持有adapter对象
        mBlockingIpView.onListRefresh();
    }

    @Override
    public void deleteBlockingIp(int position) {
        BlockIp blockIp = listAdapter.getItem(position);
        blockIp.delete();
        listIp.remove(position);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadBlockingIpList() {
        listIp = (ArrayList<BlockIp>) org.litepal.LitePal.findAll(BlockIp.class);

        listAdapter = new ListAdapter(mContext, listIp);

        mBlockingIpView.onLoadBlockingList(listAdapter);
    }
}
