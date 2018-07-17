package com.example.administrator.androidwechat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView msgRecyclerView;
    private MsgAdapter msgAdapter;
    private EditText inputText;
    private ImageButton send;
    private List<Msg> msgList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar();
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.chat_gone);
        final LinearLayout lay_voice = (LinearLayout)findViewById(R.id.lay_voice);
        final LinearLayout key = (LinearLayout)findViewById(R.id.key);
        ImageButton plus2 = (ImageButton)findViewById(R.id.plus2);
        ImageButton back_key = (ImageButton)findViewById(R.id.back_key);
        inputText = (EditText) findViewById(R.id.message);
        send = (ImageButton)findViewById(R.id.send);
        ImageButton plus = (ImageButton)findViewById(R.id.plus);
        ImageButton voice = (ImageButton)findViewById(R.id.voice);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        msgAdapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(msgAdapter);

        if(actionBar!=null){
            actionBar.hide();
        }
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
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                }else{
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lay_voice.getVisibility()==View.VISIBLE && key.getVisibility()==View.GONE){
                    lay_voice.setVisibility(View.GONE);
                    key.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }else{
                    lay_voice.setVisibility(View.VISIBLE);
                    key.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        back_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(key.getVisibility()==View.VISIBLE && lay_voice.getVisibility()==View.GONE){
                    key.setVisibility(View.GONE);
                    lay_voice.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }else{
                    key.setVisibility(View.VISIBLE);
                    lay_voice.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
//                SendMsg sendMsg = new SendMsg();
//                sendMsg.sendMessage(content);

                if(!"".equals(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                }
                inputText.setText("");
            }
        });
    }
}
