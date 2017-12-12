package com.sdzx.xtbg.activity.mail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.sdzx.xtbg.adapter.File_Adapter;
import com.sdzx.xtbg.adapter.Receiver_Adapter;
import com.sdzx.xtbg.bean.Mail;
import com.sdzx.xtbg.bean.Mail_Object;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.TimeUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 * <p/>
 * 邮件详情
 */
public class Mail_Details_Activity extends Activity implements View.OnClickListener {
    private static final String TAG = "Mail_Details_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    @ViewInject(R.id.mail_details_tv)
    TextView mail_details_tv;
    @ViewInject(R.id.mail_details_person)
    TextView mail_details_person; // 人
    @ViewInject(R.id.received_list)
    ListView received_list;
    @ViewInject(R.id.mail_details_time)
    TextView mail_details_time; // 时间
    @ViewInject(R.id.mail_details_title)
    TextView mail_details_title; // 主题
    @ViewInject(R.id.mail_details_content)
    TextView mail_details_content; // 正文
    // 附件
    @ViewInject(R.id.mail_ll_appendix)
    LinearLayout mail_ll_appendix;
    //    @ViewInject(R.id.mail_file)
//    LinearLayout mail_file;
    @ViewInject(R.id.mail_details_file_img)
    ImageView mail_details_file_img; // 附件图标
    @ViewInject(R.id.mail_details_file)
    TextView mail_details_file; // 附件
    @ViewInject(R.id.mail_reply)
    LinearLayout mail_reply; // 回复
    @ViewInject(R.id.mail_forward)
    LinearLayout mail_forward; // 转发
//    @ViewInject(R.id.mail_delete)
//    LinearLayout mail_delete; // 删除
    // 附件列表
    @ViewInject(R.id.file_list)
ListView file_list;
    // 对象
    private Context context;
    private String id;
    private int status; // 1、已收邮件 2、已发邮件
    private List<Mail_Object> annObjects = new ArrayList<Mail_Object>();
    private String file_path;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_mail_details);
        ViewUtils.inject(this);
        initConstants();
        if (status == 1) {
            readData("getshow");
        } else {
            readData("show");
        }

        initViews();
        initEvents();
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = Mail_Details_Activity.this;
        Intent intent = super.getIntent();
        status = intent.getIntExtra(ConstantString.STATUS, 1);
        id = intent.getStringExtra(ConstantString.ID);
    }

    private void initViews() {
        switch (status) {
            case 1:
                header_title.setText(getString(R.string.mail_inbox));
                mail_details_tv.setText(getString(R.string.mail_sender));
                break;
            case 2:
                header_title.setText(getString(R.string.mail_outbox));
                mail_details_tv.setText(getString(R.string.mail_receiver));
                mail_reply.setVisibility(View.GONE);
                break;
        }
        header_right.setVisibility(View.GONE);
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
        mail_reply.setOnClickListener(this);
        mail_forward.setOnClickListener(this);
//        mail_delete.setOnClickListener(this);
//        file_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 附件
//                file_path = "/sdcard/" + FileUtils.convertStrToArray(object.getShoujianren(),"[|]")
//                System.out.println("打开软件："+file_path);
//                Intent intent = Util.getIntent(file_path);
//                startActivity(intent);
//            }
//        });
    }

    /**
     * 读取数据
     */
    private void readData(String act) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, act);
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID, ""));
        params.addQueryStringParameter(ConstantString.ID, id);
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantURL.MAIL_HAVE_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
//                    Log.e("测试：", responseInfo.result);
                    KLog.json("邮件详情------>", responseInfo.result);
                    Gson gson = new Gson();
                    Mail mail = gson.fromJson(responseInfo.result, Mail.class);
                    if (mail != null) {
                        annObjects = mail.getMailinfo();
                        if (annObjects != null) {
                            initData();
                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Mail_Object object = annObjects.get(0);
        if (object != null) {
            if (status == 1) {
                mail_details_person.setText(object.getAdduser());
                received_list.setVisibility(View.GONE);
            } else {
                mail_details_person.setVisibility(View.GONE);
                Receiver_Adapter adapter = new Receiver_Adapter(context, FileUtils.convertStrToArray(object.getShoujianren().substring(1, object.getShoujianren().length()), " "));
                received_list.setAdapter(adapter);
//                Subscription s = rx.Observable
//                        .just(object.getAdduser())
//                        .map(new Func1<String, String[]>() {
//                            @Override
//                            public String[] call(String s) {
//                                return new String[0];
//                            }
//                        })
//                        .map(new Func1<String[], String>() {
//                            @Override
//                            public String call(String[] strings) {
//                                return null;
//                            }
//                        })
//                        .subscribe(new Action1<String>() {
//                            @Override
//                            public void call(String s) {
//                                Log.e("打印", s);
//                                mail_details_person.setText(s);
//                            }
//                        });
//                new CompositeSubscription().add(s);
            }
            mail_details_title.setText(object.getTitle());
            if (null != object.getAddtime() && !object.getAddtime().equals("")) {
                mail_details_time.setText(TimeUtils.getTimeForLong(Long.parseLong(object.getAddtime()) * 1000));
            }
            if (object.getContent() != null && !object.getContent().equals("")) {
                mail_details_content.setText(Html.fromHtml(object.getContent())); // 邮件内容
            } else {
                mail_details_content.setText("无");
            }
            // 附件
            Log.e(TAG, object.getFileid());
            if (object.getOldName() != null && !object.getOldName().equals("")) {
                mail_ll_appendix.setVisibility(View.VISIBLE);
                File_Adapter adapter = new File_Adapter(context
                        , Arrays.asList(FileUtils.convertStrToArray(object.getNewName()))
                        , Arrays.asList(FileUtils.convertStrToArray(object.getOldName()))
                        , Arrays.asList(FileUtils.convertStrToArray(object.getFileid())), false, status);
                file_list.setAdapter(adapter);
            } else {
                // 无附件
                mail_ll_appendix.setVisibility(View.GONE);
            }
            if (object.getNewName() != null && !object.getNewName().equals("")) {
//                /UpLoadFile/file/2015121113184419670.doc
            }
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.mail_reply:
                // 回复
                startActivity(new Intent(context, Mail_Forward_Activity.class)
                        .putExtra(ConstantString.STATUS, 1)
                        .putExtra(ConstantString.ID, id)
                        .putExtra(ConstantString.RECEIVER, mail_details_person.getText().toString())
                        .putExtra(ConstantString.SUBJECT, mail_details_title.getText().toString())
                        .putExtra(ConstantString.CONTENT, mail_details_content.getText().toString())
                        .putExtra(ConstantString.OLD_ID, annObjects.get(0).getFileid())
                        .putExtra(ConstantString.OLD_PATH, annObjects.get(0).getNewName())
                        .putExtra(ConstantString.OLD_FILE, annObjects.get(0).getOldName()));
                break;
            case R.id.mail_forward:
                // 转发
                startActivity(new Intent(context, Mail_Forward_Activity.class)
                        .putExtra(ConstantString.STATUS, 2)
                        .putExtra(ConstantString.ID, id)
                        .putExtra(ConstantString.RECEIVER, mail_details_person.getText().toString())
                        .putExtra(ConstantString.SUBJECT, mail_details_title.getText().toString())
                        .putExtra(ConstantString.CONTENT, mail_details_content.getText().toString())
                        .putExtra(ConstantString.OLD_ID, annObjects.get(0).getFileid())
                        .putExtra(ConstantString.OLD_PATH, annObjects.get(0).getNewName())
                        .putExtra(ConstantString.OLD_FILE, annObjects.get(0).getOldName()));
                break;
//            case R.id.mail_delete:
//                break;
        }
    }
}
