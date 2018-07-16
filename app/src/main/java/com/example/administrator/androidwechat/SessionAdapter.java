package com.example.administrator.androidwechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SessionAdapter extends ArrayAdapter<Session>{
    private int resourceId;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd");
    Date date=new Date(System.currentTimeMillis());
    String time=simpleDateFormat.format(date);
    public SessionAdapter(Context context, int textViewResourceId, List<Session> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Session session=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView image=(ImageView) view.findViewById(R.id.image);
        TextView user=(TextView) view.findViewById(R.id.user);
        TextView msg=(TextView) view.findViewById(R.id.msg);
        TextView date=(TextView) view.findViewById(R.id.date);
        image.setImageResource(session.getPath());
        user.setText(session.getUsername());
        msg.setText(session.getMsg());
        date.setText(time);
        return view;
    }
}
