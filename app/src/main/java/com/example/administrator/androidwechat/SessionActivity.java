package com.example.administrator.androidwechat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SessionActivity extends AppCompatActivity{
    private List<Session> sessionList=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);
        initSession();
        SessionAdapter adapter=new SessionAdapter(SessionActivity.this,R.layout.session_item,sessionList);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
    private void initSession(){
        Session a=new Session(R.drawable.session_image,"xuwei","你好吗");
        sessionList.add(a);
        Session b=new Session(R.drawable.session_image,"zhangyang","你好吗");
        sessionList.add(b);
        Session c=new Session(R.drawable.session_image,"chenxinyu","你好吗");
        sessionList.add(c);
        Session d=new Session(R.drawable.session_image,"moahanrong","你好吗");
        sessionList.add(d);
        Session e=new Session(R.drawable.session_image,"yangtiancheng","你好吗");
        sessionList.add(e);
    }
}
