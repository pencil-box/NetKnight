package com.pencilbox.netknight.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.BlockingIpImpl;
import com.pencilbox.netknight.presentor.IBlockingIpPresenter;

public class MainIp extends Fragment implements IBlockingIpView {
    private PopupWindow popupWindow;


    private IBlockingIpPresenter mBlockingIpPresenter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ip_main, container, false);
        view.findViewById(R.id.btn_iptopleft).setOnClickListener(new View.OnClickListener() {
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
        view.findViewById(R.id.btn_ipadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setView(LayoutInflater.from(getActivity())
                        .inflate(R.layout.ip_inputdialog, null)).create().show();

            }
        });


        mBlockingIpPresenter = new BlockingIpImpl(this);

        return view;
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


    @Override
    public void onLoadBlockingList(BaseAdapter adapter) {

    }

    @Override
    public void onListRefresh() {

    }

    @Override
    public void onOptionFailed(int typeId, String msg) {

    }
}
