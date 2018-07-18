package com.example.administrator.androidwechat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SessionActivity extends AppCompatActivity{
    private List<Session> sessionList=new ArrayList<>();
    private MyDatabaseHelper dbHelper;

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
        dbHelper=new MyDatabaseHelper(this,"Message.db",null,3);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select nickName from Contact where 1=1",new String[]{});
        while (cursor.moveToNext()){
            String nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            Session a=new Session(R.drawable.session_image,nickName,"你好吗");
            sessionList.add(a);
        }


    }
}
