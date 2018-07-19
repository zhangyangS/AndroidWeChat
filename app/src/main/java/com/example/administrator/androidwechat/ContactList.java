package com.example.administrator.androidwechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {
    private List<PersonInfo> personInfoList = new ArrayList<>();
    private Object new_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initPersonInfos();   //初始化人员数据
        PersonInfoAdapter adapter = new PersonInfoAdapter(ContactList.this,
                R.layout.contact_list_item,personInfoList);
        ListView listView = (ListView) findViewById(R.id.address_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Intent intent = new Intent(ContactList.this,AddNewFriend.class);
                    startActivity(intent);
                }
            }
        });







    }


      private void initPersonInfos() {
       PersonInfo new_friend = new PersonInfo("新朋友",R.drawable.new_contact);
       personInfoList.add(new_friend);
       PersonInfo group = new PersonInfo("群聊",R.drawable.group_chat);
       personInfoList.add(group);
       PersonInfo contacts = new PersonInfo("昵称",R.drawable.chathead);
       personInfoList.add(contacts);
    }


}


