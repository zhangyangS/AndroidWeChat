package com.example.administrator.androidwechat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.fragments.ChatsFragment;
import com.example.administrator.fragments.ContactsFragment;
import com.example.administrator.fragments.DiscoverFragment;
import com.example.administrator.fragments.MeFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private List<Fragment> fragmentList;
    private List<ImageView> imageViewList;
    private List<TextView> textViewList;
    private FragmentPagerAdapter adapter;

    private ImageView chatsIconSelect;
    private ImageView chatsIconNormal;
    private ImageView contactsIconSelect;
    private ImageView contactsIconNormal;
    private ImageView discoverIconSelect;
    private ImageView discoverIconNormal;
    private ImageView meIconSelect;
    private ImageView meIconNormal;

    private TextView chatsTextSelect;
    private TextView chatsTextNormal;
    private TextView contactsTextSelect;
    private TextView contactsTextNormal;
    private TextView discoverTextSelect;
    private TextView discoverTextNormal;
    private TextView meTextSelect;
    private TextView meTextNormal;

    private final int CHATS_INDEX=0;                           //微信菜单索引
    private final int CONTACTS_INDEX=1;                        //联系人菜单索引
    private final int DISCOVER_INDEX=2;                        //发现菜单索引
    private final int ME_INDEX=3;                              //我菜单索引
    private int curIndex;                                      //当前菜单索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setOverflowShowingAlways();
        initView();
    }

    //加载menu.xml文件
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //让隐藏在overflow当中的Action按钮的图标显示出来
    @Override
    public boolean onMenuOpened(int featureId,Menu menu){
        if(featureId== Window.FEATURE_ACTION_BAR && menu!=null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m=menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu,true);
                }catch(Exception e){}
            }
        }
        return super.onMenuOpened(featureId,menu);
    }

    //屏蔽掉物理menu键
    private void setOverflowShowingAlways(){
        try{
            ViewConfiguration configuration=ViewConfiguration.get(this);
            Field menuKeyField=ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(configuration,false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initView(){
        chatsIconSelect=(ImageView)findViewById(R.id.chats_icon_select);
        chatsIconNormal=(ImageView)findViewById(R.id.chats_icon_normal);
        contactsIconSelect=(ImageView)findViewById(R.id.contacts_icon_select);
        contactsIconNormal=(ImageView)findViewById(R.id.contacts_icon_normal);
        discoverIconSelect=(ImageView)findViewById(R.id.discover_icon_select);
        discoverIconNormal=(ImageView)findViewById(R.id.discover_icon_normal);
        meIconSelect=(ImageView)findViewById(R.id.me_icon_select);
        meIconNormal=(ImageView)findViewById(R.id.me_icon_normal);

        imageViewList=new ArrayList<ImageView>();
        imageViewList.add(chatsIconNormal);
        imageViewList.add(chatsIconSelect);
        imageViewList.add(contactsIconNormal);
        imageViewList.add(contactsIconSelect);
        imageViewList.add(discoverIconNormal);
        imageViewList.add(discoverIconSelect);
        imageViewList.add(meIconNormal);
        imageViewList.add(meIconSelect);

        chatsTextSelect=(TextView)findViewById(R.id.chats_text_select);
        chatsTextNormal=(TextView)findViewById(R.id.chats_text_normal);
        contactsTextSelect=(TextView)findViewById(R.id.contacts_text_select);
        contactsTextNormal=(TextView)findViewById(R.id.contacts_text_normal);
        discoverTextSelect=(TextView)findViewById(R.id.discover_text_select);
        discoverTextNormal=(TextView)findViewById(R.id.discover_text_normal);
        meTextSelect=(TextView)findViewById(R.id.me_text_select);
        meTextNormal=(TextView)findViewById(R.id.me_text_normal);

        textViewList=new ArrayList<TextView>();
        textViewList.add(chatsTextNormal);
        textViewList.add(chatsTextSelect);
        textViewList.add(contactsTextNormal);
        textViewList.add(contactsTextSelect);
        textViewList.add(discoverTextNormal);
        textViewList.add(discoverTextSelect);
        textViewList.add(meTextNormal);
        textViewList.add(meTextSelect);

        pager=(ViewPager)findViewById(R.id.pager);
        initFragments();
    }

    public void initFragments(){
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new ChatsFragment());
        fragmentList.add(new ContactsFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new MeFragment());
        adapter=new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new MyOnPageChangeListener());

        chatsIconNormal.setAlpha(0f);
        chatsTextNormal.setAlpha(0f);
        contactsIconSelect.setAlpha(0f);
        contactsTextSelect.setAlpha(0f);
        discoverIconSelect.setAlpha(0f);
        discoverTextSelect.setAlpha(0f);
        meIconSelect.setAlpha(0f);
        meTextSelect.setAlpha(0f);
    }

    public void chatsClick(View view){
        if(curIndex!=CHATS_INDEX){
            colorChange(curIndex,CHATS_INDEX,1);
            pager.setCurrentItem(CHATS_INDEX, false);
        }
    }

    public void contactsClick(View view){
        if(curIndex!=CONTACTS_INDEX){
            colorChange(curIndex,CONTACTS_INDEX,1);
            pager.setCurrentItem(CONTACTS_INDEX, false);
        }
    }

    public void discoverClick(View view){
        if(curIndex!=DISCOVER_INDEX){
            colorChange(curIndex,DISCOVER_INDEX,1);
            pager.setCurrentItem(DISCOVER_INDEX, false);
        }
    }

    public void meClick(View view){
        if(curIndex!=ME_INDEX){
            colorChange(curIndex,ME_INDEX,1);
            pager.setCurrentItem(ME_INDEX, false);
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter{

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            Log.i("color","position:"+i+"positionOffset:"+v+"positionOffsetPixels:"+i1);
            if(v>0){
                colorChange(i,i+1,v);
            }
        }

        @Override
        public void onPageSelected(int i) {
            Log.i("color","PageSelected:"+i);
            curIndex=i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            Log.i("color","ScrollState:"+i);
        }
    }

    private void colorChange(int srcIndex,int destIndex,float ratio){
        imageViewList.get(srcIndex*2).setAlpha(ratio);
        imageViewList.get(srcIndex*2+1).setAlpha(1-ratio);

        imageViewList.get(destIndex*2).setAlpha(1-ratio);
        imageViewList.get(destIndex*2+1).setAlpha(ratio);

        textViewList.get(srcIndex*2).setAlpha(ratio);
        textViewList.get(srcIndex*2+1).setAlpha(1-ratio);

        textViewList.get(destIndex*2).setAlpha(1-ratio);
        textViewList.get(destIndex*2+1).setAlpha(ratio);
    }
}
