package com.example.administrator.androidwechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText phone_num,pass;
    Button login;
    TextView reg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        phone_num = (EditText) findViewById(R.id.login_username);
        pass = (EditText) findViewById(R.id.login_password);
        //监听输入框
        phone_num.addTextChangedListener(this);
        pass.addTextChangedListener(this);
        //跳转到主页面
        login = (Button) findViewById(R.id.login_button);
        login.setEnabled(false);
        login.setOnClickListener(this);
        //跳转至注册页面
        reg = (TextView) findViewById(R.id.register);
        reg.setOnClickListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        login.setEnabled(false);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!(phone_num.getText().toString().trim().equals("") || pass.getText().toString().trim().equals(""))){
            login.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                //注册信息合法，跳转到登陆页面
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.register:
                //点击注册账号时，跳转到注册页面
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);

        }
    }
}
