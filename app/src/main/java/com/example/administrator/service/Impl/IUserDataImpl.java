package com.example.administrator.service.Impl;


import android.content.SharedPreferences;
import android.widget.EditText;

import com.example.administrator.service.IUserData;

import static android.content.Context.MODE_PRIVATE;

public class IUserDataImpl implements IUserData {
    @Override
    public String getText(EditText Text) {
        String Edit_String = Text.getText().toString().trim();
        return Edit_String;
    }

    @Override
    public void SharedPrefences(Long phone_num, String password) {
        /*
        SharedPreferences pref = getSharedPreferences("user_data",MODE_PRIVATE);
        phone_num = pref.getLong("phone_num",0);
        password = pref.getString("password","");
        */
    }
}
