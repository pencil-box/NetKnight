package com.pencilbox.netknight.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.pencilbox.netknight.R;

public class NetDariy extends FragmentActivity implements View.OnClickListener {
    private PopupWindow popupWindow;
    private ImageButton btn_netleft;
    private NetCellular net_fragment;
    private NetWifi wifi_fragment;
    private NetCellularWifi netwifi_fragment;
    private Button btn_net, btn_wifi, btn_netwifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_dariy);
        init();
        initFragement(0);

    }

    private void initFragement(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (net_fragment == null) {
                    net_fragment = new NetCellular();
                    transaction.add(R.id.net_mid1, net_fragment);
                } else {
                    transaction.show(net_fragment);
                }
                break;
            case 1:
                if (wifi_fragment == null) {
                    wifi_fragment = new NetWifi();
                    transaction.add(R.id.net_mid1, wifi_fragment);

                } else {
                    transaction.show(wifi_fragment);
                }
                break;
            case 2:
                if (netwifi_fragment == null) {
                    netwifi_fragment = new NetCellularWifi();
                    transaction.add(R.id.net_mid1, netwifi_fragment);
                } else {
                    transaction.show(netwifi_fragment);
                }
                break;
            default:
                break;


        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (net_fragment != null) {
            transaction.hide(net_fragment);
        }
        if (wifi_fragment != null) {
            transaction.hide(wifi_fragment);
        }
        if (netwifi_fragment != null) {
            transaction.hide(netwifi_fragment);
        }

    }


    private void init() {
        btn_netleft = (ImageButton) this.findViewById(R.id.btn_nettopleft);
        btn_net = (Button) this.findViewById(R.id.btn_net);
        btn_wifi = (Button) this.findViewById(R.id.btn_wifi);
        btn_netwifi = (Button) this.findViewById(R.id.btn_netwifi);
        btn_net.setOnClickListener(this);
        btn_wifi.setOnClickListener(this);
        btn_netwifi.setOnClickListener(this);
        btn_netleft.setOnClickListener(this);


    }

    private void initmPopupWindowViewleft() {
        View customView = getLayoutInflater().inflate(R.layout.netleft_top,
                null, false);
        popupWindow = new PopupWindow(customView, 500, 600);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        popupWindow.setAnimationStyle(R.style.ways);
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

                return false;
            }
        });

        /** 在这里可以实现自定义视图的功能 */
        Button btn_wall = (Button) customView.findViewById(R.id.netbtn_wall);
        Button btn_bag = (Button) customView.findViewById(R.id.netbtn_bag);
        btn_wall.setOnClickListener(this);
        btn_bag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nettopleft:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return;
                } else {
                    initmPopupWindowViewleft();
                    popupWindow.showAsDropDown(v, 0, 5);
                }
                break;
            case R.id.netbtn_wall:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.netbtn_bag:
                Intent intent1 = new Intent();
                intent1.setClass(this, GraspBag.class);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.btn_net:
                initFragement(0);
                break;
            case R.id.btn_wifi:
                initFragement(1);
                break;
            case R.id.btn_netwifi:
                initFragement(2);
                break;

            default:
                break;
        }


    }

}



