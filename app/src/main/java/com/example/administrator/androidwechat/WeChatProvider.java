package com.example.administrator.androidwechat;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Message;

public class WeChatProvider extends ContentProvider {
    public static final int Contact_DIR=0;
    public static final int Contact_ITEM=1;
    public static final int Session_DIR=2;
    public static final int Session_ITEM=3;
    public static final int Message_DIR=4;
    public static final int Message_ITEM=5;
    public static final String AUTHORITY="com.example.administrator.androidwechat.provider";
    private static  UriMatcher uriMatcher;
    private MyDatabaseHelper dbHelper;
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"contact",Contact_DIR);
        uriMatcher.addURI(AUTHORITY,"contact/#",Contact_ITEM);
        uriMatcher.addURI(AUTHORITY,"session",Session_DIR);
        uriMatcher.addURI(AUTHORITY,"session/#",Session_ITEM);
        uriMatcher.addURI(AUTHORITY,"message", Message_DIR);
        uriMatcher.addURI(AUTHORITY,"message/#",Message_ITEM);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int deleteRows=0;
        switch (uriMatcher.match(uri)){
            case Contact_DIR:
                deleteRows=db.delete("Contact",selection,selectionArgs);
                break;
            case Contact_ITEM:
                String conId=uri.getPathSegments().get(1);
                deleteRows=db.delete("Contact","contactId=?",new String[]{conId});
                break;
            case Session_DIR:
                deleteRows=db.delete("Session",selection,selectionArgs);
                break;
            case Session_ITEM:
                String sesId=uri.getPathSegments().get(1);
                deleteRows=db.delete("Session","sessionId=?",new String[]{sesId});
                break;
            case Message_DIR:
                deleteRows=db.delete("Message",selection,selectionArgs);
                break;
            case Message_ITEM:
                String mesId=uri.getPathSegments().get(1);
                deleteRows=db.delete("Contact","messageId=?",new String[]{mesId});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case Contact_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.administrator.androidwechat.provider.contact";
            case Contact_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.administrator.androidwechat.provider.contact";
            case Session_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.administrator.androidwechat.provider.session";
            case Session_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.administrator.androidwechat.provider.session";
            case Message_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.administrator.androidwechat.provider.message";
            case Message_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.administrator.androidwechat.provider.message";
        }
        return  null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri uriReturn=null;
        switch (uriMatcher.match(uri)){
            case Contact_DIR:
            case Contact_ITEM:
                long newContactId=db.insert("Contact",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/contact/"+newContactId);
                break;
            case Session_DIR:
            case Session_ITEM:
                long newSessionId=db.insert("Contact",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/contact/"+newSessionId);
                break;
            case Message_DIR:
            case Message_ITEM:
                long newMessageId=db.insert("Contact",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/contact/"+newMessageId);
                break;
            default:
                break;

        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbHelper=new MyDatabaseHelper(getContext(),"Message.db",null,3);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case Contact_DIR:
                cursor=db.query("Contact",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Contact_ITEM:
                String conId=uri.getPathSegments().get(1);
                cursor=db.query("Contact",projection,"contactId=?",new String[]{conId},null,null,sortOrder);
                break;
            case Session_DIR:
                cursor=db.query("Session",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Session_ITEM:
                String sesId=uri.getPathSegments().get(1);
                cursor=db.query("Session",projection,"sessionId=?",new String[]{sesId},null,null,sortOrder);
                break;
            case Message_DIR:
                cursor=db.query("Message",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Message_ITEM:
                String mesId=uri.getPathSegments().get(1);
                cursor=db.query("Message",projection,"messageId=?",new String[]{mesId},null,null,sortOrder);
                break;
             default:
                break;

        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int updateRows=0;
        switch (uriMatcher.match(uri)){
            case Contact_DIR:
                updateRows=db.update("Contact",values,selection,selectionArgs);
                break;
            case Contact_ITEM:
                String conId=uri.getPathSegments().get(1);
                updateRows=db.update("Conatct",values,"contactId=?",new String[]{conId});
                break;
            case Session_DIR:
                updateRows=db.update("Session",values,selection,selectionArgs);
                break;
            case Session_ITEM:
                String sesId=uri.getPathSegments().get(1);
                updateRows=db.update("Session",values,"sessionId=?",new String[]{sesId});
                break;
            case Message_DIR:
                updateRows=db.update("Message",values,selection,selectionArgs);
                break;
            case Message_ITEM:
                String mesId=uri.getPathSegments().get(1);
                updateRows=db.update("Message",values,"messageId=?",new String[]{mesId});
                break;
            default:
                break;
        }
        return updateRows;
    }
}
