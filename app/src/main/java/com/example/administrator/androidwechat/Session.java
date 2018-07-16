package com.example.administrator.androidwechat;

import android.net.Uri;

import java.util.Date;

public class Session {
    private int path;
    private String username;
    private String msg;

    public Session(int path,String username, String msg ) {
        this.username = username;
        this.path = path;
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public int getPath() {
        return path;
    }

    public String getMsg() {
        return msg;
    }
}
