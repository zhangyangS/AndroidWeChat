package com.example.administrator.androidwechat;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DataTestActivity extends AppCompatActivity{
    private String newcontactId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datatest);
        Button insert=(Button)findViewById(R.id.insert_test);
        Button query=(Button)findViewById(R.id.query_test);
        Button delete=(Button)findViewById(R.id.delete_test);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("content://com.example.administrator.androidwechat.provider/contact");
                ContentValues values=new ContentValues();
                values.put("nickName","never give up");
                values.put("phoneNum",1518987110);
                values.put("imgUrl","abcde");
                Uri newUri=getContentResolver().insert(uri,values);
                newcontactId=newUri.getPathSegments().get(1);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("content://com.example.administrator.androidwechat.provider/contact");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        String name=cursor.getString(cursor.getColumnIndex("nickName"));
                        int phoneNum=cursor.getInt(cursor.getColumnIndex("phoneNum"));
                        String imgUrl=cursor.getString(cursor.getColumnIndex("imgUrl"));
                        Log.d("DataTestActivity",name);
                        Log.d("DataTestActivity", String.valueOf(phoneNum));
                        Log.d("DataTestActivity",imgUrl);
                    }
                    cursor.close();
                }
            }
        });
    }
}
