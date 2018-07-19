package com.example.administrator.androidwechat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddNewFriend extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_friend);
        Button backBtn = (Button) findViewById(R.id.title_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewFriend.this.finish();
            }
        });


    }

}
