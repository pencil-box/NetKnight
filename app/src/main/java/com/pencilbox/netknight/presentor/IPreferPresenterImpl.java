package com.pencilbox.netknight.presentor;

import com.pencilbox.netknight.view.IPreferView;

/**
 * Created by tang on 2016/6/29.
 */
public class IPreferPresenterImpl implements IPreferPresenter {

    private IPreferView mIPreferView;

    public IPreferPresenterImpl(IPreferView IPreferView){

        mIPreferView = IPreferView;

    }

    @Override
    public void changDefaultPermission(int defaultId, int permissionId) {

    }

    @Override
    public void changRootWay(int rId) {

    }

    @Override
    public void loadPermission() {

    }
}
