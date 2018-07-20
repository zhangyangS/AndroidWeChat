package com.example.administrator.androidwechat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class MsgNotification extends AppCompatActivity implements View.OnClickListener{

    private String channelId = "mychannel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_notification_layout);

        final LinearLayout ln = (LinearLayout) findViewById(R.id.linearLayout_notification);
        Switch sn = (Switch) findViewById(R.id.switch_notification);

        sn.setOnClickListener(this);

        //Toast.makeText(this, ""+sn.isChecked(), Toast.LENGTH_SHORT).show();

        sn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               // Toast.makeText(MsgNotification.this, ""+b, Toast.LENGTH_SHORT).show();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getApplicationContext(),channelId)
                        .setContentTitle("提醒")
                        .setContentText("有新消息")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .build();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId,"channel name",NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("This is my channel description");
                    manager.createNotificationChannel(channel);
                }
                manager.notify(1,notification);
            }
        });

        final LinearLayout ld = (LinearLayout) findViewById(R.id.linearLayout_msg_details);
        final LinearLayout ls = (LinearLayout) findViewById(R.id.linearLayout_msg_sound);
        Switch ss = (Switch) findViewById(R.id.switch_msg_sound);
        final LinearLayout lh = (LinearLayout) findViewById(R.id.linearLayout_msg_hint);

        sn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ld.getVisibility() == View.VISIBLE && ls.getVisibility() == View.VISIBLE){
                    ld.setVisibility(View.GONE);
                    ls.setVisibility(View.GONE);
                    ln.setVisibility(View.VISIBLE);

                }else {
                    ld.setVisibility(View.VISIBLE);
                    ls.setVisibility(View.VISIBLE);
                    ln.setVisibility(View.VISIBLE);
                }
            }
        });

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lh.getVisibility()==View.VISIBLE){
                    lh.setVisibility(View.GONE);
                }else {
                  lh.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switch_notification:
                break;
            default:
                break;
        }
    }
}
