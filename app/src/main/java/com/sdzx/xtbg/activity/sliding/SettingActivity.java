package com.sdzx.xtbg.activity.sliding;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.MyApp;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.ActivityTaskManager;
import com.sdzx.xtbg.activity.LoginActivity;
import com.sdzx.xtbg.dialog.AlertDialog;
import com.sdzx.xtbg.tools.DataCleanManager;
import com.sdzx.xtbg.tools.UtilsVersionUpdate;

import java.io.File;

/**
 * 设置 Activity
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 */
public class SettingActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SettingActivity";
    private static final int SETTING_CHECK = 3;
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    @ViewInject(R.id.setting_chat)
    RelativeLayout setChat;

    @ViewInject(R.id.ll_change_pwd)
    RelativeLayout llChangePwd;// 修改密码

    @ViewInject(R.id.setting_clean_cache)
    RelativeLayout settingCleanCache;// 清除缓存

    @ViewInject(R.id.setting_check_update)
    RelativeLayout settingCheckUpdate;// 检查更新

    // 退出登录
    @ViewInject(R.id.setting_exit)
    RelativeLayout setting_exit;
    // 对象
    private Context context;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_setting);
        ViewUtils.inject(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        preferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        context = SettingActivity.this;
    }

    private void initViews() {
        header_title.setText(getString(R.string.sliding_setting));
        header_right.setVisibility(View.GONE);
        setChat.setVisibility(View.GONE);
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
        setting_exit.setOnClickListener(this);
        setChat.setOnClickListener(this);
        settingCheckUpdate.setOnClickListener(this);
        settingCleanCache.setOnClickListener(this);
        llChangePwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;

            case R.id.setting_clean_cache:
                try {
                    String size = "缓存大小（" +
                            DataCleanManager.getFormatSize(DataCleanManager.getFolderSize(new File(MyApp.BASE_PATH))) + ")";
                    new AlertDialog(context).builder()
                            .setMsg(size)
                            .setTitle("您确定要清除缓存吗？")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DataCleanManager.deleteFolderFile(MyApp.BASE_PATH, true);
                                }
                            })
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setting_check_update:
                new UtilsVersionUpdate(context);
                break;
            case R.id.setting_exit:
                // 弹出确认Dialog

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("退出？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferences.edit().clear().commit();
                        startActivity(new Intent(context, LoginActivity.class));
                        ActivityTaskManager.getInstance().removeActivity("MainActivity");
                        finish();
                    }
                });
                builder.show();
                break;
            case R.id.setting_chat:
//                startActivity(new Intent(context, SplashActivity.class));
                break;
            case R.id.ll_change_pwd:
                // 修改密码
                startActivity(new Intent(context, ChangePasswordActivity.class));
                break;
        }
    }


}
