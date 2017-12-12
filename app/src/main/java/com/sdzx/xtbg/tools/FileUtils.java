package com.sdzx.xtbg.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Version_Object;
import com.sdzx.xtbg.constant.ConstantURL;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import static com.sdzx.xtbg.tools.ToolUtils.filePath;

public class FileUtils {

    public static String SDPATH;
    private static boolean isNeed = false;

    public static String getSDPATH() {
        return SDPATH;
    }

    public static String getPath(Context context, Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public FileUtils() {
        // 得到当前外部存储设备的目录
        // /SDCARD
        SDPATH = Environment.getExternalStorageDirectory() + "/";
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public File write2SDFromInput(String path, String fileName,
                                  InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            while ((input.read(buffer)) != -1) {
                output.write(buffer);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 下载附件
     */
    public static void attachmentDownload(String url, String target) {
        // TODO 下载附件
        // 判断是否有SD卡
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            ToolUtils.isDownloading = true;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(url, target, true, true, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Log.e("文件下载完成", responseInfo.result.getPath());
                    ToolUtils.isDownloading = false;
//                    Toast.makeText(DemoApplication.getContext(), "下载完成", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    ToolUtils.isDownloading = false;
//                    Toast.makeText(DemoApplication.getContext(), "下载失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
//                    Toast.makeText(DemoApplication.getContext(), "下载中", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
//			Toast.makeText(context, "请先插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检测下载附件
     */
    public static void checkDownload(final Context context, String url, String path) {
        // TODO 检测下载
        ToolUtils.isDownloading = true;
        System.out.println("检测下载！" + url + path);
        // 判断是否有SD卡
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            long availableSize = Util.availableSize();
            int downloadLength = Util.downloadFileSize(url);
            //判断手机内存是否够下载文件
            if (availableSize >= downloadLength) {
                HttpUtils utils = new HttpUtils();
//				HttpHandler handlerDownload =
                utils.download(url, path, new RequestCallBack<File>() {
                    @Override
                    public void onStart() {
                        super.onStart();
//						Toast.makeText(context, "开始下载",
//								Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        super.onLoading(total, current, isUploading);
//						System.out.println(current + "/" + total);
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        // TODO Auto-generated method stub
                        ToolUtils.isDownloading = false;
//						Toast.makeText(context, "下载失败",
//								Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        // TODO Auto-generated method stub
                        ToolUtils.isDownloading = false;
//						Toast.makeText(context, "下载成功",
//						Toast.LENGTH_SHORT).show();
                    }
                });
//				if(false){
//					handlerDownload.cancel(); //如果支持断点下载  取消下载
//				}
            }
        } else {
            Toast.makeText(context, "请先插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    //使用String的split 方法
    public static String[] convertStrToArray(String str, String sign) {
        String[] strArray = null;
        strArray = str.split(sign); // "[|]" 拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    //使用String的split 方法
    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        try {
            strArray = str.split("[|]"); // "[|]" 拆分字符为"," ,然后把结果交给数组strArray
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strArray;
    }
    @SuppressLint("NewApi")
    public static Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            // retriever.setMode(MediaMetadataRetriever.);
            retriever.setDataSource(filePath);

            bitmap = retriever.getFrameAtTime(1000);

        } catch (Exception ex) {

        } finally {
            try {
                retriever.release();

            } catch (RuntimeException ex) {
            }

        }
        return bitmap;

    }

    /**
     * 转换成单位
     *
     * @param length
     * @return
     */
    public static String formatFileLength(long length) {
        if (length >> 30 > 0L) {
            float sizeGb = Math.round(10.0F * (float) length / 1.073742E+009F) / 10.0F;
            return sizeGb + " GB";
        }
        if (length >> 20 > 0L) {
            return formatSizeMb(length);
        }
        if (length >> 9 > 0L) {
            float sizekb = Math.round(10.0F * (float) length / 1024.0F) / 10.0F;
            return sizekb + " KB";
        }
        return length + " B";
    }

    /**
     * 转换成Mb单位
     *
     * @param length
     * @return
     */
    public static String formatSizeMb(long length) {
        float mbSize = Math.round(10.0F * (float) length / 1048576.0F) / 10.0F;
        return mbSize + " MB";
    }

    /**
     * 检查SDCARD是否可写
     *
     * @return
     */
    public static boolean checkExternalStorageCanWrite() {
        try {
            boolean mouted = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
            if (mouted) {
                boolean canWrite = new File(Environment
                        .getExternalStorageDirectory().getAbsolutePath())
                        .canWrite();
                if (canWrite) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 返回文件的图标
     *
     * @param fileName
     * @return
     */
    public static int getFileIcon(String fileName) {
        String fileType = fileName.toLowerCase();
        if (isDocument(fileType)) {
            return R.mipmap.file_attach_doc;
        }
        if (isPic(fileType)) {
            return R.mipmap.file_attach_img;
        }

        if (isCompresseFile(fileType)) {
            return R.mipmap.file_attach_rar;
        }
        if (isTextFile(fileType)) {
            return R.mipmap.file_attach_txt;
        }
        if (isPdf(fileType)) {
            return R.mipmap.file_attach_pdf;
        }

        if (isPPt(fileType)) {
            return R.mipmap.file_attach_ppt;
        }

        if (isXls(fileType)) {
            return R.mipmap.file_attach_xls;
        }
        return R.mipmap.file_attach_ohter;
    }

    /**
     * 是否图片
     *
     * @param fileName
     * @return
     */
    public static boolean isPic(String fileName) {
        String lowerCase = nullAsNil(fileName).toLowerCase();
        return lowerCase.endsWith(".bmp") || lowerCase.endsWith(".png")
                || lowerCase.endsWith(".jpg") || lowerCase.endsWith(".jpeg")
                || lowerCase.endsWith(".gif");
    }

    /**
     * 是否压缩文件
     *
     * @param fileName
     * @return
     */
    public static boolean isCompresseFile(String fileName) {
        String lowerCase = nullAsNil(fileName).toLowerCase();
        return lowerCase.endsWith(".rar") || lowerCase.endsWith(".zip")
                || lowerCase.endsWith(".7z") || lowerCase.endsWith("tar")
                || lowerCase.endsWith(".iso");
    }

    /**
     * 是否音频
     *
     * @param fileName
     * @return
     */
    public static boolean isAudio(String fileName) {
        String lowerCase = nullAsNil(fileName).toLowerCase();
        return lowerCase.endsWith(".mp3") || lowerCase.endsWith(".wma")
                || lowerCase.endsWith(".mp4") || lowerCase.endsWith(".rm");
    }

    /**
     * 是否文档
     *
     * @param fileName
     * @return
     */
    public static boolean isDocument(String fileName) {
        String lowerCase = nullAsNil(fileName).toLowerCase();
        return lowerCase.endsWith(".doc") || lowerCase.endsWith(".docx")
                || lowerCase.endsWith("wps");
    }

    /**
     * 是否Pdf
     *
     * @param fileName
     * @return
     */
    public static boolean isPdf(String fileName) {
        return nullAsNil(fileName).toLowerCase().endsWith(".pdf");
    }

    /**
     * 是否Excel
     *
     * @param fileName
     * @return
     */
    public static boolean isXls(String fileName) {
        String lowerCase = nullAsNil(fileName).toLowerCase();
        return lowerCase.endsWith(".xls") || lowerCase.endsWith(".xlsx");
    }

    /**
     * 是否文本文档
     *
     * @param fileName
     * @return
     */
    public static boolean isTextFile(String fileName) {
        String lowerCase = nullAsNil(fileName).toLowerCase();
        return lowerCase.endsWith(".txt") || lowerCase.endsWith(".rtf");
    }

    /**
     * 是否Ppt
     *
     * @param fileName
     * @return
     */
    public static boolean isPPt(String fileName) {
        String lowerCase = nullAsNil(fileName).toLowerCase();
        return lowerCase.endsWith(".ppt") || lowerCase.endsWith(".pptx");
    }

    /**
     * decode file length
     *
     * @param filePath
     * @return
     */
    public static int decodeFileLength(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return 0;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return 0;
        }
        return (int) file.length();
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * @param filePath
     * @return
     */
    public static boolean checkFile(String filePath) {
        if (TextUtils.isEmpty(filePath) || !(new File(filePath).exists())) {
            return false;
        }
        return true;
    }

    /**
     * @param filePath
     * @param seek
     * @param length
     * @return
     */
    public static byte[] readFlieToByte(String filePath, int seek, int length) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        if (length == -1) {
            length = (int) file.length();
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bs = new byte[length];
            randomAccessFile.seek(seek);
            randomAccessFile.readFully(bs);
            randomAccessFile.close();
            return bs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int copyFile(File src, String filename, byte[] buffer) {
        if (!src.exists()) {
            return -1;
        }
        return copyFile(src.getAbsolutePath(), filename, buffer);
    }

    /**
     * 拷贝文件
     *
     * @param fileDir
     * @param fileName
     * @param buffer
     * @return
     */
    public static int copyFile(String fileDir, String fileName, byte[] buffer) {
        if (buffer == null) {
            return -2;
        }

        try {
            File file = new File(fileDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            File resultFile = new File(file, fileName);
            if (!resultFile.exists()) {
                resultFile.createNewFile();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(resultFile, true));
            bufferedOutputStream.write(buffer);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return 0;

        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 根据文件名和后缀 拷贝文件
     *
     * @param fileDir
     * @param fileName
     * @param ext
     * @param buffer
     * @return
     */
    public static int copyFile(String fileDir, String fileName, String ext,
                               byte[] buffer) {
        return copyFile(fileDir, fileName + ext, buffer);
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @param type
     * @return 是否是图片结果true or false
     */
    public static boolean isImage(String type) {
        if (type != null
                && (type.equals("jpg") || type.equals("gif")
                || type.equals("png") || type.equals("jpeg")
                || type.equals("bmp") || type.equals("wbmp")
                || type.equals("ico") || type.equals("jpe"))) {
            return true;
        }
        return false;
    }

    public static String getFileExt(String fileName) {

        if (TextUtils.isEmpty(fileName)) {

            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1,
                fileName.length());
    }

    public static String getVideoMsgUrl(String url) {

        if (TextUtils.isEmpty(url)) {

            return "";
        }
        return url.substring(url.lastIndexOf("_") + 1,
                url.length());

    }

    /**
     * 将媒体选择器路径转换为绝对路径
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    /**
     * 过滤字符串为空
     *
     * @param str
     * @return
     */
    public static String nullAsNil(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }


}