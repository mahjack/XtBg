package com.sdzx.xtbg.activity.in_doc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.File_Document_Adapter;
import com.sdzx.xtbg.bean.InMail163Detail;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.ToolUtils;
import com.sdzx.xtbg.tools.Util;
import com.sdzx.xtbg.tools.utils;
import com.sdzx.xtbg.view.NoScrollListView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Arrays;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 163邮件详情
 * Author:Eron
 * Date: 2016/7/25 0025
 * Time: 14:44
 */
public class InMail163DetailActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;

    @Bind(R.id.in_mail_details_title)
    TextView inMailDetailsTitle;// 标题
    @Bind(R.id.in_mail_details_person)
    TextView inMailDetailsPerson;// 发件人
    @Bind(R.id.in_mail_details_from_address)
    TextView inMailDetailsFromAddress;// 发件地址
    @Bind(R.id.in_mail_details_time)
    TextView inMailDetailsTime;// 发件时间
    @Bind(R.id.in_mail_details_content)
    TextView inMailDetailsContent;// 内容
    @Bind(R.id.in_mail_file_list)
    NoScrollListView inMailFileList;// 附件列表

    @Bind(R.id.webView_content)
    WebView webViewContent;// 网页内容

    private Context context;
    private RequestCall mCall;

    private InMail163Detail.MailDetail detail;

    private int detailId = 0;
    private static final int UPDATE_UI = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    inMailDetailsTitle.setText(detail.getTitle());
                    inMailDetailsPerson.setText(detail.getFromName());
                    inMailDetailsFromAddress.setText(detail.getFromBy());
                    inMailDetailsTime.setText(detail.getMailDate());
                    inMailDetailsContent.setText(Html.fromHtml(detail.getBody()));

                    if (detail.getNewName() != null && !detail.getNewName().equals("")) {
                        if (detail.getNewName().contains(".jpg") || detail.getNewName().contains(".jpeg")) {
                            inMailDetailsContent.setText(Html.fromHtml(detail.getBody()) + "\n\n\n\n图片内容请点击查看详情！");
                        } else {
                            Util.showHtml(detail.getBody(), inMailDetailsContent);
                        }
                    } else {
                        Util.showHtml(detail.getBody(), inMailDetailsContent);
                    }
                    String inMailContent = detail.getBody();
                    if (detail.getNewName() != null && !detail.getNewName().equals("")) {
                        // 附件
                        String[] attach_list = FileUtils.convertStrToArray(detail.getNewName(), "[|]");
                        for (int i = 0; i < attach_list.length; i++) {
                            KLog.d("附件地址------>" + attach_list[i]);
                            int file_type = 0;
                            if (attach_list[i].contains(".jpg")) {
                                file_type = 1;
                            } else {
                                file_type = 2;
                                inMailFileList.setVisibility(View.VISIBLE);
                                File_Document_Adapter adapter = new File_Document_Adapter(context
                                        , Arrays.asList(FileUtils.convertStrToArray(detail.getNewName()))
                                        , Arrays.asList(FileUtils.convertStrToArray(detail.getOldName()))
                                        , false);
                                inMailFileList.setAdapter(adapter);
                            }
                            String split = ":/";
                            StringTokenizer token = new StringTokenizer(attach_list[i], split);
                            String file_name = "";
                            while (token.hasMoreTokens()) {
                                file_name = token.nextToken();
                            }
                            String file_path = ToolUtils.ATTACHMENT_PATH + "/" + file_name;
                            if (utils.isNetUseful(context)) {
                                if (FileUtils.fileIsExists(file_path)) {
                                    // 如果存在
                                } else {
                                    // 如果不存在 下载
                                    FileUtils.attachmentDownload(attach_list[i], file_path);
                                }
                            }
                        }
                    }

                    webViewContent.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }
                    });
                    webViewContent.loadUrl("http://www.baidu.com");
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_in_mail_163);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        context = InMail163DetailActivity.this;
        Intent intent = getIntent();
        detailId = intent.getIntExtra("detailID", 0);
        KLog.d("163邮件详情ID------->", detailId);
    }

    private void initData() {
        getData();
    }


    private void initView() {
        headerTitle.setText("163邮件详情");
        headerRight.setVisibility(View.GONE);
        webViewContent.getSettings().setJavaScriptEnabled(true);
    }

    private void initEvent() {
        headerBack.setOnClickListener(this);
    }

    private void getData() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.IN_MAIL_163_DETAIL + detailId)
                .build();
        mCall.readTimeOut(60 * 1000);
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "访问服务器错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("163邮件详情------>", response);
                    Gson gson = new Gson();
                    InMail163Detail details = gson.fromJson(response, InMail163Detail.class);
                    detail = details.getInfo().get(0);
                    if (detail != null) {
                        Message message = new Message();
                        message.what = UPDATE_UI;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
