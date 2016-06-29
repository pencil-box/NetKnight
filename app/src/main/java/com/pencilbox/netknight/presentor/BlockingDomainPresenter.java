package com.pencilbox.netknight.presentor;

/**
 * Created by tang on 2016/6/29.
 */
public interface BlockingDomainPresenter {

    void addBlockingDomain(String domainName,String doIp);

    void changeBlockingDomain(long doId,String domainName,String doIp);

    void deleteBlockingDomain(long doId);

    void loadBlockingDomainList();
}
