package com.example.administrator.androidwechat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar();
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.chat_gone);
        if(actionBar!=null){
            actionBar.hide();
        }
        ImageButton plus = (ImageButton)findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                }else{
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
