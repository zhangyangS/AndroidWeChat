package com.example.administrator.androidwechat;

import android.graphics.Bitmap;

import java.util.Date;

public class Msg {

    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND= 1;
    public static final int TYPE_PHOTO=2;
    private Bitmap bitmap;
    private String content;
    private int type;
    private String date;

    public Msg(String content,int type){
        this.content = content;
        this.type = type;
    }
    public Msg(Bitmap bitmap,int type){
        this.bitmap = bitmap;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
