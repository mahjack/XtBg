package com.sdzx.xtbg.activity.document;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.sdzx.xtbg.adapter.Document_Process_Adapter_2;
import com.sdzx.xtbg.adapter.File_Document_Adapter;
import com.sdzx.xtbg.bean.Document_Status;
import com.sdzx.xtbg.bean.Document_Value;
import com.sdzx.xtbg.bean.Document_Value_Object;
import com.sdzx.xtbg.bean.JudgeRebut;
import com.sdzx.xtbg.bean.RebutState;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.Util;
import com.sdzx.xtbg.view.NoScrollListView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Arrays;

import okhttp3.Call;

/**
 * 公文详情
 * Author：Mark
 * Date：2015/12/9 0009
 * Tell：15006330640
 */
public class Document_Details_Activity extends Activity implements View.OnClickListener {
    private static final String TAG = "Document_Details_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    // 标题
    @ViewInject(R.id.document_title)
    TextView document_title;
    // 收文世间
    @ViewInject(R.id.document_incoming_time)
    TextView document_incoming_time;
    // 来文方式
    @ViewInject(R.id.document_way)
    TextView document_way;
    // 成文日期
    @ViewInject(R.id.document_create_date)
    TextView document_create_date;
    // 发文机关
    @ViewInject(R.id.document_office)
    TextView document_office;
    @ViewInject(R.id.document_number)
    TextView document_number; // 编号
    @ViewInject(R.id.document_file_quantity)
    TextView document_file_quantity; //来文数量
    @ViewInject(R.id.document_file_number)
    TextView document_file_number; // 文件号
    @ViewInject(R.id.document_file_title)
    TextView document_file_title; // 公文标题
    @ViewInject(R.id.document_expiration)
    TextView document_expiration; // 截止日期
    @ViewInject(R.id.document_content)
    TextView document_content; // 公文内容
    @ViewInject(R.id.file_list)
    NoScrollListView fileList;// 附件
    @ViewInject(R.id.document_btn1)
    Button document_btn1; // 拟办、分法
    @ViewInject(R.id.document_btn2)
    Button document_btn2;// 驳回
    @ViewInject(R.id.document_btn3)
    Button document_btn3;
    @ViewInject(R.id.details_bottom)
    LinearLayout details_bottom;
    @ViewInject(R.id.opinion_list)
    ListView opinion_list; // 拟办意见
    @ViewInject(R.id.document_opinion_ll)
    LinearLayout document_opinion_ll;
    @ViewInject(R.id.agreement_list)
    ListView agreement_list; // 领导批示
    @ViewInject(R.id.document_agreement_ll)
    LinearLayout document_agreement_ll;
    @ViewInject(R.id.reading_list)
    ListView reading_list; // 领导阅示
    @ViewInject(R.id.document_reading_ll)
    LinearLayout document_reading_ll;
    @ViewInject(R.id.result_list)
    ListView result_list; // 办理结果
    @ViewInject(R.id.document_result_ll)
    LinearLayout document_result_ll;
    @ViewInject(R.id.case_list)
    ListView case_list; // 办结情况
    @ViewInject(R.id.document_case_ll)
    LinearLayout document_case_ll;

    @ViewInject(R.id.ll_doc_lwfs)
    LinearLayout ll_doc_lwfs;
    @ViewInject(R.id.ll_doc_cwrq)
    LinearLayout ll_doc_cwrq;

    // 对象
    private Context context;
    private String id;
    private String uid;// 用户
    private Document_Value_Object object;
    private int status = 100;
    private String totag, user_id, tagName;// 公文状态
    private String appendix_url; // 来文内容
    private String file_path = "";
    private Animation scale;
    private SharedPreferences preferences;
    private int file_type = 0;
    private String[] str;
    private RequestCall mCall;

