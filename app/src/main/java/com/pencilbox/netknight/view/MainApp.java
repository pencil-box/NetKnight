package com.pencilbox.netknight.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.pencilbox.netknight.R;


public class MainApp extends Fragment implements View.OnClickListener {
    private PopupWindow popupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_main, container, false);

        view.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(intent.setClass(getActivity(),MainTabbed.class));
                getActivity().finish();
            }
        });
        view.findViewById(R.id.dariy_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(intent.setClass(getActivity(),DairyTabbed.class));
                getActivity().finish();
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
        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
       /*  ImageButton btn_topleft = (ImageButton) getActivity().findViewById(R.id.btn_topleft);
        ImageButton btn_topright = (ImageButton) getActivity().findViewById(R.id.btn_topright);
        btn_topleft.setOnClickListener(new View.OnClickListener() {
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

        btn_topright.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    private void initmPopupWindowViewleft() {
        View customView = getActivity().getLayoutInflater().inflate(R.layout.mainleft_top,
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
