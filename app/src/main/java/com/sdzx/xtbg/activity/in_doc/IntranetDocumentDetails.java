package com.sdzx.xtbg.activity.in_doc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.document.Document_Add;
import com.sdzx.xtbg.adapter.File_Adapter;
import com.sdzx.xtbg.bean.Document_Intranet;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.TimeUtils;
import com.sdzx.xtbg.view.NoScrollListView;
import com.socks.library.KLog;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 内网文件/邮件详情
 * <p/>
 * Author:Eron
 * Date: 2016/6/28 0028
 * Time: 13:59
 */
public class IntranetDocumentDetails extends Activity implements View.OnClickListener {

    // 附件列表
    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;
    @Bind(R.id.details_title) // 标题
            TextView detailsTitle;
    @Bind(R.id.details_num) // 文号
            TextView detailsNo;
    @Bind(R.id.details_num_lin)
    LinearLayout detailsNoLin;
    @Bind(R.id.details_sender)
    TextView detailsSender;
    @Bind(R.id.details_time)
    TextView detailsTime;
    @Bind(R.id.details_word) // 主题词
            TextView detailsWord;
    @Bind(R.id.details_word_lin)
    LinearLayout detailsWordLin;
    @Bind(R.id.details_content_txt)
    TextView detailsContentTxt;
    @Bind(R.id.details_content)
    TextView detailsContent;
    @Bind(R.id.details_content_lin)
    LinearLayout detailsContentLin;
    @Bind(R.id.file_list)
    NoScrollListView fileList;
    @Bind(R.id.mail_ll_appendix)
    LinearLayout mailLlAppendix;
    @Bind(R.id.in_doc_relay_to_doc)
    Button inDocRelayToDoc;// 转到公文

    // 对象
    private Context context;
    private String id;
    private int status; // 1、内网文件 2、内网邮件
    private String file_path;
    private SharedPreferences preferences;
    private Document_Intranet.Intranet intranet; // 文件对象
//    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.intranet_document_details);
        ButterKnife.bind(this);
        initConstants();
        String act;
        if (status == 1) {
            act = "file_show";
        } else {
            act = "pipe_show";
        }
//        materialDialog = new MaterialDialog.Builder(context)
//                .title("正在读取数据")
//                .content("正在努力下载中······")
//                .progress(true, 0)
//                .progressIndeterminateStyle(true)
//                .cancelable(false)
//                .show();
        readData(act);
        initViews();
        initEvents();
        initData();
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = IntranetDocumentDetails.this;
        Intent intent = super.getIntent();
        status = intent.getIntExtra(ConstantString.STATUS, 1);
        id = intent.getStringExtra(ConstantString.ID);
        intranet = (Document_Intranet.Intranet) intent.getSerializableExtra(ConstantString.INTRANET);
    }

    private void initViews() {
        switch (status) {
            case 1:
                headerTitle.setText(getString(R.string.intranet_document_title));
                break;
            case 2:
                headerTitle.setText(getString(R.string.intranet_mail_title));
                break;
        }
        headerRight.setVisibility(View.GONE);
    }

    private void initEvents() {
        headerBack.setOnClickListener(this);
    }

    /**
     * 读取数据
     */
    private void readData(String act) {
        String url = ConstantURL.IN_DOC_DETAIL + "?act=" + act + "&id=" + id;
        KLog.d("内网文件网址------->", url);
        HttpUtils httpUtils = new HttpUtils(60 * 1000);
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    KLog.json("内网文件详情-------->", responseInfo.result);
                    Gson gson = new Gson();
                    Document_Intranet documentIntranet = gson.fromJson(responseInfo.result, Document_Intranet.class);
                    if (null != documentIntranet) {
                        intranet = documentIntranet.getInfo().get(0);
                        if (null != intranet) {
                            initData();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (status == 1) {
            // 内网文件
            // 文号
            detailsNoLin.setVisibility(View.VISIBLE);
            detailsNo.setText(intranet.getFilenum());
            // 主题词
            detailsWordLin.setVisibility(View.VISIBLE);

            // 内容
            detailsContentTxt.setText(getString(R.string.intranet_content));
            if (intranet.getContent() != null && !intranet.getContent().equals("")) {
                detailsContent.setText(intranet.getContent()); // 内容
            } else {
                detailsContent.setText("无");
            }
        } else {
            // 内网邮件
            detailsNoLin.setVisibility(View.GONE);
            detailsWordLin.setVisibility(View.GONE);
            // 内容
            detailsContentTxt.setText(getString(R.string.intranet_mail_content));
            if (intranet.getContent() != null && !intranet.getContent().equals("")) {
                detailsContent.setText(intranet.getContent()); // 内容
            } else {
                detailsContent.setText("无");
            }
        }
        // 标题
        detailsTitle.setText(intranet.getTitle());
        // 发文人
        if (null != intranet.getSendername() && !intranet.getSendername().equals("")) {
            detailsSender.setText(intranet.getSendername());
        }
        // 发文时间
        if (null != intranet.getAddtime() && !intranet.getAddtime().equals("")) {
            detailsTime.setText(TimeUtils.getDateFromString(intranet.getAddtime(), "yyyy-MM-dd"));
        }
        // 附件
        if (intranet.getOldName() != null && !intranet.getOldName().equals("")) {
            mailLlAppendix.setVisibility(View.VISIBLE);
            File_Adapter adapter = new File_Adapter(context
                    , Arrays.asList(FileUtils.convertStrToArray(intranet.getNewName()))
                    , Arrays.asList(FileUtils.convertStrToArray(intranet.getOldName()))
                    , null, false, status);
            fileList.setAdapter(adapter);
//            materialDialog.dismiss();
        } else {
            // 无附件
            mailLlAppendix.setVisibility(View.GONE);
        }
    }

    /**
     * 转到公文
     */
    @OnClick(R.id.in_doc_relay_to_doc)
    void relayToDoc() {
        startActivity(new Intent(context, Document_Add.class)
                .putExtra("in_doc_title", intranet.getTitle())
                .putExtra("in_doc_time", TimeUtils.getDateFromString(intranet.getAddtime(), "yyyy-MM-dd"))
                .putExtra("in_doc_content", intranet.getContent())
                .putExtra("in_doc_att_old_name", intranet.getOldName())
                .putExtra("in_doc_att_new_name", intranet.getNewName())
                .putExtra("come_from", "1")
        .putExtra("type",1));// 1是内网文件
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
