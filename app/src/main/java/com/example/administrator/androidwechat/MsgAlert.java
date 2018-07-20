package com.example.administrator.androidwechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MsgAlert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_alert_layout);
        TextView msgalert= (TextView) findViewById(R.id.msg_alert1);
        msgalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MsgAlert.this,MsgNotification.class);
                startActivity(intent);
            }
        });
    }
}
