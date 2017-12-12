package com.sdzx.xtbg.tools;

import android.os.Environment;

/**
 * Created by Administrator on 2015/4/23.
 */
public class ImageLoaderPathUtils {
    /**
     * 解决ImageLoader访问网络路径和访问本地路径URL的区别问题
     * */
    public static String getImageLoaderPath(String path){
        if(null == path){
            return "";
        }
        //如果是本地路径
        if(path.contains(Environment.getExternalStorageDirectory().getPath())){
            return ToolUtils.LOADER_FILE +path;
        }
        else{
            return path;
        }
    }
 }
