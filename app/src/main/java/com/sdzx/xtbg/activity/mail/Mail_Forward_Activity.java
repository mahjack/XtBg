package com.sdzx.xtbg.activity.mail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.File_Adapter;
import com.sdzx.xtbg.bean.GetDataDao;
import com.sdzx.xtbg.bean.GetDataDaoImpl;
import com.sdzx.xtbg.bean.LoadCallBack;
import com.sdzx.xtbg.bean.RequestVo;
import com.sdzx.xtbg.bean.ResultValue;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.dialog.ECProgressDialog;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.ToolUtils;
import com.sdzx.xtbg.tools.Util;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Author：Mark
 * Date：2015/12/16 0016
 * Tell：15006330640
 * <p/>
 * 转发邮件、新建邮件
 */
public class Mail_Forward_Activity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "Mail_Forward_Activity";
    // TitleBar
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;

    // 邮件头
    @ViewInject(R.id.et_mail_receiver)
    EditText mReplyReceiver; // 收件人
    @ViewInject(R.id.iv_mail_add_receiver)
    ImageView addReceiver;
    @ViewInject(R.id.et_mail_sender)
    EditText mReplySender; // 发件人
    @ViewInject(R.id.et_mail_subject)
    EditText mReplySubject; // 邮件主题
    @ViewInject(R.id.et_mail_detail_body)
    EditText mReplyDetailBody; // 邮件正文

    // 附件部分
    @ViewInject(R.id.mail_files_list)
    ListView lv_mail_details_files;
    @ViewInject(R.id.iv_mail_attachment)
    ImageView mReplyAddAttachment; // 添加附件
    @ViewInject(R.id.ll_mail_attachment)
    LinearLayout mMailAttachmentDetails; // 显示隐藏 邮件附件布局

    // 对象
    private Context context;
    private String id, content, subject, receiver; // 内容，主题，收件人
    private int status;
    private String sender, uid;
    private SharedPreferences preferences;
    private String user_name, user_id = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mReplyReceiver.setText(user_name);
            } else if (msg.what == 2) {
                Log.e("更新附件", path_list.size() + "");
                adapter.notifyDataSetChanged();
            }
            super.handleMessage(msg);
        }
    };
    private static final int FILE_SELECT_CODE = 5;
    private String old_ID, old_Path, old_File;
    private File_Adapter adapter;
    public static List<String> path_list = new ArrayList();
    public static List<String> name_list = new ArrayList<String>();
    public static List<String> file_id = new ArrayList<String>();
    private ECProgressDialog mPostingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.document_mail);
        ViewUtils.inject(this);
        initConstants();
        initView();
        initData();
        initEvent();
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = Mail_Forward_Activity.this;
        Intent intent = super.getIntent();
        id = intent.getStringExtra(ConstantString.ID);
        status = intent.getIntExtra(ConstantString.STATUS, 3);
        content = intent.getStringExtra(ConstantString.CONTENT);
        subject = intent.getStringExtra(ConstantString.SUBJECT);
        receiver = intent.getStringExtra(ConstantString.RECEIVER);
        old_ID = intent.getStringExtra(ConstantString.OLD_ID);
        old_Path = intent.getStringExtra(ConstantString.OLD_PATH);
        old_File = intent.getStringExtra(ConstantString.OLD_FILE);
        Log.e(TAG, old_Path + "----" + old_File);
        sender = preferences.getString(ConstantString.NAME, "");
        uid = preferences.getString(ConstantString.UID, "");

    }

    private void initView() {
        header_right.setImageResource(R.drawable.selector_mail_send);

        switch (status) {
            case 1:
                // 回复
                header_title.setText(getString(R.string.mail_reply_title));
                mReplyReceiver.setText(receiver); // 收件人
                mReplyReceiver.setEnabled(false);
                addReceiver.setVisibility(View.GONE);
                mReplySender.setText(sender);
                mReplySubject.setText("回复：" + subject); // 主题
                mReplyDetailBody.setText(Html.fromHtml("<br/><br/><br/><br/><br/>"
                        + "----------原始内容---------<br/><br/>"
                        + "发件人:  " + receiver + "<br/><br/>"
                        + "主题:  " + subject + "<br/><br/>"
                        + "内容:  " + content)); // 内容
                mMailAttachmentDetails.setVisibility(View.VISIBLE); // 默认显示附件内容
                break;
            case 2:
                // 转发
                header_title.setText(getString(R.string.mail_forward_title));
                addReceiver.setVisibility(View.VISIBLE);
                mReplySender.setText(sender);
                mReplySubject.setText("转发：" + subject); // 主题
                mReplyDetailBody.setText(Html.fromHtml("<br/><br/><br/><br/><br/>"
                        + "----------原始内容---------<br/><br/>"
                        + "发件人:  " + receiver + "<br/><br/>"
                        + "主题:  " + subject + "<br/><br/>"
                        + "内容:  " + content)); // 内容
                mMailAttachmentDetails.setVisibility(View.VISIBLE); // 默认显示附件内容
                break;
            case 3:
                // 新建
                header_title.setText(getString(R.string.mail_new_title));
                mReplySender.setText(sender);
                addReceiver.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initData() {
        if (null != old_File && !old_File.equals("")) {
            path_list.addAll(Arrays.asList(FileUtils.convertStrToArray(old_Path)));
            name_list.addAll(Arrays.asList(FileUtils.convertStrToArray(old_File)));
            file_id.addAll(Arrays.asList(FileUtils.convertStrToArray(old_ID)));
        } else {
            if (status != 3) {
                try {
                    path_list.addAll(ToolUtils.filePath);
                    name_list.addAll(Arrays.asList(FileUtils.convertStrToArray(old_File)));
                    file_id.addAll(Arrays.asList(FileUtils.convertStrToArray(old_ID)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        lv_mail_details_files.setAdapter(adapter);
    }


    private void initEvent() {
        header_back.setOnClickListener(this);
        header_right.setOnClickListener(this);
        mReplyAddAttachment.setOnClickListener(this);
        addReceiver.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_right:
                // 发送邮件
                if (checkSend()) {
                    mPostingDialog = new ECProgressDialog(context, R.string.loading_press);
                    mPostingDialog.show();
                    switch (status) {
                        case 1:
                            // 回复
                            sendEmail("getreply");
                            break;
                        case 2:
                            // 转发
                            sendEmail("getforward");
                            break;
                        case 3:
                            // 新建
                            sendEmail("add");
                            break;
                    }
                }
                break;
            case R.id.iv_mail_add_receiver:
                // 添加收件人
                startActivityForResult(new Intent(context, Mail_Select_Activity.class), 1);
                break;
            // 显示隐藏 添加附件 布局
            case R.id.iv_mail_attachment:
                // 选择附件
                showFileChooser();
                break;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkSend() {
        if (mReplyReceiver.getText().toString() == null || mReplyReceiver.getText().toString().equals("")) {
            Toast.makeText(context, "请选择收件人！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    name_list.add(fileName);
                    path_list.add(path);
                    Message msg1 = handler.obtainMessage();
                    msg1.what = 2;
                    handler.sendMessage(msg1);
                }
                break;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    user_id = data.getStringExtra("user_id");
                    user_name = data.getStringExtra("user_name");
                    Log.e("选择人员：", data.getStringExtra("user_id") + "---" + data.getStringExtra("user_name"));
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    msg.obj = user_name;
                    handler.sendMessage(msg);
                    break;
                case 1100:
                    Uri uri = data.getData();
                    Log.e("选择的文件：", uri.toString());
                    StringTokenizer token = new StringTokenizer(uri.toString(), "/");
                    String fileName = "";
                    while (token.hasMoreTokens()) {
                        fileName = token.nextToken();
                    }
                    name_list.add(fileName);
                    path_list.add(uri.toString());
                    Message msg1 = handler.obtainMessage();
                    msg1.what = 2;
                    handler.sendMessage(msg1);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 发送
     */
    private void sendEmail(String act) {
        String url = ConstantURL.MAIL_HAVE_URL + "?act=" + act + "&uid=" + preferences.getString(ConstantString.UID, "") + "&id=" + id;
        GetDataDao getDataDao = new GetDataDaoImpl();
        RequestVo requestVo = new RequestVo();
        requestVo.setRequestUrl(url);
        requestVo.setContext(context);
        requestVo.setHttpMethod(HttpRequest.HttpMethod.POST);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter(ConstantString.TITLE, mReplySubject.getText().toString());
        requestParams.addBodyParameter(ConstantString.CONTENT, mReplyDetailBody.getText().toString());
        if (status != 1) {
            requestParams.addBodyParameter(ConstantString.STAFF, user_id);
        }
        if (status != 3) {
            String file_id_request = "";
            if (!file_id.isEmpty()) {
                for (String id : file_id) {
                    file_id_request += (id + ",");
                }
                Log.e(TAG, file_id_request.substring(0, file_id_request.length() - 1));
                requestParams.addBodyParameter(ConstantString.OLD_ID, file_id_request.substring(0, file_id_request.length() - 1));
            }

        }
        for (int i = 0; i < path_list.size(); i++) {
            Log.e("附件", path_list.get(i).replace("file://", "") + name_list.get(i));
            File file = new File(path_list.get(i).replace("file://", ""));
            if (file.exists()) {
                Log.e(TAG, "文件存在！");
                requestParams.addBodyParameter("file" + i, file);
                requestParams.addBodyParameter("fileName" + i, name_list.get(i));
            } else {
                Log.e(TAG, "文件不存在！");
            }
        }
        requestVo.setRequestParams(requestParams);
        Log.e(TAG, url);
        getDataDao.getData(requestVo, new LoadCallBack() {
            @Override
            public void onLoading(int progress) {
            }

            @Override
            public void onLoadSuccess(String obj) {
                Util.dismissPostingDialog(mPostingDialog);
                Log.e("结果", obj);
                if (null == obj || obj.equals("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.mail_success),
                            Toast.LENGTH_SHORT).show();
                    clearData();
                    finish();
                }
                ResultValue resultValue = new ResultValue();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject((String) obj);
                    ResultValue value = resultValue
                            .parseJson(jsonObject);
                    if ("ok".equals(value.getValue())) {
                        Toast.makeText(
                                context,
                                context.getResources().getString(
                                        R.string.mail_success),
                                Toast.LENGTH_SHORT).show();
                        clearData();
                        finish();
                    } else {
                        Toast.makeText(
                                context,
                                context.getResources().getString(
                                        R.string.mail_fail),
                                Toast.LENGTH_SHORT).show();
                        clearData();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    clearData();
                }
            }

            @Override
            public void onLoadFail() {
                Toast.makeText(
                        context,
                        context.getResources().getString(
                                R.string.base_server),
                        Toast.LENGTH_SHORT).show();
                Util.dismissPostingDialog(mPostingDialog);
                clearData();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }
}
