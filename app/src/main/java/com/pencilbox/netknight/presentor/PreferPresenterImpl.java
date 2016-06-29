package com.pencilbox.netknight.presentor;

import com.pencilbox.netknight.view.PreferView;

/**
 * Created by tang on 2016/6/29.
 */
public class PreferPresenterImpl implements PreferPresenter{

    private PreferView mPreferView;

    public PreferPresenterImpl(PreferView preferView){

        mPreferView = preferView;

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
