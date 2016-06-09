package com.pencilbox.netknight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pencilbox.netknight.view.DemoAty;

public class MainActivity extends AppCompatActivity {


    private Button mDemoBtn2DemoAty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoBtn2DemoAty = (Button) findViewById(R.id.btn_2_demo_aty);

        mDemoBtn2DemoAty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DemoAty.class));
            }
        });
    }
}
