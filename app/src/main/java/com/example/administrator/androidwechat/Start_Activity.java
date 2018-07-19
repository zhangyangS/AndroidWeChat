package com.example.administrator.androidwechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Start_Activity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000; // 三秒后进入系统

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(Start_Activity.this,
                        LoginActivity.class);
                Start_Activity.this.startActivity(mainIntent);
                Start_Activity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
