package com.pencilbox.netknight.presentor;


import android.content.Context;
import android.util.Log;

import com.pencilbox.netknight.model.BlockName;
import com.pencilbox.netknight.view.IBlockingAddressView;

import java.util.ArrayList;

/**
 * Created by tang on 2016/6/29.
 */
public class IBlockingDomainImpl implements IBlockingDomainPresenter {

    private IBlockingAddressView iBlockingAddressView;
    private Context mContext;
    private ArrayList<BlockName> listaddress;
    private AddressListAdapter addressListAdapter;

    public IBlockingDomainImpl(IBlockingAddressView iBlockingAddressView, Context context) {
        this.iBlockingAddressView = iBlockingAddressView;
        mContext = context;
    }

    @Override
    public void addBlockingDomain(String cName, String ip) {
        BlockName blockName = new BlockName();
        blockName.setcName(cName);
        blockName.setIp(ip);
        if (!blockName.save()) {
            Log.e("BlockingAddressImpl", "blockingIp 保存失败");
            return;
        }
        listaddress.add(blockName);
        addressListAdapter.notifyDataSetChanged();
        Log.d("BlockingAddressImpl", "添加数据成功!");
        //执行完后,更新列表信息,这里持有adapter对象
        iBlockingAddressView.onListRefresh();
    }

    @Override
    public void deleteBlockingDomain(int id) {
        BlockName blockName = addressListAdapter.getItem(id);
        blockName.delete();
        listaddress.remove(id);
        addressListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadBlockingDomainList() {
        listaddress = (ArrayList<BlockName>) org.litepal.LitePal.findAll(BlockName.class);
        addressListAdapter = new AddressListAdapter(mContext, listaddress);
        iBlockingAddressView.onLoadBlockingAddressList(addressListAdapter);
    }
}





