package com.pencilbox.netknight.view;
/**
 * wu
 * main界面
 * 创建时间:6/12
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.pencilbox.netknight.R;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private MainApp app_fragment;
    private MainAddress address_fragment;
    private MainIp ip_fragment;
    private MainPrefer prefer_fragment;
    private Button btn_app, btn_ip, btn_address, btn_prefer;


    // private Button mDemoBtn2DemoAty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //方便检测
        Intent intent1 = new Intent();
        intent1.setClass(this, GraspBag.class);
        startActivity(intent1);
        finish();


//        init();
//        initFragement(0);

        // mDemoBtn2DemoAty = (Button) findViewById(R.id.btn_2_demo_aty);

    }

    private void initFragement(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (app_fragment == null) {
                    app_fragment = new MainApp();
                    transaction.add(R.id.content, app_fragment);
                } else {
                    transaction.show(app_fragment);
                }
                break;
            case 1:
                if (ip_fragment == null) {
                    ip_fragment = new MainIp();
                    transaction.add(R.id.content, ip_fragment);

                } else {
                    transaction.show(ip_fragment);
                }
                break;
            case 2:
                if (address_fragment == null) {
                    address_fragment = new MainAddress();
                    transaction.add(R.id.content, address_fragment);
                } else {
                    transaction.show(address_fragment);
                }
                break;
            case 3:
                if (prefer_fragment == null) {
                    prefer_fragment = new MainPrefer();
                    transaction.add(R.id.content, prefer_fragment);
                } else {
                    transaction.show(prefer_fragment);
                }

                break;


        }
        transaction.commit();
    }

    private void hideFragment(android.support.v4.app.FragmentTransaction transaction) {
        if (app_fragment != null) {
            transaction.hide(app_fragment);
        }
        if (ip_fragment != null) {
            transaction.hide(ip_fragment);
        }
        if (address_fragment != null) {
            transaction.hide(address_fragment);
        }

        if (prefer_fragment != null) {
            transaction.hide(prefer_fragment);
        }

    }

    private void init() {
        btn_app = (Button) this.findViewById(R.id.btn_app);
        btn_ip = (Button) this.findViewById(R.id.btn_ip);
        btn_address = (Button) this.findViewById(R.id.btn_address);
        btn_prefer = (Button) this.findViewById(R.id.btn_prefer);
        // btn_topleft = (ImageButton) this.findViewById(R.id.btn_topleft);
        //btn_topright = (ImageButton) this.findViewById((R.id.btn_topright));

        btn_ip.setOnClickListener(this);
        btn_app.setOnClickListener(this);
        btn_address.setOnClickListener(this);
        btn_prefer.setOnClickListener(this);
//        btn_topleft.setOnClickListener(this);
        //      btn_topright.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_app:
                initFragement(0);
                break;
            case R.id.btn_ip:
                initFragement(1);
                break;
            case R.id.btn_address:
                initFragement(2);
                break;
            case R.id.btn_prefer:
                initFragement(3);
                break;
            /*
            case R.id.btn_topleft:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                } else {
                    initmPopupWindowViewleft();
                    popupWindow.showAsDropDown(v, 0, 5);
                }
                break;

            case R.id.btn_topright:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                } else {
                    initmPopupWindowViewright();
                    popupWindow.showAsDropDown(v, 0, 5);
                }
                break;

            case R.id.btn_dariy:
                Intent intent = new Intent();
                intent.setClass(this, NetDariy.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.btn_bag:
                Intent intent1 = new Intent();
                intent1.setClass(this, GraspBag.class);
                startActivity(intent1);
                this.finish();
                break;
                */

            default:
                break;

        }


    }
}
