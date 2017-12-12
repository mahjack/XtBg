package com.sdzx.xtbg.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.sdzx.xtbg.MyApp;
import com.sdzx.xtbg.dialog.ECProgressDialog;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Util {
	private static Util util;
	private Util(){

	}

	public static Util getInstance(){
		if(util == null){
			util = new Util();
		}
		return util;
	}

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
	/**
	 * 判断是否有sdcard
	 * @return
	 */
	public boolean hasSDCard(){
		boolean b = false;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			b = true;
		}
		return b;
	}

	/**
	 * 得到sdcard路径
	 * @return
	 */
	public String getExtPath(){
		String path = "";
		if(hasSDCard()){
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}

	/**
	 * 得到/data/data/yanbin.imagedownload目录
	 * @param mActivity
	 * @return
	 */
	public String getPackagePath(Activity mActivity){
		return mActivity.getFilesDir().toString();
	}

	/**
	 * 根据url得到图片名
	 * @param url
	 * @return
	 */
	public String getImageName(String url) {
		String imageName = "";
		if(url != null){
			imageName = url.substring(url.lastIndexOf("/") + 1);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&"+imageName);
		}
		return imageName;
	}
	/**
	 * 获取网络文件大小
	 *
	 * @param path
	 *            文件链接
	 * @return 文件大小
	 */
	public static int downloadFileSize(final String path) {
		final int[] length = {0};
		new Thread() {
			public void run() {
				try {
					URL url = new URL(path);     //创建url对象
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();     //建立连接
					conn.setRequestMethod("POST");    //设置请求方法
					conn.setReadTimeout(5000);       //设置响应超时时间
					conn.setConnectTimeout(5000);   //设置连接超时时间
					conn.connect();   //发送请求
					int responseCode = conn.getResponseCode();    //获取响应码
					if (responseCode == 200) {   //响应码是200(固定值)就是连接成功，否者就连接失败
						length[0] = conn.getContentLength();    //获取文件的大小
					} else {
						//						Toast.makeText(context, "连接失败", Toast.LENGTH_LONG)
						//								.show();
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
		return length[0];
	}
	/**
	 * 获取手机剩余内存大小
	 *
	 * @return 手机剩余内存(单位：byte)
	 */
	public static long availableSize() {
		// 取得SD卡文件路径
		File file = Environment.getExternalStorageDirectory();
		StatFs fs = new StatFs(file.getPath());
		// 获取单个数据块的大小(Byte)
		int blockSize = fs.getBlockSize();
		// 空闲的数据块的数量
		long availableBlocks = fs.getAvailableBlocks();
		return blockSize * availableBlocks;
	}
	public static Intent getIntent(String param){
		if(param.contains(".html")){
			//android获取一个用于打开HTML文件的intent
			Uri uri = Uri.parse(param ).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param ).build();
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.setDataAndType(uri, "text/html");
			return intent;
		}else if(param.contains(".jpg") || param.contains(".png") || param.contains(".jpeg")){
			//android获取一个用于打开图片文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "image/*");
			return intent;
		}else if (param.contains(".pdf")) {
			//android获取一个用于打开PDF文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "application/pdf");
			return intent;
		}else if (param.contains(".txt")) {
			//android获取一个用于打开文本文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (false){
				Uri uri1 = Uri.parse(param );
				intent.setDataAndType(uri1, "text/plain");
			}else{
				Uri uri2 = Uri.fromFile(new File(param ));
				intent.setDataAndType(uri2, "text/plain");
			}
			return intent;
		}else if (param.contains(".mp3")) {
			//android获取一个用于打开音频文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("oneshot", 0);
			intent.putExtra("configchange", 0);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "audio/*");
			return intent;
		}else if (param.contains(".avi")) {
			//android获取一个用于打开视频文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("oneshot", 0);
			intent.putExtra("configchange", 0);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "video/*");
			return intent;
		}else if (param.contains(".doc")) {
			//android获取一个用于打开Word文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "application/msword");
			return intent;
		}else if (param.contains(".xls")) {
			//android获取一个用于打开Excel文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "application/vnd.ms-excel");
			return intent;
		}else if (param.contains(".ppt")) {
			//android获取一个用于打开PPT文件的intent .ppt 编辑模式 *.pps 放映模式 *.pptx 编辑模式 *.ppsx 放映模式
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
			return intent;
		}else if(param.contains("")){
			//android获取一个用于打开CHM文件的intent
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri, "application/x-chm");
			return intent;
		}else {
			return null;
		}
	}

	// Html 图文显示
	public static void showHtml(final String ware_description, final TextView ware_content){
		ware_content.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x101) {
					ware_content.setText((CharSequence) msg.obj);
				}
				super.handleMessage(msg);
			}
		};

		// 因为从网上下载图片是耗时操作 所以要开启新线程
		Thread t = new Thread(new Runnable() {
			Message msg = Message.obtain();

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/**
				 * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
				 * fromHtml (String source, Html.ImageGetterimageGetter,
				 * Html.TagHandler
				 * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
				 * (String source)方法中返回图片的Drawable对象才可以。
				 */
				Html.ImageGetter imageGetter = new Html.ImageGetter() {

					@Override
					public Drawable getDrawable(String source) {
						// TODO Auto-generated method stub
						URL url;
						Drawable drawable = null;
						try {
							if(source.contains("http")){
								url = new URL(source);
//								FileUtils.checkDownload(context,source,"/sdcard/"+file_name);
								drawable = Drawable.createFromStream(
										url.openStream(), null);
								drawable.setBounds(0, 0,
										drawable.getIntrinsicWidth(),
										drawable.getIntrinsicHeight());
							}else {
								url = new URL(MyApp.BASE_PATH+source);
								drawable = Drawable.createFromStream(
										url.openStream(), null);
								drawable.setBounds(0, 0,
										drawable.getIntrinsicWidth(),
										drawable.getIntrinsicHeight());
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return drawable;
//						return null;
					}
				};
				CharSequence test = Html.fromHtml(ware_description, imageGetter, null);
				msg.what = 0x101;
				msg.obj = test;
				handler.sendMessage(msg);
			}
		});
		t.start();
	}
	public static void dismissPostingDialog( ECProgressDialog progressDialog) {
		if(progressDialog == null || !progressDialog.isShowing()) {
			return ;
		}
		progressDialog.dismiss();
		progressDialog = null;
	}
	/**
	 * 创建一个文件夹
	 *
	 * @return
	 */
	public static final File createFolder() {
		File file = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			//得到一个路径，sdcard/zxkj
			String path = Environment.getExternalStorageDirectory().getPath() + "/zxkj";
			file = new File(path);
			if (!file.exists()) {
				//若不存在，创建目录
				file.mkdirs();
			}
		}
		return file;
	}

}