    private String niban_suggestion = "";// 拟办意见
    // 撤回
    private String state, tag, step, comeFrom = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    try {
//                        if (comeFrom.equals("chayue") && state.equals("ok")) {
//                            document_btn2.setText("撤回");
//                            document_btn1.setVisibility(View.GONE);
//                            details_bottom.setVisibility(View.GONE);
//                        } else if (comeFrom.equals("chayue") && state.equals("no")) {
//                            details_bottom.setVisibility(View.GONE);
//                        }else if (comeFrom.equals("daiban")){
//                            document_btn1.setVisibility(View.VISIBLE);
//                            details_bottom.setVisibility(View.VISIBLE);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.document_details);
        ViewUtils.inject(this);
        initConstants();
        readStatus();
//        judgeRebut();// 判断是否可以撤回
        readData();
        initViews();
        initEvents();
    }

    private void initConstants() {
        preferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");
        context = Document_Details_Activity.this;
        Intent intent = super.getIntent();
        id = intent.getStringExtra("id");
        comeFrom = intent.getStringExtra("comeFrom");
        KLog.d("当前公文ID和用户ID------>", id + "-----------" + uid);
//        handler.sendEmptyMessage(1);
        if (comeFrom.equals("daiban")){
            document_btn3.setVisibility(View.VISIBLE);
            details_bottom.setVisibility(View.GONE);
        }else if (comeFrom.equals("chayue")){
            document_btn3.setVisibility(View.GONE);
            details_bottom.setVisibility(View.GONE);
        }
    }

