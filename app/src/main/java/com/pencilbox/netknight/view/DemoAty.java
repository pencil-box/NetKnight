package com.pencilbox.netknight.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.DemoPresenter;
import com.pencilbox.netknight.presentor.IDemoPresenter;

/**
 * Created by pencil-box on 16/6/9.
 */
public class DemoAty extends Activity implements IDemoView {


    private Button mDemoBtn;
    private TextView mDemoTv;

    private IDemoPresenter mDemoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_demo);

        initView();
        initEvent();

        mDemoPresenter = new DemoPresenter(this);

    }

    private void initEvent() {
        mDemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行耗时操作,注意是在主线程调用的
                mDemoPresenter.doSomeThing();

            }
        });

    }

    /**
     * 初始化控件
     */
    private void initView() {

        mDemoTv = (TextView) findViewById(R.id.tv_demo);

        mDemoBtn = (Button) findViewById(R.id.btn_demo);


    }

    @Override
    public void onDemoResult(String demoId) {
        //拿到demoId,并执行相关的显示操作


        mDemoTv.setText("DemoId:"+demoId);
    }
}
