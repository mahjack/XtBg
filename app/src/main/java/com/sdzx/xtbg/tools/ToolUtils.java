package com.sdzx.xtbg.tools;

import android.os.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：Mark
 * Date：2015/12/25 0025
 * Tell：15006330640
 */
public class ToolUtils {
    //使用imageloading加载本地图片的时候的前缀
    public static final String LOADER_FILE = "file:/";
    //图片加载类
//    public static ImageLoader imageLoader;
    public static String apkUrl = "";
    // 本地版本号
    public static int versionCode = 1;
    // 本地版本名
    public static String versionName;
    public static Map<String, String> file_path = new HashMap<String, String>();
    public static List<String> filePath = new ArrayList<String>();
    public static boolean isDownloading = false;
    public static String uid = "";
    public static String token = "";
    // 资源文件夹
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/rzcj";
    // 附件文件夹
    public static final String ATTACHMENT_PATH = BASE_PATH + "/attachment";
}
