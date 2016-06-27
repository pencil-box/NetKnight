package com.pencilbox.netknight.view;

import android.content.Intent;
import android.net.VpnService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.service.NetKnightService;
import com.pencilbox.netknight.utils.AppUtils;
import com.pencilbox.netknight.utils.MyLog;
import com.pencilbox.netknight.utils.NetUtils;

public class GraspBag extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn_graspleft;
    private PopupWindow popupWindow;

    private Button mCaptureBtn;
    private Button mStopBtn;
    private TextView mLogTv;

   private Button mNetConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.grasp_bag);
        init();
    }

    private void init() {

        btn_graspleft = (ImageButton) this.findViewById(R.id.btn_grasptopleft);

        btn_graspleft.setOnClickListener(this);




        mCaptureBtn = (Button) findViewById(R.id.btn_capture);

        mStopBtn = (Button) findViewById(R.id.btn_stop_capture);
        mLogTv = (TextView) findViewById(R.id.tv_packet);

        mNetConnect = (Button) findViewById(R.id.btn_connect_server_by_http);


        mNetConnect.setOnClickListener(this);
        mCaptureBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);

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
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.graspbtn_dariy:
                Intent intent1 = new Intent();
                intent1.setClass(this, NetDariy.class);
                startActivity(intent1);
                this.finish();
                break;

            case R.id.btn_capture:

                startVpnService();

                break;
            case R.id.btn_stop_capture:
//                stopVpnService();
                AppUtils.getAppPackegeName(this);

                break;
            case R.id.btn_connect_server_by_http:
                NetUtils.sendHttp();
                break;

            default:
                break;
        }

    }


    private final int REQ_START_VPN = 100;

    /**
     * 开启vpnservice
     */
    private void startVpnService(){


       Intent intent = VpnService.prepare(this);
        if(intent!=null){
            startActivityForResult(intent,REQ_START_VPN);

        }else{

            onActivityResult(REQ_START_VPN,RESULT_OK,null);
        }

    }

    /**
     * 停止vpnservice
     */
    private void stopVpnService(){


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case REQ_START_VPN:
                MyLog.logd(this,"start Vpn Service");

                Intent intent = new Intent(this, NetKnightService.class);
                startService(intent);

                break;
            default:
                break;

        }

    }

    private void initmPopupWindowViewleft() {
        View customView = getLayoutInflater().inflate(R.layout.graspleft_top,
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
        Button btn_wall = (Button) customView.findViewById(R.id.graspbtn_wall);
        Button btn_dariy = (Button) customView.findViewById(R.id.graspbtn_dariy);
        btn_wall.setOnClickListener(this);
        btn_dariy.setOnClickListener(this);

    }
}
