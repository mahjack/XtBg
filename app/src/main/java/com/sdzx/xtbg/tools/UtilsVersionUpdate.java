package com.sdzx.xtbg.tools;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.MyApp;
import com.sdzx.xtbg.bean.UpdateInfo;
import com.sdzx.xtbg.constant.ConstantURL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

/**
 * 更新下载
 */
public class UtilsVersionUpdate {
    public final static String Tag = "UtilsVersionUpdate";
    private String localVersion;
    private UpdateInfo info;
    private UpdateInfo.VersionValueBean versionValue;
    private Context context;
    /** 更新进度 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;

    public UtilsVersionUpdate(Context context) {
        this.context = context;
        Update();
    }

    public void Update() {
        try {
            localVersion = getVersionName(context);
            getData();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取当前程序的版本号
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionName;
    }

    public void getData() {
        OkHttpUtils
                .get()
                .url(ConstantURL.VERSION)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    info = new Gson().fromJson(response, UpdateInfo.class);
                    versionValue=info.getVersionValue();
                    if (versionValue.getVersionOld().equals(localVersion)) {
                        Toast.makeText(context, "已是最新版本", Toast.LENGTH_SHORT).show();
                    } else{
                        showUpdataDialog();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
     *
     * 弹出对话框通知用户更新程序
     *
     * 弹出对话框的步骤：
     *  1.创建alertDialog的builder.
     *  2.要给builder设置属性, 对话框的内容,样式,按钮
     *  3.通过builder 创建一个对话框
     *  4.对话框show()出来
     */
    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setTitle("版本升级");
        builer.setMessage("发现新版本");
        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(Tag, "下载apk,更新");
                downLoadApk();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //do sth
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk() {
        final ProgressDialog pd = new ProgressDialog(context);    //进度条对话框
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();
        OkHttpUtils
                .get()
                .url(versionValue.getApkUrl())
                .build()
                .execute(new FileCallBack(Util.createFolder().getAbsolutePath(), MyApp.apkName) {
                    @Override
                    public void inProgress(float progress, long total) {
                        pd.setProgress((int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e(Tag, "onError :" + e.getMessage());
                        Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                        pd.dismiss(); //结束掉进度条对话框
                    }

                    @Override
                    public void onResponse(File response) {
                        File file=new File(response.getAbsolutePath());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        //执行动作
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                            intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file), "application/vnd.android.package-archive");
                        }else {
                            //执行的数据类型
                            intent.setDataAndType(Uri.fromFile(response), "application/vnd.android.package-archive");
                        }
                        context.startActivity(intent);
                        pd.dismiss(); //结束掉进度条对话框
                        Log.e(Tag, "onResponse :" + response.getAbsolutePath());
                    }
                });
    }


}