    /**
     * 判断是否可以撤回
     */
    private void judgeRebut() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.DOCUMENT_IS_REBUT + uid + "&id=" + id + "&do=show")
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                KLog.json("公文是否可撤回------->", response);
                try {
                    Gson gson = new Gson();
                    JudgeRebut judgeRebut = gson.fromJson(response, JudgeRebut.class);
                    state = judgeRebut.getState();
                    tag = judgeRebut.getTag();
                    step = judgeRebut.getStep();
                    KLog.d("撤回状态------>", state);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        });
    }


    private void initViews() {
        header_title.setText(getString(R.string.document_details));
        document_btn2.setText("撤回");
        header_right.setImageDrawable(getResources().getDrawable(R.mipmap.finish));
        scale = AnimationUtils.loadAnimation(this, R.anim.finish_scale);
        ll_doc_lwfs.setVisibility(View.GONE);
        ll_doc_cwrq.setVisibility(View.GONE);
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
        document_btn1.setOnClickListener(this);
        document_btn2.setOnClickListener(this);
        document_btn3.setOnClickListener(this);
        header_right.setOnClickListener(this);
        /**
         * 设置监听事件
         */
        document_content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (file_type == 1) {
                    // 打开图片
//                    Intent intent = new Intent(context, ImagePagerActivity.class);
//                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
//                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, str);
//                    context.startActivity(intent);
//                    ((Activity) context).overridePendingTransition(R.anim.override_in_anim, R.anim.override_out_anim);
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
        });
    }

    /**
     * 读取状态
     */
    private void readStatus() {
        document_btn3.setVisibility(View.GONE);
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, "shenpido");
        params.addQueryStringParameter(ConstantString.UID, uid);
        params.addQueryStringParameter(ConstantString.ID, id);
        params.addQueryStringParameter(ConstantString.DO, "show");
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.DOCUMENT_DETAILS_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                System.out.print(responseInfo.result);
                KLog.json("审批状态", responseInfo.result);
                Gson gson = new Gson();
                Document_Status object = gson.fromJson(responseInfo.result, Document_Status.class);
                if (object != null) {
                    if (object.getTag() != null && !object.getTag().equals("")) {
                        status = Integer.parseInt(object.getTag());
                        if (null != object.getTotag()) {
                            totag = object.getTotag();
                        } else {
                            totag = "";
                        }
                        user_id = object.getUserid();
                        tagName = object.getTagName();
                        initStatus();
//                        initSOP();// 初始化操作状态
                    }
                } else {

                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 初始化操作流程
     */
    private void initSOP() {
        if (null !=tagName) {
            document_btn1.setText(tagName);
            details_bottom.setVisibility(View.VISIBLE);
            document_btn2.setVisibility(View.GONE);
        } else if ("chayue".equals(comeFrom)) {
            document_btn2.setText("撤回");
            details_bottom.setVisibility(View.VISIBLE);
            document_btn1.setVisibility(View.GONE);
        }
//        else if (comeFrom.equals("daiban")) {
//
//        }
//        else {
//            details_bottom.setVisibility(View.GONE);
//        }
    }

    /**
     * 初始化状态
     */
    private void initStatus() {
        System.out.print("-------yunxing");
        switch (status) {

            case 0:
                System.out.print("-------yunxing2");
                document_btn3.setText(getString(R.string.document_btn0));
                document_btn3.setVisibility(View.VISIBLE);
                break;
            case 1:
                document_btn3.setText(getString(R.string.document_btn1));
                document_btn3.setVisibility(View.VISIBLE);
                break;
            case 2:
                document_btn3.setText(getString(R.string.document_btn2));
                document_btn3.setVisibility(View.VISIBLE);
                break;
            case 3:
                document_btn3.setText(getString(R.string.document_btn3));
                document_btn3.setVisibility(View.VISIBLE);
                break;
            case 4:
                document_btn3.setText(getString(R.string.document_btn4));
                document_btn3.setVisibility(View.VISIBLE);
                break;
            case 5:
                document_btn3.setText(getString(R.string.document_btn5));
                document_btn3.setVisibility(View.VISIBLE);
                break;
            case 8:
                document_btn3.setText(getString(R.string.document_btn8));
                document_btn3.setVisibility(View.VISIBLE);
                break;
            default:
                document_btn3.setVisibility(View.GONE);
                break;
        }
    }

    private void readData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, "show");
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID, ""));
        params.addQueryStringParameter(ConstantString.ID, id);
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.DOCUMENT_DETAILS_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    KLog.json(responseInfo.result);
                    Gson gson = new Gson();
                    object = gson.fromJson(responseInfo.result, Document_Value_Object.class);
                    if (object != null) {
                        KLog.json(responseInfo.result);
                        initData();
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
        Document_Value value = object.getValue().get(0);
        document_incoming_time.setText(value.getSwtime()); // 收文时间
        document_way.setText(value.getSwtype()); // 收文方式
        document_create_date.setText(value.getCwtime()); // 成文时间
        document_office.setText(value.getDanwei()); // 来文单位
        document_number.setText(value.getBianhao()); // 编号
        document_file_quantity.setText(value.getFenshu()); // 来文份数
        document_file_number.setText(value.getWenhao()); // 来文号
        document_file_title.setText(value.getTitle()); // 标题
        document_expiration.setText(value.getJztime());  // 截止日期
        niban_suggestion = value.getNbcont();// 拟办意见

        if (value.getNewName() != null && !value.getNewName().equals("")) {
            if (value.getNewName().contains(".jpg") || value.getNewName().contains(".jpeg")) {
                document_content.setText(value.getCont() + "\n\n\n\n图片内容请点击查看详情！");
            } else {
                Util.showHtml(value.getCont(), document_content);
            }
        } else {
            Util.showHtml(value.getCont(), document_content);
        }

//        Log.e("来文内容", value.getCont());
//        document_content.setText(Html.fromHtml(value.getCont()));
        appendix_url = value.getCont();
        if (value.getNewName() != null && !value.getNewName().equals("")) {
            // 附件
            str = FileUtils.convertStrToArray(value.getNewName(), "[|]");
            for (int i = 0; i < str.length; i++) {
                KLog.d("附件地址------>" + str[i]);
                if (str[i].contains(".jpg")) {
                    file_type = 1;
                } else {
                    file_type = 2;
                    fileList.setVisibility(View.VISIBLE);
                    File_Document_Adapter adapter = new File_Document_Adapter(context
                            , Arrays.asList(FileUtils.convertStrToArray(value.getNewName()))
                            , Arrays.asList(FileUtils.convertStrToArray(value.getOldName()))
                            , false);
                    fileList.setAdapter(adapter);
                }
            }
        }
        // 拟办意见
        if (object.getValue1() != null) {
            document_opinion_ll.setVisibility(View.VISIBLE);
            if (!object.getValue1().isEmpty()) {
                Document_Process_Adapter_2 adapter = new Document_Process_Adapter_2(context, object.getValue1());
                opinion_list.setAdapter(adapter);
            }
        } else {
            document_opinion_ll.setVisibility(View.GONE);
        }
        // 领导批示
        if (object.getValue2() != null) {
            document_agreement_ll.setVisibility(View.VISIBLE);
            if (!object.getValue2().isEmpty()) {
                Document_Process_Adapter_2 adapter = new Document_Process_Adapter_2(context, object.getValue2());
                agreement_list.setAdapter(adapter);
            }
        } else {
            document_agreement_ll.setVisibility(View.GONE);
        }
        // 领导阅示
        if (object.getValue3() != null) {
            document_reading_ll.setVisibility(View.VISIBLE);
            if (!object.getValue3().isEmpty()) {
                Document_Process_Adapter_2 adapter = new Document_Process_Adapter_2(context, object.getValue3());
                reading_list.setAdapter(adapter);
            }
        } else {
            document_reading_ll.setVisibility(View.GONE);
        }
        // 办理结果
        if (object.getValue4() != null) {
            document_result_ll.setVisibility(View.VISIBLE);
            if (!object.getValue4().isEmpty()) {
                Document_Process_Adapter_2 adapter = new Document_Process_Adapter_2(context, object.getValue4());
                result_list.setAdapter(adapter);
            }
        } else {
            document_result_ll.setVisibility(View.GONE);
        }
        // 办结情况
        if (false) {
            document_case_ll.setVisibility(View.VISIBLE);
            if (!object.getValue4().isEmpty()) {
                Document_Process_Adapter_2 adapter = new Document_Process_Adapter_2(context, object.getValue4());
                case_list.setAdapter(adapter);
            }
        } else {
            document_case_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_right:
                // 刷新
                header_right.startAnimation(scale);
                readData();
                break;
            case R.id.document_btn3:
                // 操作
                startActivity(new Intent(context, Document_Process_Activity.class)
                        .putExtra("act", "shenpido")
                        .putExtra(ConstantString.STATUS, status)
                        .putExtra(ConstantString.TOTAG, totag)
                        .putExtra(ConstantString.ID, id)
                        .putExtra(ConstantString.TITLE, document_file_title.getText().toString())
                        .putExtra("SOP", tagName)
                        .putExtra(ConstantString.NIBAN_SUGGESTION, niban_suggestion)
                        .putExtra(ConstantString.USER_ID, user_id));
                KLog.d("办理", "status------>" + status + "---"
                        + "totag------>" + totag + "----"
                        + "id------>" + id);
                break;
            case R.id.document_btn2:
                // 撤回
//                document_rebut();
                break;
        }
    }

    /**
     * 公文撤回
     * gongwen.php?act=che&uid=5&id=2&do=do
     */
    public void document_rebut() {
        mCall = OkHttpUtils
                .post()
                .url(ConstantURL.DOCUMENT_REBUT + uid + "&id=" + id + "&do=do")
                .addParams("state", state)
                .addParams("step", step)
                .addParams("tag", tag)
                .addParams("id", id)
                .addParams("do", "do")
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("提交撤回返回数据------>", response);
                    Gson gson = new Gson();
                    RebutState rebutState = gson.fromJson(response, RebutState.class);
                    String state = rebutState.getState();
                    if (state.equals("ok")) {
                        Toast.makeText(context, "撤回成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        readStatus();
//        readData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        readStatus();
        readData();
    }
}
