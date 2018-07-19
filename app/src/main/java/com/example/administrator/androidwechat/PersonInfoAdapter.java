package com.example.administrator.androidwechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PersonInfoAdapter extends ArrayAdapter {
    private int resourceId;
    public PersonInfoAdapter(Context context, int textViewPersonResourceId,
                             List<PersonInfo> objects){
        super(context,textViewPersonResourceId,objects);
        resourceId = textViewPersonResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonInfo personInfo = (PersonInfo) getItem(position);  //获取当前项的PersonInfo实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView Img = (ImageView) view.findViewById(R.id.img);
        TextView NickName = (TextView) view.findViewById(R.id.nikname);
        Img.setImageResource(personInfo.getImg());
        NickName.setText(personInfo.getPersonName());
        return view;
    }
}
