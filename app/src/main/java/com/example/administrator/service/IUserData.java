package com.example.administrator.service;

import android.widget.EditText;

public interface IUserData {

    /**
     * 获取EditText中的字符串
     * @param Text
     * @return
     */
    public String getText(EditText Text);

    /**
     * 获取SharedPreferences中的手机账号和密码
     * @param phone_num
     * @param password
     */
    public void SharedPrefences(Long phone_num,String password);

}
