package com.sdzx.xtbg.activity.document;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.sdzx.xtbg.adapter.Document_Add_Spinner_Adapter;
import com.sdzx.xtbg.adapter.File_Adapter;
import com.sdzx.xtbg.adapter.SpinnerDocComeDepartAdapter;
import com.sdzx.xtbg.bean.DocComeDepart;
import com.sdzx.xtbg.bean.DocumentAddBasic;
import com.sdzx.xtbg.bean.Document_Add_Doc_Come;
import com.sdzx.xtbg.bean.Document_Add_Response;
import com.sdzx.xtbg.bean.FuJian;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.constant.DateUtils;
import com.sdzx.xtbg.dialog.DialogFragment_file;
import com.sdzx.xtbg.dialog.ECProgressDialog;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.ToolUtils;
import com.sdzx.xtbg.view.FlowRadioGroup;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 公文添加
 * Author：Mark
 * Date：2016/6/17 0017
 * Tell：15006330640
 */
public class Document_Add extends FragmentActivity {

    private static final String TAG = "Document_Add";

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.document_add_type_flowRadioGroup)
    FlowRadioGroup flowRadioGroup;// 选择收文类别
    @Bind(R.id.quzhengfu)
    RadioButton quzhengfu;// 区政府
    @Bind(R.id.quwei)
    RadioButton quwei;// 区委
    @Bind(R.id.quzhibumen)
    RadioButton quzhibumen;// 区直部门
    @Bind(R.id.qita)
    RadioButton qita;// 其他
    @Bind(R.id.document_add_choice_date)
    TextView documentAddChoiceDate;// 收文日期
    @Bind(R.id.document_add_come_from)
    Spinner documentAddComeFrom;// 来文机关
    @Bind(R.id.document_add_no)
    EditText documentAddNo;

    @Bind(R.id.ll_doc_laiwen_type)
    LinearLayout ll_doc_laiwen_type;// 来文方式

    @Bind(R.id.document_add_come_from_mode)
    Spinner documentAddComeFromMode;// 来文方式
    @Bind(R.id.document_add_id)
    EditText documentAddId;
    @Bind(R.id.document_add_come_from_no)
    EditText documentAddComeFromNo;
    @Bind(R.id.document_add_time)
    TextView documentAddTime;// 成文日期
    @Bind(R.id.document_add_title)
    EditText documentAddTitle;
    @Bind(R.id.document_add_end_time)
    EditText documentAddEndTime;// 截止日期
    @Bind(R.id.document_add_choice_end_time)
    TextView documentAddChoiceEndTime;// 选择截止日期
    @Bind(R.id.document_add_content)
    EditText documentAddContent;// 来文内容

    @Bind(R.id.document_add_niban)
    EditText documentAddNiban;// 拟办意见

    @Bind(R.id.document_add_attach_list)
    ListView documentAddAttachList;// 上传附件列表
    @Bind(R.id.document_add_attach_list_in)
    ListView documentAddAttachListIn;// 内网附件列表
    @Bind(R.id.document_add_submit)
    Button documentAddSubmit;
    @Bind(R.id.doc_add_attach)
    ImageView llDocAddAttach;// 附件
    @Bind(R.id.niban_FlowRadioGroup)
    FlowRadioGroup nibanFlowRadioGroup;// 拟办人Group
    @Bind(R.id.niban_radioButton1)
    RadioButton nibanRadioButton1;// 拟办人1
    @Bind(R.id.niban_radioButton2)
    RadioButton nibanRadioButton2;// 拟办人1

    private Context context;
    private String comeTime, startTime, endTime, userId;
    // 对象
    private PopupWindow popupWindow;
    private View view;
    private ListView docmentComeModeList;// 来文方式 PopupWindow ListView
    private List<Document_Add_Doc_Come> document_add_doc_comes = new ArrayList<>();
    ArrayAdapter<String> docComeModeAdapter;
    private Document_Add_Spinner_Adapter document_add_spinner_adapter;// 来文方式
    private SpinnerDocComeDepartAdapter docComeDepartAdapter;// 来文机关
    private DocComeDepart docComeDepart = new DocComeDepart();
    private DialogFragment_file dialogFragment_file;
    FuJian mFuJian = null;

    private RequestCall mCall;
    private SharedPreferences preferences;

    private static final int FILE_SELECT_CODE = 5;
    private String old_ID, old_Path, old_File;
    private File_Adapter adapter;
    public static List<String> path_list = new ArrayList();
    public static List<String> name_list = new ArrayList<>();
    public static List<String> file_id = new ArrayList<>();
    private ECProgressDialog mPostingDialog;
    private int status = 3;
    private String docComeType = "1";// 收文类别
    private String laiwenfangshi = "公文传输";// 来文方式
    private String laiwenjiguan = "";// 来文机关
    private List<String> laiwenjiguans = new ArrayList<>();// 来文机关
    private String laiwenjiguan2 = "";
    private String nibanren, nibanrenid, nibanrenFinal, nibanrenIdFinal = "";

    private String laiwendanweicanshu = "danwei";// 来文机关参数名，用于动态改变请求参数
    private String laiwenfangshicanshu = "swtype";//来文方式参数名

    // 内网文件转公文
    private String in_doc_title, in_doc_content, in_doc_time, in_doc_att_oldName, in_doc_att_newName,
            come_from = "";
    private int type = 0;


    private DocumentAddBasic documentAddBasic;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
            } else if (msg.what == 2) {
                Log.e("更新附件", path_list.size() + "");
                adapter.notifyDataSetChanged();
            } else if (msg.what == 3) {
                // 来文机关
                docComeDepartAdapter.notifyDataSetChanged();
            } else if (msg.what == 4) {
//                if (userId.equals("6") || userId.equals("7")) {
//                    nibanRadioButton1.setText("尹德志");
//                } else {
//                    nibanRadioButton1.setText("李婷婷");
//                    nibanRadioButton2.setText("邱彦博");
//                }
                nibanRadioButton1.setText("刘加文");
                nibanRadioButton2.setVisibility(View.GONE);
            } else if (msg.what == 9) {
                documentAddTitle.setText(in_doc_title);
                documentAddTime.setText(in_doc_time);
                if (null != in_doc_content) {
                    documentAddContent.setText(in_doc_content);
                } else {
                    documentAddContent.setText("");
                }
                if (in_doc_att_oldName != null && in_doc_att_newName != null) {
                    File_Adapter adapter = new File_Adapter(context
                            , Arrays.asList(FileUtils.convertStrToArray(in_doc_att_newName))
                            , Arrays.asList(FileUtils.convertStrToArray(in_doc_att_oldName))
                            , null, false, status);
                    documentAddAttachListIn.setAdapter(adapter);
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_add);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();

    }


    private void initConstant() {
        this.context = Document_Add.this;
        preferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");
        type = super.getIntent().getIntExtra("type", 0);
        if (type == 1) {
            initComeFrom();
        }
        initReceiver();
    }

    /**
     * 初始化内网文件接收数据
     */
    private void initComeFrom() {
        Intent intent = getIntent();
        in_doc_title = intent.getStringExtra("in_doc_title");
        in_doc_time = intent.getStringExtra("in_doc_time");
        in_doc_content = intent.getStringExtra("in_doc_content");
        in_doc_att_oldName = intent.getStringExtra("in_doc_att_old_name");
        in_doc_att_newName = intent.getStringExtra("in_doc_att_new_name");
        come_from = intent.getStringExtra("come_from");

        Message message = new Message();
        message.what = 9;
        handler.sendMessage(message);
        KLog.d("Intent接收到的数据------>", in_doc_title
                + "------文件Url" + in_doc_att_newName + "------文件名" + in_doc_att_oldName);
    }

    /**
     * 初始化接收人
     */
    private void initReceiver() {
//        if (userId.equals("6") || userId.equals("7")) {
//            nibanRadioButton2.setVisibility(View.GONE);
//            nibanRadioButton1.setText("尹德志");
//            nibanRadioButton1.setChecked(true);
//            nibanrenIdFinal = "2";
//            nibanFlowRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    if (checkedId == R.id.niban_radioButton1) {
//                        nibanrenIdFinal = "2";
//                    }
//                }
//            });
//        } else {
        // 拟办人
        nibanRadioButton1.setChecked(true);
        nibanrenIdFinal = "4";
        nibanFlowRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    if (checkedId == R.id.niban_radioButton1) {
//                        nibanrenIdFinal = "6";
//                        KLog.d("拟办人ID-------------------->", nibanrenIdFinal);
//                    } else if (checkedId == R.id.niban_radioButton2) {
//                        nibanrenIdFinal = "7";
//                        KLog.d("拟办人ID-------------------->", nibanrenIdFinal);
//                    }
                if (checkedId == R.id.niban_radioButton1) {
                    nibanrenIdFinal = "4";
                }
            }
        });
