package com.sdzx.xtbg.activity.senddocument;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.ActivityTaskManager;
import com.sdzx.xtbg.adapter.File_Document_Adapter;
import com.sdzx.xtbg.adapter.Send_Document_Process_Adapter;
import com.sdzx.xtbg.bean.SendDocApprove;
import com.sdzx.xtbg.bean.SendDocDetails;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.view.NoScrollListView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 发文详情
 * Author:Eron
 * Date: 2016/6/24 0016
 * Time: 14:51
 */
public class SendDocumentDetailsActivity extends Activity implements View.OnClickListener {


    // header
    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;

    // 内容模块
    @Bind(R.id.document_title)
    TextView documentTitle;
    @Bind(R.id.send_doc_detail_no)
    TextView sendDocDetailNo;
    @Bind(R.id.send_doc_detail_title)
    TextView sendDocDetailTitle;
    @Bind(R.id.send_doc_detail_main)
    TextView sendDocDetailMain;
    @Bind(R.id.send_doc_detail_copy)
    TextView sendDocDetailCopy;
    @Bind(R.id.send_doc_detail_send_time)
    TextView sendDocDetailSendTime;
    @Bind(R.id.send_doc_detail_quota)
    TextView sendDocDetailQuota;// 份数
    @Bind(R.id.send_doc_detail_add_user)
    TextView sendDocDetailAddUser;
    @Bind(R.id.send_doc_detail_add_time)
    TextView sendDocDetailAddTime;
    @Bind(R.id.send_doc_detail_finish_time)
    TextView sendDocDetailFinishTime;
    @Bind(R.id.send_doc_detail_depart)
    TextView sendDocDetailDepart;
    @Bind(R.id.document_file_title)
    TextView documentFileTitle;
    @Bind(R.id.send_doc_detail_content)
    TextView sendDocDetailContent;

    // 审批模块
    @Bind(R.id.file_list)
    NoScrollListView fileList;
    @Bind(R.id.opinion_list)
    NoScrollListView opinionList;
    @Bind(R.id.moment_left_of_comment_button2)
    RelativeLayout momentLeftOfCommentButton2;
    @Bind(R.id.document_opinion_ll)
    LinearLayout documentOpinionLl;// 核稿
    @Bind(R.id.agreement_list)
    NoScrollListView agreementList;
    @Bind(R.id.document_agreement_ll)
    LinearLayout documentAgreementLl;// 审签
    @Bind(R.id.reading_list)
    NoScrollListView readingList;
    @Bind(R.id.document_reading_ll)
    LinearLayout documentReadingLl;// 签发
    @Bind(R.id.result_list)
    NoScrollListView resultList;
    @Bind(R.id.document_result_ll)
    LinearLayout documentResultLl;
    @Bind(R.id.case_list)
    NoScrollListView caseList;


    @Bind(R.id.ll_send_doc_result)
    LinearLayout ll_send_doc_result;// 办理结果
    @Bind(R.id.document_case_ll)
    LinearLayout documentCaseLl;// 办结情况

    @Bind(R.id.document_btn)
    Button documentBtn;
    // 对象
    private Context context;
    private String stepName, tag, status, totag;// 操作步骤名

    private RequestCall mCall;

    private String detailId, userId;// 文件ID 用户ID
    private SharedPreferences preferences;
    private int comeState = 0;// 0为办理 1为查阅
    private static final int UPDATE_UI = 1;
    private String[] str;
    private int file_type = 0;
    private String file_path = "";

