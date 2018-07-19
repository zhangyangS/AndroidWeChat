package com.example.administrator.androidwechat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Uri headUri;
    //裁剪后图片返回码
    private static final int CROP_PICTURE = 3;
    //裁剪图片存放地址的Uri
    private Uri cropImageUri;
    private SharedPreferences register_sp;

    private MyDatabaseHelper register_contacts;
    private String nick,pass,a;
    private Long num;

    private Button register;
    private EditText edt_nickname,edt_phone_num,edt_password;
    private ImageView head_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        edt_nickname = (EditText) this.findViewById(R.id.register_nickname);
        edt_phone_num = (EditText) this.findViewById(R.id.register_phone_num);
        edt_password = (EditText) this.findViewById(R.id.register_pass);
        //监听输入框
        edt_nickname.addTextChangedListener(this);
        edt_phone_num.addTextChangedListener(this);
        edt_password.addTextChangedListener(this);
        //跳转至登陆界面
        register = (Button) findViewById(R.id.register_button);
        register.setEnabled(false);
        register.setOnClickListener(this);
        //头像选择
        head_image = (ImageView) findViewById(R.id.pic_select);
        head_image.setOnClickListener(this);

        register_contacts = new MyDatabaseHelper(this,"Message.db",null,4);


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        register.setEnabled(false);
        //在这里重复设置，以保证清除任意EditText中的内容，按钮重新变回不可点击状态
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //用户名与密码不能为空，手机号必须匹配手机号范围
        if (!edt_nickname.getText().toString().trim().equals("") && !edt_password.getText().toString().trim().equals("")
                && edt_phone_num.getText().toString().trim().matches("[1][3578]\\d{9}")){
            register.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_button:
                //注册信息合法，跳转到登陆页面
                Toast.makeText(this, "账号注册成功", Toast.LENGTH_SHORT).show();
                nick = getText(edt_nickname);
                a = getText(edt_phone_num);
                pass = getText(edt_password);
                Shared();
                insert();
                query();
                Intent loginIntent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.pic_select:
                setDialog();
                break;
            case R.id.btn_choose_img:
                //查看是否拥有权限，没有则弹框询问是否赋予权限
                if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openPhoto();
                }
                break;
            case R.id.btn_open_camera:
                // 新建一个File对象，用来存储拍照后的照片
                //File outputImage = new File("/storage/emulated/0/head_photo/output_image.jpg");
                File outputImage = new File("/storage/emulated/0","output_image.jpg");
                try {
                    if (!outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    headUri = FileProvider.getUriForFile(RegisterActivity.this,"com.example.cameeralbumtest.fileprovider",outputImage);
                }else {
                    headUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,headUri);
                startActivityForResult(intent,TAKE_PHOTO);
                break;
        }
    }
    private void Shared(){
            SharedPreferences.Editor editor  = getSharedPreferences("user_data",MODE_PRIVATE).edit();
            num = Long.parseLong(a);
            editor.putLong("phone_num",num);
            editor.putString("password",pass);
            editor.commit();
            Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
    }

    private void insert(){
        SQLiteDatabase db = register_contacts.getWritableDatabase();
        ContentValues values = new ContentValues();
        num = Long.parseLong(a);
        try {
            values.put("nickName",nick);
            values.put("phoneNum",num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        values.put("imgUrl",cropImageUri.toString());
        db.insert("Contact",null,values);
    }
    private void query(){
        SQLiteDatabase db = register_contacts.getWritableDatabase();
        Cursor cursor = db.query("Contact",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String nickN = cursor.getString(cursor.getColumnIndex("nickName"));
                Long phone_num = cursor.getLong(cursor.getColumnIndex("phoneNum"));
                String uri = cursor.getString(cursor.getColumnIndex("imgUrl"));
                Log.d("RegisterActivity", nickN + ","+ phone_num + "," + uri);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private String getText(EditText Text){
        String get = Text.getText().toString().trim();
        return get;
    }

    private void setDialog() {
        Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.camera_layout, null);
        //初始化视图
        root.findViewById(R.id.btn_choose_img).setOnClickListener(this);
        root.findViewById(R.id.btn_open_camera).setOnClickListener(this);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    public String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    startPhotoZoom(headUri);
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                        head_image.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4及以上
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
            case CROP_PICTURE:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                    head_image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openPhoto();
                }else {
                    Toast.makeText(this, "缺少打开相册权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //4.4版本以上调用handleImageOnKitKat来处理图片，4.4以后的版本，相册中返回图片的Uri是封装过的Uri
    @TargetApi(19)
    public void handleImageOnKitKat(Intent data){
        String  imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的uri，则通过取出document id进行处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                //Uri的Authority是media格式，document id需要分割字符串取出后半部分得到数字id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                //解析出数字格式的id，构建新的Uri与条件语句
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        startPhotoZoom(uri);

    }

    public void handleImageBeforeKitKat(Intent data){
        //直接将Uri传入，就能获取真实路径
        Uri uri = data.getData();
        startPhotoZoom(uri);
    }


    public void openPhoto(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        //打开相册
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    public void startPhotoZoom(Uri uri) {
        File CropPhoto=new File("/storage/emulated/0","crop_image.jpg");
        try{
            if(CropPhoto.exists()){
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        cropImageUri=Uri.fromFile(CropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }



}
