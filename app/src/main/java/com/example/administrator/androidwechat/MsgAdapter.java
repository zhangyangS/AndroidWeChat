package com.example.administrator.androidwechat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_layout,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Msg msg = mMsgList.get(i);
//        long systemTime = System.currentTimeMillis();
//        Date datee = new Date(systemTime);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        String time = dateFormat.format(datee);
//        boolean c = viewHolder.timeCom();
           if(msg.getType()==Msg.TYPE_RECEIVED){
               viewHolder.leftLayout.setVisibility(View.VISIBLE);
               viewHolder.rightLayout.setVisibility(View.GONE);
               viewHolder.take_photo.setVisibility(View.GONE);
//               if (c==true){
//                   viewHolder.layout_date.setVisibility(View.GONE);
//               }else {
//                   viewHolder.layout_date.setVisibility(View.VISIBLE);
//                   viewHolder.date.setText(time);
//               }
               viewHolder.leftMsg.setText(msg.getContent());
           }else if(msg.getType()==Msg.TYPE_SEND){
               viewHolder.leftLayout.setVisibility(View.GONE);
               viewHolder.rightLayout.setVisibility(View.VISIBLE);
               viewHolder.take_photo.setVisibility(View.GONE);
               viewHolder.rightMsg.setText(msg.getContent());
//               if (c==true){
//                   viewHolder.layout_date.setVisibility(View.GONE);
//               }else {
//                   viewHolder.layout_date.setVisibility(View.VISIBLE);
//                   viewHolder.date.setText(time);
//               }

           }else{
               viewHolder.leftLayout.setVisibility(View.GONE);
               viewHolder.rightLayout.setVisibility(View.GONE);
               viewHolder.take_photo.setVisibility(View.VISIBLE);
               viewHolder.imageView.setImageBitmap(msg.getBitmap());
           }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        LinearLayout layout_date;
        LinearLayout take_photo;
        TextView leftMsg;
        TextView rightMsg;
        TextView date;
        ImageView imageView;
        public ViewHolder(View view){
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            layout_date = view.findViewById(R.id.layout_date);
            take_photo = view.findViewById(R.id.take_photo);
            leftMsg = view.findViewById(R.id.left_msg);
            rightMsg = view.findViewById(R.id.right_msg);
            date = view.findViewById(R.id.date);
            imageView = (ImageView)view.findViewById(R.id.take_image);
        }
//        public boolean timeCom(){
//            boolean fatime = false;
//            String timeout = date.getText().toString();
//            if(timeout.equals("")){
//                timeout="18:40";
//            }
//            long systemTime = System.currentTimeMillis();
//            Date datee = new Date(systemTime);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//            String time = dateFormat.format(datee);
//            long a=0;
//            long b=0 ;
//            try {
//                a = dateFormat.parse(timeout).getTime();
//                b = dateFormat.parse(time).getTime();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            int minutes = (int)((b-a)/(1000*60));
//            if(minutes<2){
//                fatime =true;
//            }else{
//                fatime = false;
//                timeout = time;
//            }
//           return fatime;
//        }
   }
    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }
}
