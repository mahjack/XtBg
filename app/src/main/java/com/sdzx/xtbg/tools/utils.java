package com.sdzx.xtbg.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;

public class utils {

    public static byte[] getBytes(InputStream is) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        is.close();
        bos.flush();
        byte[] result = bos.toByteArray();
        System.out.println(new String(result));
        return result;
    }

    /**
     * 产生随机字符串
     */
    private static Random randGen = null;
    private static char[] numbersAndLetters = null;

    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                    + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
            // numbersAndLetters =
            // ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
            // randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }

    /**
     * 将两个数组合并
     *
     * @param a
     * @param b
     * @return
     */
    public static String[] contact(String a[], String b[]) {
        String[] c = new String[a.length + b.length];
        // for (int i = 0; i < c.length; i++)
        // if (i < a.length)
        // c[i] = a[i];
        // else
        // c[i] = b[i - a.length];
        // return c;
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }
        for (int i = 0; i < b.length; i++) {
            c[a.length + i] = b[i];
        }
        return c;
    }

    /**
     * ScrollView 滑动到最后
     *
     * @param scroll
     * @param inner
     */
    public static void scrollToBottom(final HorizontalScrollView scroll,
                                      final View inner) {

        Handler mHandler = new Handler();

        mHandler.post(new Runnable() {
            public void run() {
                if (scroll == null || inner == null) {
                    return;
                }

                int offset = inner.getMeasuredWidth() - scroll.getWidth();
                if (offset < 0) {
                    offset = 0;
                }

                scroll.smoothScrollTo(offset, 0);
            }
        });
    }

    // /**
    // * 判断是否为平板
    // *
    // * @return
    // */
    // public static boolean isPad(Context context) {
    // WindowManager wm = (WindowManager) context
    // .getSystemService(Context.WINDOW_SERVICE);
    // Display display = wm.getDefaultDisplay();
    // // 屏幕宽度
    // float screenWidth = display.getWidth();
    // // 屏幕高度
    // float screenHeight = display.getHeight();
    // DisplayMetrics dm = new DisplayMetrics();
    // display.getMetrics(dm);
    // double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
    // double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
    // // 屏幕尺寸
    // double screenInches = Math.sqrt(x + y);
    // // 大于6尺寸则为Pad
    // if (screenInches >= 6.0) {
    // return true;
    // }
    // return false;
    // }

    /**
     * 打开网络设置
     *
     * @param context
     */
    public static void openNetworkSettings(final Context context) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);

        ad.setTitle("网络不可用");
        ad.setMessage("当前网络不可用");
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        ad.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                context.startActivity(new Intent(
                        android.provider.Settings.ACTION_WIFI_SETTINGS));
            }
        });
        ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        ad.create();
        ad.show();

    }

    /**
     * 判断当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetUseful(Context context) {
        //		ConnectivityManager connectivityManager = (ConnectivityManager) context
        //				.getSystemService(Context.CONNECTIVITY_SERVICE);
        //		NetworkInfo networkInfo1 = connectivityManager
        //				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //		NetworkInfo networkInfo2 = connectivityManager
        //				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //		if (false == networkInfo1.isConnected()
        //				&& false == networkInfo2.isConnected()) {
        //			return false;
        //		} else {
        //			return true;
        //		}
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 是否连接WIFI
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    // /*
    // *
    // * 从服务器取图片 参数：String类型 返回：Bitmap类型
    // */
    //
    // public static Bitmap getHttpBitmap(String urlpath) {
    // Bitmap bitmap = null;
    // try {
    // // 生成一个URL对象
    // URL url = new URL(urlpath);
    // // 打开连接
    // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    // // conn.setConnectTimeout(6*1000);
    // // conn.setDoInput(true);
    // conn.connect();
    // // 得到数据流
    // InputStream inputstream = conn.getInputStream();
    // BitmapFactory.Options options = new BitmapFactory.Options();
    // options.inJustDecodeBounds = false;
    // options.inSampleSize = 2;
    // bitmap = BitmapFactory.decodeStream(inputstream, null, options);
    // // 关闭输入流
    // inputstream.close();
    // // 关闭连接
    // conn.disconnect();
    // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
    // 设置日期格式
    // System.out.println("time1:" + df.format(new Date()));// new
    // // Date()为获取当前系统时间
    // } catch (Exception e) {
    // Log.i("MyTag", "error:" + e.toString());
    // }
    // return bitmap;
    // }
    // 获取版本号
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 获取版本名
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }
}
