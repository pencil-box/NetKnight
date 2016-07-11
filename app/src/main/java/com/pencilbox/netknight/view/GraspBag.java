package com.pencilbox.netknight.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.IGraspBagImpl;
import com.pencilbox.netknight.presentor.IGraspBagPresenter;
import com.pencilbox.netknight.service.NetKnightService;


public class GraspBag extends AppCompatActivity implements View.OnClickListener, IGraspBagView {

    private ImageButton btn_graspleft;
    private PopupWindow popupWindow;
    private Button mCaptureBtn;


    private IGraspBagPresenter mGraspPresenter;

    private boolean isGrasp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.grasp_bag);
        init();
        mGraspPresenter = new IGraspBagImpl(this);
    }

    private void init() {

        btn_graspleft = (ImageButton) this.findViewById(R.id.btn_grasptopleft);

        btn_graspleft.setOnClickListener(this);


        mCaptureBtn = (Button) findViewById(R.id.btn_capture);



        mCaptureBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_grasptopleft:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return;
                } else {
                    initmPopupWindowViewleft();
                    popupWindow.showAsDropDown(v, 0, 5);
                }
                break;
            case R.id.graspbtn_wall:
                Intent intent = new Intent();
                intent.setClass(this, MainTabbed.class);
                startActivity(intent);
                popupWindow.dismiss();
                this.finish();
                break;
            case R.id.graspbtn_dariy:
                Intent intent1 = new Intent();
                intent1.setClass(this, DairyTabbed.class);
                startActivity(intent1);
                popupWindow.dismiss();
                this.finish();
                break;

            case R.id.btn_capture:

                if (isGrasp) {
                    isGrasp = false;
                    mCaptureBtn.setText("开始抓包");
                    stopCapture();

                } else {
                    if (startCapture()) {
                        isGrasp = true;
                        mCaptureBtn.setText("停止抓包");
                    }
                }

                break;

            default:
                break;
        }

    }


    /**
     * 停止抓包
     */
    private void stopCapture() {

        //读写应该放在子线程中操作...
        mGraspPresenter.stopGraspingBag();

    }

    /**
     * 开始抓包咯
     */
    private boolean startCapture() {

        if (NetKnightService.isRunning) {
            Toast.makeText(this, "正在进行抓包", Toast.LENGTH_SHORT).show();

            //传参应该为appId
//            PCapFilter.startCapPacket(0);
            mGraspPresenter.startGraspingBag(-1);
            return true;
        } else {
            Toast.makeText(this, "请开启vpn服务", Toast.LENGTH_SHORT).show();
            return false;
        }


    }


    private void initmPopupWindowViewleft() {
        View customView = getLayoutInflater().inflate(R.layout.graspleft_top,
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
        Button btn_wall = (Button) customView.findViewById(R.id.graspbtn_wall);
        Button btn_dariy = (Button) customView.findViewById(R.id.graspbtn_dariy);
        btn_wall.setOnClickListener(this);
        btn_dariy.setOnClickListener(this);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            startActivity(intent.setClass(this, MainTabbed.class));
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGraspFininished(String savePath) {

        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(this, "抓包失败orz", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "抓包成功,保存路径为:" + savePath, Toast.LENGTH_LONG).show();


    }


    @Override
    public void onLoadApp(BaseAdapter baseAdapter) {

    }

    @Override
    public void onOptionFailed(int typeId, String msg) {

    }

    @Override
    public void onDataShowRefresh() {

    }
}

