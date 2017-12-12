package com.sdzx.xtbg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.sdzx.xtbg.MyApp;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.sliding.About_Activity;
import com.sdzx.xtbg.activity.sliding.FeedbackActivity;
import com.sdzx.xtbg.activity.sliding.Log_Operation_Activity;
import com.sdzx.xtbg.activity.sliding.Logged_Activity;
import com.sdzx.xtbg.activity.sliding.QR_Activity;
import com.sdzx.xtbg.activity.sliding.SettingActivity;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.fragment.Fragment_Address_new;
import com.sdzx.xtbg.fragment.Fragment_Home;
import com.sdzx.xtbg.fragment.Fragment_Mail;
import com.sdzx.xtbg.fragment.Fragment_Notices;
import com.sdzx.xtbg.tools.Util;
import com.sdzx.xtbg.view.DragLayout;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    // 日常
    @Bind(R.id.re_daily)
    RelativeLayout re_daily;
    @Bind(R.id.iv_daily)
    ImageView iv_daily;
    @Bind(R.id.tv_daily)
    TextView tv_daily;
    // 管理
    @Bind(R.id.re_manage)
    RelativeLayout re_manage;
    @Bind(R.id.iv_manage)
    ImageView iv_manage;
    @Bind(R.id.tv_manage)
    TextView tv_manage;
    // 通讯录
    @Bind(R.id.re_book)
    RelativeLayout re_book;
    @Bind(R.id.iv_book)
    ImageView iv_book;
    @Bind(R.id.tv_book)
    TextView tv_book;
    // 工具
    @Bind(R.id.re_tool)
    RelativeLayout re_tool;
    @Bind(R.id.iv_tool)
    ImageView iv_tool;
    /**侧栏*/
    @Bind(R.id.sliding_rl)
    RelativeLayout sliding_rl;
    @Bind(R.id.tv_tool)
    TextView tv_tool;
    @Bind(R.id.dl)
    DragLayout main_dl;
    // 账户
    @Bind(R.id.sliding_ll)
    RelativeLayout sliding_ll;
    // 列表
    @Bind(R.id.sliding_lv)
    ListView sliding_lv;
    // 头像
    @Bind(R.id.sliding_icon)
    ImageView sliding_icon;
    // 二维码
    @Bind(R.id.sliding_qr)
    ImageView sliding_qr;
    // 用户名
    @Bind(R.id.sliding_username)
    TextView sliding_username;
    // 设置
    @Bind(R.id.sliding_setting)
    LinearLayout sliding_setting;
    /**
     * 顶栏
     */
    //  标题
    @Bind(R.id.header_title)
    TextView header_title;
    // 头像
    @Bind(R.id.header_icon)
    ImageView header_icon;
    // 对象
    private Fragment[] fragments;
    private Fragment_Home fragment_home; // 首页
    private Fragment_Notices conversationListFragment;
    private Fragment_Mail fragment_mail; // 邮件
    private Fragment_Address_new fragment_address; // 通讯录
    private ImageView[] imageViews; // 底部导航图标
    private TextView[] textViews; // 底部导航文字
    private int index;
    private int fragment_index; // 当前 Fragment 下标
    private long exitTime = 0; // 两次点击相隔时间
    private Context context;
    private SharedPreferences preferences;

    protected static final String TAG = "MainActivity";
    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    private TextView unreadAddressLable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initConstants();
        initDragLayout();
        initFragment(); // 初始化 Fragment
        iniView();
    }
    /**
     * 初始化变量
     */
    private void initConstants() {
        context = MainActivity.this;
        preferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        MyApp.versionCode = Util.getVersionCode(context);
        MyApp.versionName = Util.getVersionName(context);
        // Activity管理器
        ActivityTaskManager.getInstance().putActivity("MainActivity", MainActivity.this);
//        new UtilsVersionUpdate(this);//检查版本更新
    }
    /**
     * 初始化 Draglayout
     */
    private void initDragLayout() {
        //TODO 初始Draglayout
        main_dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                sliding_lv.smoothScrollToPosition(new Random().nextInt(30));
            }
            @Override
            public void onClose() {
                shake();
            }
            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(header_icon, 1 - percent);
            }
        });
    }

    private void initFragment() {
        fragment_home = new Fragment_Home();
        conversationListFragment = new Fragment_Notices();
        fragment_mail = new Fragment_Mail();
        fragment_address = new Fragment_Address_new();
        fragments = new Fragment[]{fragment_home, conversationListFragment,fragment_address};
        imageViews = new ImageView[]{iv_daily, iv_manage, iv_tool};
        imageViews[0].setSelected(true);
        textViews = new TextView[]{tv_daily, tv_manage, tv_tool};
        textViews[0].setTextColor(getResources().getColor(R.color.gold));
        // 添加显示 第一个Fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment_home)
                .add(R.id.fragment_container, conversationListFragment)
                .add(R.id.fragment_container, fragment_address)
                .hide(fragment_address).hide(conversationListFragment)
                .show(fragment_home).commit();
    }
    /**
     * 初始化 控件
     */
    private void iniView() {
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        header_title.setText(getString(R.string.daily_title));
        header_icon.setOnClickListener(this);
        sliding_username.setText(preferences.getString(ConstantString.NAME, ""));
        sliding_lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_text, new String[]{"登录日志", "操作日志",
                "意见反馈"}));
        sliding_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (position == 0) {
                    // 登录日志
                    startActivity(new Intent(context, Logged_Activity.class));
                } else if (position == 1) {
                    // 操作日志
                    startActivity(new Intent(context, Log_Operation_Activity.class));
                } else if (position == 2) {
                    // 意见反馈
                    startActivity(new Intent(context, FeedbackActivity.class));
                }
                else {
                    // 关于
                    startActivity(new Intent(context, About_Activity.class));
                }
            }
        });
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) sliding_rl.getLayoutParams();
        lp.width = this.getWindowManager().getDefaultDisplay().getWidth() * 2 / 3;
        lp.height = this.getWindowManager().getDefaultDisplay().getHeight() * 5 / 6;
        sliding_rl.setLayoutParams(lp);
        sliding_ll.setOnClickListener(this);
        sliding_setting.setOnClickListener(this);
        sliding_qr.setOnClickListener(this);
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_daily:
                index = 0;
                header_icon.setVisibility(View.VISIBLE);
                header_title.setText(getString(R.string.daily_title));
                break;
            case R.id.re_manage:
                index = 1;
                header_icon.setVisibility(View.GONE);
                header_title.setText("信息中心");
                break;

            case R.id.re_tool:
                index =2;
                header_icon.setVisibility(View.GONE);
                header_title.setText(getString(R.string.tool_title));
                break;
        }
        if(fragment_index != index){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[fragment_index]);
            if(!fragments[index].isAdded()){
                ft.add(R.id.fragment_container,fragments[index]);
            }
            ft.show(fragments[index]).commit();
        }
        imageViews[fragment_index].setSelected(false);
        textViews[fragment_index].setTextColor(getResources().getColor(R.color.white));
        // 当前 Tab 选中
        imageViews[index].setSelected(true);
        textViews[index].setTextColor(getResources().getColor(R.color.gold));
        fragment_index = index;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(false);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 头像震动
     */
    private void shake() {
        header_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_icon:
                main_dl.open();
                break;
            case R.id.sliding_ll:
                break;
            case R.id.sliding_qr:
                startActivity(new Intent(context, QR_Activity.class));
                break;
            case R.id.sliding_setting:
                // 设置
                startActivity(new Intent(context, SettingActivity.class));
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (!isConflict && !isCurrentAccountRemoved) {
//            updateUnreadLabel();
//            updateUnreadAddressLable();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putBoolean("isConflict", isConflict);
//        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
//        super.onSaveInstanceState(outState);
    }

}
