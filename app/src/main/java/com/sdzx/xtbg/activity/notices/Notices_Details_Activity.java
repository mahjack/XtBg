package com.sdzx.xtbg.activity.notices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.document.ImagePagerActivity;
import com.sdzx.xtbg.activity.mail.Mail_Forward_Activity;
import com.sdzx.xtbg.adapter.Attachment_Adapter;
import com.sdzx.xtbg.bean.Ann_Det;
import com.sdzx.xtbg.bean.Ann_Object;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.ToolUtils;
import com.sdzx.xtbg.tools.Util;
import com.sdzx.xtbg.tools.utils;
import com.sdzx.xtbg.view.NoScrollListView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Author：Mark
 * Date：2015/12/9 0009
 * Tell：15006330640
 * <p/>
 * 并读详情
 */
public class Notices_Details_Activity extends Activity implements View.OnClickListener {
    private static final String TAG = "Notices_Details_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    @ViewInject(R.id.notices_title)
    TextView notices_title;
    @ViewInject(R.id.notices_time)
    TextView notices_time;
    @ViewInject(R.id.notices_content)
    TextView notices_content;
    @ViewInject(R.id.notices_list)
    NoScrollListView noticesList;
    //    @ViewInject(R.id.notices_web)
//    WebView noticesWeb;
    // 对象
    private Context context;
    private String id, title, time;
    private SharedPreferences preferences;
    private Ann_Object object;
    private Attachment_Adapter adapter;
    private List<String> paths = new ArrayList<String>();
    private String[] str;
    private List<String> imagePath = new ArrayList<String>();
    private List<String> attachmentPath = new ArrayList<String>();
    private String file_path = "";
    private int file_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.notices_details);
        ViewUtils.inject(this);
        initConstants();
        readData();
        initViews();
        initEvents();
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = Notices_Details_Activity.this;
        Intent intent = super.getIntent();
        id = intent.getStringExtra(ConstantString.ID);
        title = intent.getStringExtra(ConstantString.TITLE);
        time = intent.getStringExtra(ConstantString.TIME);
    }

    private void initViews() {
        header_title.setText("必读详情");
        header_right.setVisibility(View.GONE);
        notices_title.setText(title);
        notices_time.setText("发布时间：" +time);
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
    }

    /**
     * 读取数据
     */
    private void readData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, "show");
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID, ""));
        params.addQueryStringParameter(ConstantString.ID, id);
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.BASE_URL+"tongzhi.php", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                KLog.json("综合必读详情------>", responseInfo.result);
                Gson gson = new Gson();
                Ann_Det det = gson.fromJson(responseInfo.result, Ann_Det.class);
                if (det != null) {

                    object = det.getInfo();
                    initData();
                } else {
                    Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initData() {
        if (object != null) {
            if (object.getContent() != null && !object.getContent().equals("")) {
                Util.showHtml(object.getContent(), notices_content);
            }
            if (object.getNewName() != null && !object.getNewName().equals("")) {
                str = FileUtils.convertStrToArray(object.getNewName(), "[|]");
                for (int i = 0; i < str.length; i++) {
                    KLog.d("地址：", str[i]);
                    if (str[i].contains(".jpg") || str[i].contains(".jpeg") || str[i].contains(".png")) {
                        file_type = 1;
                    } else {

                        file_type = 2;
                    }
                    String split = ":/";
                    StringTokenizer token = new StringTokenizer(str[i], split);
                    String file_name = "";
                    while (token.hasMoreTokens()) {
                        file_name = token.nextToken();
                    }
                    file_path = ToolUtils.ATTACHMENT_PATH + "/" + file_name;
                    if (utils.isNetUseful(context)) {
                        if (FileUtils.fileIsExists(file_path)) {
                            // 如果存在
                            KLog.d("文件", "存在");
                        } else {
                            // 如果不存在 下载
//                            FileUtils.checkDownload(context,str[i],"/sdcard/"+file_name);
                            FileUtils.attachmentDownload(str[i], file_path);
                        }
                    }
                }
            }
            if (object.getNewName() != null && !object.getNewName().equals("")) {
                KLog.d("附件", object.getNewName());
                String[] strs = FileUtils.convertStrToArray(object.getNewName(), "[|]");
                paths = Arrays.asList(strs);
//                String[] toBeStored = paths.toArray(new String[paths.size()]);
                for (String str : paths) {
                    KLog.d("附件地址", str);
                    if (str.contains(".jpg") || str.contains(".jpeg") || str.contains(".png")) {
                        imagePath.add(str);
                    } else {
                        attachmentPath.add(str);
                        file_type = 2;
                    }
                }
                initList();
            } else {
                KLog.d("无附件", "--------");
            }
        }
    }

    private void initList() {
        if (imagePath.size() > 0) {
            View headerView = LayoutInflater.from(context).inflate(R.layout.item_attachment, null);
            noticesList.addHeaderView(headerView);
            TextView textView = (TextView) headerView.findViewById(R.id.mail_details_file);
            textView.setText("点击查看附件图片");
            ImageView imageView = (ImageView) headerView.findViewById(R.id.mail_details_file_img);
            imageView.setImageResource(R.mipmap.ic_file_img);
            RelativeLayout relativeLayout = (RelativeLayout) headerView.findViewById(R.id.mail_file);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开图片
                    Intent intent = new Intent(context, ImagePagerActivity.class);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imagePath.toArray(new String[imagePath.size()]));
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.override_in_anim, R.anim.override_out_anim);
                }
            });
        }
        adapter = new Attachment_Adapter(context, attachmentPath);
        noticesList.setAdapter(adapter);
        noticesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d("List点击", file_type + "");
                if (ToolUtils.isDownloading) {
                    Toast.makeText(context, "正在下载,请稍等.", Toast.LENGTH_SHORT).show();
                } else {
                    if (file_type == 1) {
                        // 打开图片
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, str);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.override_in_anim, R.anim.override_out_anim);
                    } else if (file_type == 2) {
                        // 打开PDF
                        if (file_path != null && !file_path.equals("")) {
                            System.out.println("打开软件：" + file_path);
                            Intent intent = Util.getIntent(file_path);
                            if (intent == null) {
                                Toast.makeText(context, "无法打开文件！", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(intent);
                            }
                        }
                    }
                }

            }
        });
        noticesList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(context, Mail_Forward_Activity.class)
                        .putExtra("status", 3)
                );
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
        }
    }

}
