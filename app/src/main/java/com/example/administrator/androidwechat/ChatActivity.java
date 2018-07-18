package com.example.administrator.androidwechat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    private RecyclerView msgRecyclerView;
    private MsgAdapter msgAdapter;
    private EditText inputText;
    private ImageButton send;
    public static final int CHOOSE_PHOTO=2;
    private Uri imageUri;
    private Uri uri;
    private List<Msg> msgList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar();
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.chat_gone);
        final LinearLayout lay_voice = (LinearLayout) findViewById(R.id.lay_voice);
        final LinearLayout key = (LinearLayout) findViewById(R.id.key);
        ImageButton plus2 = (ImageButton) findViewById(R.id.plus2);
        ImageButton back_key = (ImageButton) findViewById(R.id.back_key);
        ImageButton photos = (ImageButton) findViewById(R.id.photos);
        ImageButton take_photos = (ImageButton)findViewById(R.id.take_photos);
        inputText = (EditText) findViewById(R.id.message);
        send = (ImageButton) findViewById(R.id.send);
        ImageButton plus = (ImageButton) findViewById(R.id.plus);
        ImageButton voice = (ImageButton) findViewById(R.id.voice);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        msgAdapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(msgAdapter);

        if (actionBar != null) {
            actionBar.hide();
        }
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.setVisibility(View.GONE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.setVisibility(View.GONE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lay_voice.getVisibility() == View.VISIBLE && key.getVisibility() == View.GONE) {
                    lay_voice.setVisibility(View.GONE);
                    key.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                } else {
                    lay_voice.setVisibility(View.VISIBLE);
                    key.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        back_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (key.getVisibility() == View.VISIBLE && lay_voice.getVisibility() == View.GONE) {
                    key.setVisibility(View.GONE);
                    lay_voice.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                } else {
                    key.setVisibility(View.VISIBLE);
                    lay_voice.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
            }
        });
        take_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(ChatActivity.this, "com.example.administrator.androidwechat.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ChatActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
            }
        });

    }
        private void openAlbum(){
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent,CHOOSE_PHOTO);
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode){
                case 1:
                    if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                        openAlbum();
                    }else{
                        Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            int degree=0;
            switch (requestCode){
                case TAKE_PHOTO:
                    if(resultCode == RESULT_OK){
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            int h = bitmap.getHeight();
                            int w = bitmap.getWidth();
                            // 缩略图缩放的比例尺
                            int THUMB_SIZE;
                            THUMB_SIZE = 5;
                            // 对原始图片Bitmap等比例缩小5倍的缩略图
                            Bitmap bmp2 = ThumbnailUtils.extractThumbnail(bitmap, w / THUMB_SIZE, h
                                    / THUMB_SIZE);
                            Msg msg1 = new Msg(bmp2,Msg.TYPE_PHOTO);
                            msgList.add(msg1);
                            msgAdapter.notifyItemInserted(msgList.size()-1);
                            msgRecyclerView.scrollToPosition(msgList.size()-1);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CHOOSE_PHOTO:
                    if(resultCode == RESULT_OK){
                        if(Build.VERSION.SDK_INT>=19) {
                            handleImageOnKitKat(data);
                        }else{
                            handleImageBeforeKitKat(data);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        @TargetApi(19)
        private void handleImageOnKitKat(Intent data){
            String imagePath = null;
            Uri uri = data.getData();
            if(DocumentsContract.isDocumentUri(this,uri)){
                String docId = DocumentsContract.getDocumentId(uri);
                if("com.android.providers.media.documents".equals(uri.getAuthority())){
                    String id = docId.split(":")[1];//解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://dowloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                //content类型的uri,用普通方式处理
                imagePath = getImagePath(uri,null);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                //file类型的Uri,直接获取图片路径
                imagePath = uri.getPath();
            }
            displayImage(imagePath);
        }
        private void handleImageBeforeKitKat(Intent data){
            Uri uri = data.getData();
            String imagePath = getImagePath(uri,null);
            displayImage(imagePath);
        }
        private String getImagePath(Uri uri,String selection){
            String path = null;
            Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
            return path;
        }
        private void displayImage(String imagePath){
            if(imagePath!=null){
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                int h = bitmap.getHeight();
                int w = bitmap.getWidth();
                // 缩略图缩放的比例尺
                int THUMB_SIZE;
                THUMB_SIZE = 1;
                // 对原始图片Bitmap等比例缩小5倍的缩略图
                Bitmap bmp2 = ThumbnailUtils.extractThumbnail(bitmap, w / THUMB_SIZE, h
                        / THUMB_SIZE);
                Msg msg2 = new Msg(bmp2,Msg.TYPE_PHOTO);
                msgList.add(msg2);
                msgAdapter.notifyItemInserted(msgList.size()-1);
                msgRecyclerView.scrollToPosition(msgList.size()-1);

            }else{
                Toast.makeText(this,"faile to get image",Toast.LENGTH_SHORT).show();
            }
        }
    }

