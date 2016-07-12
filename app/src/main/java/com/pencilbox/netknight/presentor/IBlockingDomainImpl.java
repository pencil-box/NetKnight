package com.pencilbox.netknight.presentor;


import com.pencilbox.netknight.model.BlockName;
import com.pencilbox.netknight.view.IBlockingIpView;

/**
 * Created by tang on 2016/6/29.
 */
public class IBlockingDomainImpl implements IBlockingDomainPresenter {

    private IBlockingIpView  doName;

    public IBlockingDomainImpl(IBlockingIpView domainName){

        doName = domainName;
    }

    @Override
    public void addBlockingDomain(String domainName, String doIp) {
        BlockName blockName = new BlockName();
        blockName.setcName(domainName);
        blockName.setIp(doIp);
        blockName.save();
        doName.onListRefresh();
    }

    @Override
    public void changeBlockingDomain(long doId, String domainName, String doIp) {

    }

    @Override
    public void deleteBlockingDomain(long doId) {

    }

    @Override
    public void loadBlockingDomainList() {

    }
}

