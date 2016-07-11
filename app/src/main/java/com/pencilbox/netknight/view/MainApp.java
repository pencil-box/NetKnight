package com.pencilbox.netknight.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.IAppInfoImpl;
import com.pencilbox.netknight.presentor.IAppInfoPresenter;
import com.pencilbox.netknight.service.NetKnightService;



public class MainApp extends Fragment implements View.OnClickListener, IAppInfoView {
    private PopupWindow popupWindow;
    private ListView app_listView ;


    private Switch mFireWallSwitch;

    private IAppInfoPresenter mIAppInfoPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_main, container, false);
        app_listView = (ListView) view.findViewById(R.id.app_listview);

        app_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("MainApp","onItemLongClick :"+position);

                if(NetKnightService.isRunning){
                    Toast.makeText(getActivity(),"请关闭VpnService再重试",Toast.LENGTH_SHORT).show();
                    return false;
                }

                mIAppInfoPresenter.changeAppIsVpnAccess(position);

                return false;
            }
        });


        mFireWallSwitch = (Switch) view.findViewById(R.id.switch_vpnsevice);
        mFireWallSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (getActivity() instanceof MainTabbed) {

                    if (isChecked) {

                        ((MainTabbed) getActivity()).startVpnService();

                    } else {

                        ((MainTabbed) getActivity()).stopVpnService();

                    }

                } else {

                    Log.e("MainApp", "--onCheckedChanged-- activity is not MainTabbed");
                }

            }
        });
        //设置firewall的状态咯
        mFireWallSwitch.setChecked(NetKnightService.isRunning);


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

        mIAppInfoPresenter = new IAppInfoImpl(getActivity(), this);
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
        Log.d("MainApp", "加载数据咯");
        app_listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListRefresh(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onOptionFailed(int optionId, String msg) {

    }
}