//        }
    }

    private void initData() {

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
        documentAddAttachList.setAdapter(adapter);

        Document_Add_Doc_Come data1, data2, data3, data4;
        data1 = new Document_Add_Doc_Come();
        data2 = new Document_Add_Doc_Come();
        data3 = new Document_Add_Doc_Come();
        data4 = new Document_Add_Doc_Come();
        data1.setId("1");
        data1.setName("公文传输");
        data2.setId("2");
        data2.setName("电话通知");
        data3.setId("3");
        data3.setName("拿文");
        data4.setId("4");
        data4.setName("其他");
        document_add_doc_comes.add(data1);
        document_add_doc_comes.add(data2);
        document_add_doc_comes.add(data3);
        document_add_doc_comes.add(data4);

        requestDocAddData();// 请求文件录入基础数据
    }

    private void requestDocAddData() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.DOCUMENT_ADD_URL)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, R.string.base_server, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("公文录入基础数据", response);
                    Gson gson = new Gson();
                    DocumentAddBasic basic = gson.fromJson(response, DocumentAddBasic.class);
                    nibanren = basic.getNiban().getName();
                    nibanrenid = basic.getNiban().getUser();
                    Message msg = new Message();
                    msg.what = 4;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        // 默认隐藏软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        headerTitle.setText("收文登记");
        headerRight.setVisibility(View.GONE);
        documentAddChoiceDate.setText(DateUtils.getDateYMD());//设置系统当前时间// 收文日期
        documentAddTime.setText(DateUtils.getDateYMD());//成文日期

        // 来文方式
        document_add_spinner_adapter = new Document_Add_Spinner_Adapter(context, document_add_doc_comes);
        documentAddComeFromMode.setAdapter(document_add_spinner_adapter);

        // 来文机关
        docComeDepartAdapter = new SpinnerDocComeDepartAdapter(context, laiwenjiguans);
        documentAddComeFrom.setAdapter(docComeDepartAdapter);

        ll_doc_laiwen_type.setVisibility(View.GONE);

    }

    /**
     * 收文时间
     */
    @OnClick(R.id.document_add_choice_date)
    void addDate() {
        DatePickerDialog datePickerDialogStart = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                comeTime = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) :
                        (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                documentAddChoiceDate.setText(comeTime);
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
        datePickerDialogStart.show();
    }

    /**
     * 成文时间
     */
    @OnClick(R.id.document_add_time)
    void startDate() {
        DatePickerDialog datePickerDialogStart = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startTime = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) :
                        (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                documentAddTime.setText(startTime);
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
        datePickerDialogStart.show();
    }

    /**
     * 截止日期
     */
    @OnClick(R.id.document_add_choice_end_time)
    void endDate() {
        DatePickerDialog datePickerDialogStart = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endTime = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) :
                        (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                documentAddEndTime.setText(endTime);
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
        datePickerDialogStart.show();
    }

    // 添加附件
    @OnClick(R.id.doc_add_attach)
    void addAttach() {
        showFileChooser();
    }

    @OnClick(R.id.header_back)
    void back() {
        finish();
    }


    @OnClick(R.id.document_add_submit)
    void submit() {
        if (checkInput() == true) {
            // 提交数据
            submitData();
        }
    }

    private void initEvent() {
        // 收文类别
        quzhengfu.setChecked(true);// 默认区政府文件
        requestDocComeDepart();
        flowRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.quzhengfu) {
                    docComeType = "1";
                    laiwenjiguans.clear();
                    requestDocComeDepart();
                    Toast.makeText(context, "市政府文件", Toast.LENGTH_SHORT).show();
                }
                if (checkedId == R.id.quwei) {
                    docComeType = "2";
                    laiwenjiguans.clear();
                    requestDocComeDepart();
                    Toast.makeText(context, "市委文件", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.quzhibumen) {
                    docComeType = "3";
                    laiwenjiguans.clear();
                    requestDocComeDepart();
                    Toast.makeText(context, "市直部门文件", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.qita) {
                    docComeType = "6";
                    laiwenjiguans.clear();
                    requestDocComeDepart();
                    Toast.makeText(context, "其他", Toast.LENGTH_SHORT).show();
                    laiwendanweicanshu = "danwei2";
                }
            }
        });


        // 来文方式
        documentAddComeFromMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                laiwenfangshi = document_add_doc_comes.get(position).getName();
                Log.e("tag", "来文方式----------------------->" + laiwenfangshi);
                if (laiwenfangshi.equals("其他")) {
                    laiwenfangshicanshu = "swtype2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 来文机关
        documentAddComeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                laiwenjiguan2 = laiwenjiguans.get(position);
                Log.e("tag", "来文机关----------------------->" + laiwenjiguan2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                break;
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
     * 检查输入
     *
     * @return
     */
    private boolean checkInput() {
        if (documentAddChoiceDate.getText().toString().equals("")) {
            Toast.makeText(context, "请选择收文日期", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentAddNo.getText().toString().equals("")) {
            Toast.makeText(context, "请输入编号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentAddId.getText().toString().equals("")) {
            Toast.makeText(context, "请选择文件号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentAddComeFromNo.getText().toString().equals("")) {
            Toast.makeText(context, "请输入来文份数", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentAddTime.getText().toString().equals("")) {
            Toast.makeText(context, "请选择成文时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentAddTitle.getText().toString().equals("")) {
            Toast.makeText(context, "请输入文件标题", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentAddContent.getText().toString().equals("")) {
            Toast.makeText(context, "请输入文件内容", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentAddNiban.getText().toString().equals("")) {
            Toast.makeText(context, "请输入拟办意见", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 来文机关
     */
    private void requestDocComeDepart() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.DOCUMENT_COME_DEPART + docComeType)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json(response);
                    Gson gson = new Gson();
                    docComeDepart = gson.fromJson(response, DocComeDepart.class);
                    for (int i = 0; i < docComeDepart.getType().size(); i++) {
                        laiwenjiguan = docComeDepart.getType().get(i);
                        laiwenjiguans.add(laiwenjiguan);
                        Message msg3 = handler.obtainMessage();
                        msg3.what = 3;
                        handler.sendMessage(msg3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 提交附件
     */
    private void submitData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();

        requestParams.addBodyParameter("type", docComeType);
        requestParams.addBodyParameter("swtime", documentAddChoiceDate.getText().toString());
        requestParams.addBodyParameter(laiwendanweicanshu, laiwenjiguan2);
        requestParams.addBodyParameter("bianhao", documentAddNo.getText().toString());
        requestParams.addBodyParameter(laiwenfangshicanshu, laiwenfangshi);
        requestParams.addBodyParameter("wenhao", documentAddId.getText().toString());
        requestParams.addBodyParameter("fenshu", documentAddComeFromNo.getText().toString());
        requestParams.addBodyParameter("cwtime", documentAddEndTime.getText().toString());
        requestParams.addBodyParameter("title", documentAddTitle.getText().toString());
        requestParams.addBodyParameter("cont", documentAddContent.getText().toString());
        requestParams.addBodyParameter("nbcont", documentAddNiban.getText().toString());
        requestParams.addBodyParameter("user", nibanrenIdFinal);
        if (come_from.equals("1")) {
            requestParams.addBodyParameter("addr_title", in_doc_att_oldName);// 文件名
            requestParams.addBodyParameter("addr", in_doc_att_newName);// 文件url
        }

        KLog.d("tag", "拟办人--------------------------->" + nibanrenIdFinal);

        for (int i = 0; i < path_list.size(); i++) {
            File file = new File(path_list.get(i).replace("file://", ""));
            KLog.d("文件上传路径------>", file);
            if (file.exists()) {
                KLog.d("tag", "文件存在------>路径" + path_list.get(i));
                requestParams.addBodyParameter("file" + i, file);
            } else {
                KLog.d("tag", "文件不存在----->");
            }
        }


        httpUtils.send(HttpMethod.POST, ConstantURL.DOCUMENT_SUBMIT + userId, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    KLog.d(responseInfo.result);
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
                Toast.makeText(context, R.string.base_server, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }
}
