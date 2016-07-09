package com.pencilbox.netknight.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.App;
import com.pencilbox.netknight.model.AppInfo;
import com.pencilbox.netknight.presentor.AppInfoAdapter;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.orhanobut.logger.Logger;
import com.pencilbox.netknight.presentor.IAppInfoImpl;
import com.pencilbox.netknight.presentor.IAppInfoPresenter;
import com.pencilbox.netknight.utils.AppUtils;


public class MainApp extends Fragment implements View.OnClickListener ,IAppInfoView{
    private PopupWindow popupWindow;
    private ListView app_listView = null;
    private List<String> data_appchoose = null;
    private Spinner app_choose;
    private ArrayAdapter<String> appchoose_adapter;


    private IAppInfoPresenter mIAppInfoPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_main, container, false);


        app_listView = (ListView) view.findViewById(R.id.app_listview);





        //TODO 这个inflate是闹哪样?
        View view1 = inflater.inflate(R.layout.app_items, null);

        app_choose = (Spinner) view1.findViewById(R.id.app_choose);
        data_appchoose = new ArrayList<String>();
        data_appchoose.add("允许联网");
        data_appchoose.add("禁止联网");
        data_appchoose.add("联网时提示");
        data_appchoose.add("禁止后台偷跑");
        appchoose_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_appchoose);
        //设置样式
        appchoose_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        app_choose.setAdapter(appchoose_adapter);
        app_choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        view.findViewById(R.id.btn_topleft).setOnClickListener(new View.OnClickListener() {
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

        view.findViewById(R.id.btn_topright).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                } else {
                    initmPopupWindowViewright();
                    popupWindow.showAsDropDown(v, 0, 5);
                }

            }
        });
        /**
         * Listview off apps in cellphone
         */

        mIAppInfoPresenter = new IAppInfoImpl(getActivity(),this);
        mIAppInfoPresenter.loadAppList();

        return view;

    }

    @Override
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
                getActivity().finish();
            }
        });
        btn_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), GraspBag.class);
                startActivity(intent1);
                getActivity().finish();

            }
        });


    }

    private void initmPopupWindowViewright() {
        View customView = getActivity().getLayoutInflater().inflate(R.layout.right_top,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupWindow = new PopupWindow(customView, 500, 800);
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
        Button btn_appsort = (Button) customView.findViewById(R.id.btn_appsort);
        Button btn_netusesort = (Button) customView.findViewById(R.id.btn_netusesort);
        Button btn_netlimitsort = (Button) customView.findViewById(R.id.btn_netlimitsort);
        Button btn_wifiusesort = (Button) customView.findViewById(R.id.btn_wifisort);
        Button btn_wifilimitsort = (Button) customView.findViewById(R.id.btn_wifilimitsort);
        btn_appsort.setOnClickListener(this);
        btn_netusesort.setOnClickListener(this);
        btn_netlimitsort.setOnClickListener(this);
        btn_wifiusesort.setOnClickListener(this);
        btn_wifilimitsort.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }



//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        //启动应用么?
//        Intent intent = listAppInfo.get(position).getIntent();
//        //startActivity(intent);
//
//    }



    @Override
    public void onLoadAppInfoList(BaseAdapter adapter) {
Log.d("MainApp","加载数据咯");
        app_listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListRefresh() {



    }

    @Override
    public void onOptionFailed(int optionId, String msg) {

    }
}
