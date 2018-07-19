package com.example.administrator.provider;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.example.administrator.androidwechat.R;

public class MyActionProvider extends ActionProvider {

    private Context context;

    public MyActionProvider(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean hasSubMenu(){
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu){
        subMenu.clear();
        //***************添加subMenu******************
        subMenu.add(context.getString(R.string.group_chat))
                .setIcon(R.mipmap.group_chat)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });
        subMenu.add(context.getString(R.string.add_friend))
                .setIcon(R.mipmap.add_friend)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });
        subMenu.add(context.getString(R.string.scan))
                .setIcon(R.mipmap.scan)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });
        subMenu.add(context.getString(R.string.gather_payment))
                .setIcon(R.mipmap.gather_payment)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });
        super.onPrepareSubMenu(subMenu);
    }
}