    private SendDocDetails sendDocDetails;
    private List<SendDocDetails.Value> value = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    if (sendDocDetails.getValue() != null) {
                        sendDocDetailNo.setText(value.get(0).getBianhao());
//                        Log.e("tag", "发文编号------------------>" + value.get(0).getBianhao());
                        sendDocDetailTitle.setText(value.get(0).getTitle());
                        sendDocDetailMain.setText(value.get(0).getZhusong());
                        sendDocDetailCopy.setText(value.get(0).getChaosong());
                        sendDocDetailSendTime.setText(value.get(0).getFwtime());
                        sendDocDetailQuota.setText(value.get(0).getFenshu());
                        sendDocDetailAddTime.setText(value.get(0).getAddtime());
                        sendDocDetailDepart.setText(value.get(0).getDepart());
                        sendDocDetailAddUser.setText(value.get(0).getAdduser());

                        if (value.get(0).getNewName() != null && !value.get(0).getNewName().equals("")) {
                            if (value.get(0).getNewName().contains(".jpg") || value.get(0).getNewName().contains(".jpeg")) {
                                sendDocDetailContent.setText("图片内容请点击查看详情！");
                            }
                        }

                        if (value.get(0).getNewName() != null && !value.get(0).getNewName().equals("")) {
                            // 附件
                            str = FileUtils.convertStrToArray(value.get(0).getNewName(), "[|]");
                            for (int i = 0; i < str.length; i++) {
                                KLog.d("附件地址------>" + str[i]);
                                if (str[i].contains(".jpg")) {
                                    file_type = 1;
                                } else {
                                    file_type = 2;
                                    fileList.setVisibility(View.VISIBLE);
                                    File_Document_Adapter adapter = new File_Document_Adapter(context
                                            , Arrays.asList(FileUtils.convertStrToArray(value.get(0).getNewName()))
                                            , Arrays.asList(FileUtils.convertStrToArray(value.get(0).getOldName()))
                                            , false);
                                    fileList.setAdapter(adapter);
                                }
                            }
                        }

                        // 核稿
                        if (sendDocDetails.getValue1() != null) {
                            documentOpinionLl.setVisibility(View.VISIBLE);
                            if (!sendDocDetails.getValue1().isEmpty()) {
                                Send_Document_Process_Adapter adapter =
                                        new Send_Document_Process_Adapter(context, sendDocDetails.getValue1());
                                opinionList.setAdapter(adapter);
                            }
                        }

                        // 审签
                        if (sendDocDetails.getValue2() != null) {
                            documentAgreementLl.setVisibility(View.VISIBLE);
                            if (!sendDocDetails.getValue2().isEmpty()) {
                                Send_Document_Process_Adapter adapter =
                                        new Send_Document_Process_Adapter(context, sendDocDetails.getValue2());
                                agreementList.setAdapter(adapter);
                            }
                        }
                        // 签发
                        if (sendDocDetails.getValue3() != null) {
                            documentReadingLl.setVisibility(View.VISIBLE);
                            if (!sendDocDetails.getValue3().isEmpty()) {
                                Send_Document_Process_Adapter adapter =
                                        new Send_Document_Process_Adapter(context, sendDocDetails.getValue3());
                                readingList.setAdapter(adapter);
                            }
                        }
                        // 签发
                        if (sendDocDetails.getValue4() != null) {
                            ll_send_doc_result.setVisibility(View.VISIBLE);
                            if (!sendDocDetails.getValue4().isEmpty()) {
                                Send_Document_Process_Adapter adapter =
                                        new Send_Document_Process_Adapter(context, sendDocDetails.getValue4());
                                resultList.setAdapter(adapter);
                            }
                        }
                    }
                    break;
                case 2:
                    documentBtn.setText(stepName);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.send_document_details);
        ButterKnife.bind(this);

        initConstants();
        readData();
        initViews();
        initEvents();
    }

    private void initConstants() {
        this.context = SendDocumentDetailsActivity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");
        Intent intent = getIntent();
        detailId = intent.getStringExtra(ConstantString.SEND_DOC_DETAIL_ID);
        comeState = intent.getIntExtra(ConstantString.SEND_DOC_DETAIL_STATES, 0);

        // Activity管理器
        ActivityTaskManager.getInstance().putActivity("SendDocumentDetails", SendDocumentDetailsActivity.this);
    }

    private void readData() {
        sendDetailRequest(); // 查看发文详情
        getApproveDetail(); // 查看审核详情
        documentBtn.setText("审核");

    }


    private void initViews() {

        headerTitle.setText("发文详情");
        headerRight.setVisibility(View.GONE);
        ll_send_doc_result.setVisibility(View.GONE);
        documentCaseLl.setVisibility(View.GONE);

        if (comeState == 0) {
            documentBtn.setVisibility(View.VISIBLE);
        } else if (comeState == 1) {
            documentBtn.setVisibility(View.GONE);
        }

    }

    private void initEvents() {
        headerBack.setOnClickListener(this);
    }

    /**
     * 发送详情请求
     */
    private void sendDetailRequest() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.SEND_DOCUMENT_DETAILS + detailId + "&uid=" + userId)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("发文详情", response);
                    Gson gson = new Gson();
                    sendDocDetails = gson.fromJson(response, SendDocDetails.class);
                    value = sendDocDetails.getValue();

                    Message message = new Message();
                    message.what = UPDATE_UI;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 查看审核详情
     */
    private void getApproveDetail() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.SEND_DOCUMENT_ADD_APPROVE + detailId + "&uid=" + userId)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "服务器或网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("审核详情", response);
                    JSONObject object=new JSONObject(response);
                    Gson gson = new Gson();
                    SendDocApprove sendDocApprove = gson.fromJson(response, SendDocApprove.class);
                    tag = sendDocApprove.getTag();
                    status = sendDocApprove.getStatus();
                    totag = sendDocApprove.getTotag();
//                    Message message = new Message();
//                    message.what = 2;
//                    handler.sendMessage(message);
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

    /**
     * 提交审批数据
     * fawen.php?act=shenpido&do=do&id=2&uid=3
     */
    @OnClick(R.id.document_btn)
    void submitApprove() {
        startActivityForResult(new Intent(context, SendDocApproveActivity.class)
                .putExtra("send_doc_detailId", detailId)
                .putExtra("send_doc_totag", totag)
                .putExtra("tag", tag)
                .putExtra("send_doc_tag_name", stepName),0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mCall.cancel();
    }
}
