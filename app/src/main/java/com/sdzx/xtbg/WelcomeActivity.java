package com.sdzx.xtbg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.sdzx.xtbg.activity.LoginActivity;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.tools.ToolUtils;

import java.io.File;

public class WelcomeActivity extends Activity {
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        preferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        createFile();
        startMain();
    }

    /**
     * 创建文件夹
     */
    private void createFile() {
        File file=new File(ToolUtils.ATTACHMENT_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
    }
    /**
     * 进入下一个页面
     */
    private void startMain() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1*1000);
                    String token = preferences.getString(ConstantString.TOKEN,"");
                    //非第一次注册登录流程
                    if(token != null){ //次帐号非第一次登录
//                        Toolutils.uid = sharedPreferences.getString(ConstantString.UID,null);
//                        Toolutils.token = sharedPreferences.getString(ConstantString.TOKEN,null);
                        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else { //次帐号第一次登录
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
//    /**
//     * 初始化图片加载类
//     * */
//    private void createImageLoader(){
//        ToolUtils.imageLoader = ImageLoader.getInstance();
//        ImageLoaderConfiguration imageLoaderConfiguration=new ImageLoaderConfiguration.Builder(this).build();
//        ToolUtils.imageLoader.init(imageLoaderConfiguration);
//    }
}
