package com.example.administrator.androidwechat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper{
    public static final String CREATE_Contact="create table Contact("
            +"contactId integer primary key autoincrement,"
            +"nickName text,"
            +"phoneNum integer,"
            +"imgUrl text)";
    public static final String CREATE_Session="create table Session("
            +"sessionId integer primary key autoincrement,"
            +"contactId integer,"
            +"content text,"
            +"time date,"
            +"type integer,"
            +"sessionType integer)";
    public static final String CREATE_Message="create table Message("
            +"messageId integer primary key autoincrement,"
            +"type integer,"
            +"content text,"
            +"date date,"
            +"sessionId integer,"
            +"sendId integer,"
            +"direction integer,"
            +"path text)";
    private Context mContext;
    public  MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Contact);
        db.execSQL(CREATE_Session);
        db.execSQL(CREATE_Message);
        Toast.makeText(mContext,"Create succeed",Toast.LENGTH_SHORT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Contact");
        db.execSQL("drop table if exists Session");
        db.execSQL("drop table if exists Message");

    }
}
