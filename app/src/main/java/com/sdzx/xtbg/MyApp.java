package com.sdzx.xtbg;

import android.os.Environment;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class MyApp {

    /**APP文件夹目录*/
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/rzcj";
    /**APP文件夹目录文件*/
    public static final String ATTACHMENT_PATH = BASE_PATH + "/attachment";
    /**用户名*/
    public static String USERNAME = "";
    /**用户ID*/
    public static String USERID = "";

    public static final String TOKEN = "";
    public static final String ACT = "act";
    public static final String STATUS = "status";

    // 本地版本号
    public static int versionCode = 1;
    // 本地版本名
    public static String versionName;
    public static String apkName="协同办公.apk";
}
