package com.sdzx.xtbg.activity.senddocument;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.File_Adapter;
import com.sdzx.xtbg.bean.Document_Add_Response;
import com.sdzx.xtbg.bean.FuJian;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.constant.DateUtils;
import com.sdzx.xtbg.dialog.DialogFragment_file;
import com.sdzx.xtbg.dialog.ECProgressDialog;
import com.sdzx.xtbg.dialog.SendDocApproveDialog;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.StringUtils;
import com.sdzx.xtbg.tools.ToolUtils;
import com.sdzx.xtbg.view.NoScrollListView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发文拟稿
 * <p>
 * Author:Eron
 * Date: 2016/6/23 0023
 * Time: 19:15
 */
public class SendDocumentActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "SendDocumentActivity";

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;

    @Bind(R.id.document_send_no)
    EditText documentSendNo;
    @Bind(R.id.document_send_title)
    EditText documentSendTitle;
    @Bind(R.id.document_send_main_word)
    EditText documentSendMainWord;
    @Bind(R.id.document_send_main_send)
    EditText documentSendMainSend;
    @Bind(R.id.document_send_copy_send)
    EditText documentSendCopySend;
    @Bind(R.id.document_send_time)
    TextView documentSendTime;// 发文时间
    @Bind(R.id.document_send_print_no)
    EditText documentSendPrintNo;
    @Bind(R.id.document_send_review_id)
    TextView documentSendReviewId;// 核稿人

    @Bind(R.id.document_send_submit)
    Button documentSendSubmit;
    @Bind(R.id.document_send_clear)
    Button documentSendClear;
    @Bind(R.id.send_doc_add_attach)
    ImageView sendDocAddAttach;// 添加附件
    @Bind(R.id.send_document_add_attach_list)
    NoScrollListView sendDocumentAddAttachList;// 附件列表显示
    @Bind(R.id.send_doc_bottom)
    LinearLayout sendDocBottom;

    private RequestCall mCall;

    private Context context;
    private SharedPreferences preferences;

    private String sendTime, userId;

    // 附件
    private DialogFragment_file dialogFragment_file;
    FuJian mFuJian = null;
    private static final int FILE_SELECT_CODE = 5;
    private int status = 3;
    private ECProgressDialog mPostingDialog;
    private String old_ID, old_Path, old_File;
    private File_Adapter adapter;
    public static List<String> path_list = new ArrayList();
    public static List<String> name_list = new ArrayList<String>();
    public static List<String> file_id = new ArrayList<String>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
            } else if (msg.what == 2) {
                Log.e("更新附件", path_list.size() + "");
                adapter.notifyDataSetChanged();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_document);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        this.context = SendDocumentActivity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");
    }

    private void initData() {

//        requestReceiver();// 获取核稿人

        if (null != old_File && !old_File.equals("")) {
            path_list.addAll(Arrays.asList(FileUtils.convertStrToArray(old_Path)));
            name_list.addAll(Arrays.asList(FileUtils.convertStrToArray(old_File)));
            file_id.addAll(Arrays.asList(FileUtils.convertStrToArray(old_ID)));
        } else {
            if (status != 3) {
                path_list.addAll(ToolUtils.filePath);
                name_list.addAll(Arrays.asList(FileUtils.convertStrToArray(old_File)));
                file_id.addAll(Arrays.asList(FileUtils.convertStrToArray(old_ID)));
            }
        }
        for (String path : path_list) {
            Log.e(TAG, path + "---");
        }
        for (String name : name_list) {
            Log.e(TAG, name + "---");
        }
        for (String id : file_id) {
            Log.e(TAG, id + "---");
        }
        adapter = new File_Adapter(context, path_list, name_list, file_id, true, status);
        sendDocumentAddAttachList.setAdapter(adapter);

    }


    private void initView() {
        headerTitle.setText("文件处理单");
        headerRight.setVisibility(View.GONE);

        // 隐藏键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    // 发文时间
    @OnClick(R.id.document_send_time)
    void sendTime() {
        DatePickerDialog datePickerDialogStart = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                sendTime = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) :
                        (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                documentSendTime.setText(sendTime);
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
        datePickerDialogStart.show();
    }

    private void initEvent() {
        headerBack.setOnClickListener(this);
        sendDocAddAttach.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.send_doc_add_attach:
                showFileChooser();// 选择附件
                break;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件上传"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "请安装文件选择器", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                    String path = data.getDataString();
                    path = Uri.decode(path);
                    if (path.contains("media")) {
                        path = FileUtils.getRealPathFromUri(context, data.getData());
                    }
                    // 在系统文件选择器中，在最近文件选项中，选择媒体文件出现 data.getDataString 为空的情况
                    if (null == path) {
                        path = data.getDataString();
                    }
                    KLog.d("媒体文件地址------>", path);
                    KLog.d("选择的文件：", path + "-----" + uri.toString());

                    StringTokenizer token = new StringTokenizer(path, "/");
                    String fileName = "";
                    while (token.hasMoreTokens()) {
                        fileName = token.nextToken();
                    }
                    // 保存文件名
                    name_list.add(fileName);
                    // 保存路径
                    path_list.add(path);
                    Message msg1 = handler.obtainMessage();
                    msg1.what = 2;
                    handler.sendMessage(msg1);
                }
        }

    }

    private void clearData() {
        if (!path_list.isEmpty()) {
            path_list.clear();
        }
        if (!name_list.isEmpty()) {
            name_list.clear();
        }
        if (!file_id.isEmpty()) {
            file_id.clear();
        }
    }

    /**
     * 提交数据
     */
    @OnClick(R.id.document_send_submit)
    void submit() {
        if (checkInput() == true) {
            submitData();
        }

    }

    /**
     * 检查输入
     *
     * @return
     */
    private boolean checkInput() {
        if (documentSendNo.getText().toString().equals("")) {
            Toast.makeText(context, "请输入发文编号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentSendTitle.getText().toString().equals("")) {
            Toast.makeText(context, "请输入文件标题", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentSendMainWord.getText().toString().equals("")) {
            Toast.makeText(context, "请输入主题词", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentSendMainSend.getText().toString().equals("")) {
            Toast.makeText(context, "请输入主送", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentSendCopySend.getText().toString().equals("")) {
            Toast.makeText(context, "请输入抄送", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentSendTime.getText().toString().equals("")) {
            Toast.makeText(context, "请选择发文日期", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentSendPrintNo.getText().toString().equals("")) {
            Toast.makeText(context, "请输入印发份数", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentSendReviewId.getText().toString().equals("请选择核稿人")) {
            Toast.makeText(context, "请选择核稿人", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 选择接收人
     */
    @OnClick(R.id.document_send_review_id)
    void getReceiver() {
//        Toast.makeText(context, "选择审批人", Toast.LENGTH_SHORT).show();
        new SendDocApproveDialog(context)
                .readData(ConstantURL.SEND_DOCUMENT_ADD_RECEIVER, userId)
                .builder()
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        documentSendReviewId.setText(StringUtils.approve_name);
                    }
                })
                .show();
    }

    /**
     * 提交数据
     */
    private void submitData() {

        Log.e("tag", "发文审核人ID------------------------->" + StringUtils.approve_uid);

        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();

        requestParams.addBodyParameter("bianhao", documentSendNo.getText().toString());
        requestParams.addBodyParameter("title", documentSendTitle.getText().toString());
        requestParams.addBodyParameter("zhutici", documentSendMainWord.getText().toString());
        requestParams.addBodyParameter("zhusong", documentSendMainSend.getText().toString());
        requestParams.addBodyParameter("chaosong", documentSendCopySend.getText().toString());
        requestParams.addBodyParameter("fwtime", documentSendTime.getText().toString());
        requestParams.addBodyParameter("fenshu", documentSendPrintNo.getText().toString());
        requestParams.addBodyParameter("user", StringUtils.approve_uid);


        for (int i = 0; i < path_list.size(); i++) {
            File file = new File(path_list.get(i).replace("file://", ""));
            KLog.d("文件上传路径------>", file);
            if (file.exists()) {
                KLog.d("文件路径------>", "" + path_list.get(i));
                requestParams.addBodyParameter("file[]", file);
            } else {
                KLog.d("文件路径------>", "文件不存在----->");
            }
        }

        httpUtils.send(HttpMethod.POST, ConstantURL.SEND_DOCUMENT_ADD + userId, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.e("tag", "上传返回数据-------------->" + responseInfo.result);
                    Gson gson = new Gson();
                    Document_Add_Response response = gson.fromJson(responseInfo.result, Document_Add_Response.class);
                    String state = response.getState();
                    if (state.equals("ok")) {
                        Toast.makeText(context, "提交成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "网络错误！请连接网络后重试！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }


}
