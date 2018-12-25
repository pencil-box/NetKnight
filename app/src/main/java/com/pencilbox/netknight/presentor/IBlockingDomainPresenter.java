package com.pencilbox.netknight.presentor;

/**
 * Created by tang on 2016/6/29.
 */
public interface IBlockingDomainPresenter {

    void addBlockingDomain(String cName,String ip);

    void deleteBlockingDomain(int id);

    void loadBlockingDomainList();
}
