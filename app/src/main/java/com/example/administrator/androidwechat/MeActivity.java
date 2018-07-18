package com.example.administrator.androidwechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


//me页面   设置 跳转到 MsgAlert(消息提醒)
public class MeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_layout);
        RelativeLayout setup = (RelativeLayout) findViewById(R.id.set_up);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this,MsgAlert.class);
                startActivity(intent);
            }
        });
    }
}
