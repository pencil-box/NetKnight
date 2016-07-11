package com.pencilbox.netknight.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.pencilbox.netknight.R;

import java.util.ArrayList;
import java.util.List;

public class MainPrefer extends Fragment {
    private PopupWindow popupWindow;
    private Spinner spinner_wall, spinner_net, spinner_wifi;
    private List<String> data_wall, data_net, data_wifi;
    private ArrayAdapter<String> wall_adapter, net_adapter, wifi_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prefer_main, container, false);
        view.findViewById(R.id.btn_pretopleft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                } else {
                    initmPopupWindowViewleft();
                    popupWindow.showAsDropDown(v, 0, 5);
                }
            }
        });
        /**
         * spinner wall
         */
        spinner_wall = (Spinner) view.findViewById(R.id.spinner_wall);
        data_wall = new ArrayList<String>();
        data_wall.add("只在WIFI网络开启时打开防火墙");
        data_wall.add("只在移动数据网络开启时打开防火墙");
        data_wall.add("当有网络时开启防火墙");
        data_wall.add("总是开启防火墙");
        wall_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_wall);
        //设置样式
        wall_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner_wall.setAdapter(wall_adapter);
        spinner_wall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * spiner_net
         */
        spinner_net = (Spinner) view.findViewById(R.id.spinner_net);
        data_net = new ArrayList<String>();
        data_net.add("连接时提示");
        data_net.add("允许联网");
        data_net.add("禁止后台偷跑");
        data_net.add("禁止联网");
        net_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_net);
        //设置样式
        net_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner_net.setAdapter(net_adapter);
        spinner_net.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * spiner wifi
         */
        spinner_wifi = (Spinner) view.findViewById(R.id.spinner_wifi);
        data_wifi = new ArrayList<String>();
        data_wifi.add("连接时提示");
        data_wifi.add("允许联网");
        data_wifi.add("禁止后台偷跑");
        data_wifi.add("禁止联网");
        wifi_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_wifi);
        //设置样式
        net_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner_wifi.setAdapter(wifi_adapter);
        spinner_wifi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initmPopupWindowViewleft() {
        View customView = getActivity().getLayoutInflater().inflate(R.layout.mainleft_top,
                null, false);
        popupWindow = new PopupWindow(customView, 500, 600);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        popupWindow.setAnimationStyle(R.style.ways);
        popupWindow.setOutsideTouchable(true);
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
        Button btn_dariy = (Button) customView.findViewById(R.id.btn_dariy);
        Button btn_bag = (Button) customView.findViewById(R.id.btn_bag);
        btn_dariy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DairyTabbed.class);
                startActivity(intent);
                popupWindow.dismiss();
                getActivity().finish();
            }
        });
        btn_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), GraspBag.class);
                startActivity(intent1);
                popupWindow.dismiss();
                getActivity().finish();

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
