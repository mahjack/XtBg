package com.sdzx.xtbg.activity.notices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.File_Adapter;
import com.sdzx.xtbg.adapter.SpinnerInfoAdapter;
import com.sdzx.xtbg.bean.Document_Add_Response;
import com.sdzx.xtbg.bean.ReadAddType;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.dialog.PersonDialog;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.StringUtils;
import com.sdzx.xtbg.view.NoScrollListView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 信息中心添加
 * Author:Eron
 * Date: 2016/7/16 0016
 * Time: 10:53
 */
public class Info_Center_Add_Activity extends Activity {


    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;

    @Bind(R.id.info_add_type)
    Spinner infoAddType;//发文类型
    @Bind(R.id.info_add_title)
    EditText infoAddTitle;// 标题
    @Bind(R.id.info_add_content)
    EditText infoAddContent;// 内容
    @Bind(R.id.info_add_attach)
    ImageView infoAddAttach;// 附件添加
    @Bind(R.id.info_add_attach_list)
    NoScrollListView infoAddAttachList;// 附件列表
    @Bind(R.id.info_add_receiver)
    TextView infoAddReceiver;// 权限查看接收人员
    @Bind(R.id.info_add_submit)
    Button infoAddSubmit;// 提交


    private Context context;
    private SharedPreferences preferences;
    private String userId, user_idk, select_userID = "";

    private SpinnerInfoAdapter spinnerInfoAdapter;//发文类型Adapter
    private int essayTypeId = 0;

    private ReadAddType type;
    private List<ReadAddType.Mtype> mtypes = new ArrayList<>();

    private RequestCall mCall;

    // 附件相关
    private File_Adapter adapter;
    public static List<String> path_list = new ArrayList();
    public static List<String> name_list = new ArrayList<>();
    public static List<String> file_id = new ArrayList<>();
    private int status = 3;
    private static final int FILE_SELECT_CODE = 5;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    Log.e("更新附件", path_list.size() + "");
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_center_add);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();

    }

    @OnClick(R.id.header_back)
    void close() {
        finish();
    }

    /**
     * 选择接收人
     */
    @OnClick(R.id.info_add_receiver)
    void choiceReceiver() {
        new PersonDialog(context)
                .readData(ConstantURL.ADDRESS_URL)
                .builder()
                .setTitle("分办人")
                .setCancelable(false)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KLog.d("分办人------>", StringUtils.name + "----" + StringUtils.user_id);
                        // 接收人ID
                        user_idk = StringUtils.user_id;
                        // 显示接收人
                        infoAddReceiver.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                        select_userID = user_idk.substring(0, user_idk.length() - 1);
                        KLog.d("已选中接收人------>", select_userID);
                    }
                })
                .show();
    }

    private void initConstant() {
        this.context = Info_Center_Add_Activity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");
    }

    private void initData() {
        requestInfoType();

    }


    private void initView() {
        headerTitle.setText("信息中心添加");
        headerRight.setVisibility(View.GONE);

        // 附件Adapter
        adapter = new File_Adapter(context, path_list, name_list, file_id, true, status);
        infoAddAttachList.setAdapter(adapter);


//        spinnerInfoAdapter = new SpinnerInfoAdapter(context, mtypes);
//        infoAddType.setAdapter(spinnerInfoAdapter);

    }

    private void initEvent() {
        infoAddType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                essayTypeId = mtypes.get(position).getId();
                KLog.d("发文类型ID------>", essayTypeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                essayTypeId = 32;
            }
        });
    }

    /**
     * 添加附件
     */
    @OnClick(R.id.info_add_attach)
    void selectAttach() {
        showFileChooser();
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
                    KLog.d("媒体文件地址------>", path);
                    Log.e("选择的文件：", path + "-----" + uri.toString());
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

    /**
     * 提交数据
     */
    @OnClick(R.id.info_add_submit)
    void submitData() {
        if (checkInput() == true) {
            submitDataWithAttach();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }

    private boolean checkInput() {
        if (infoAddTitle.getText().toString().equals("")) {
            Toast.makeText(context, "请输入文章标题", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoAddContent.getText().toString().equals("")) {
            Toast.makeText(context, "请输入文章n内容", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoAddReceiver.getText().toString().equals("")) {
            Toast.makeText(context, "请选择接收人", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 清空附件
     */
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
     * 发文类型
     */
    private void requestInfoType() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.READ_ADD_TYPE + userId)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "请求服务器失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("发文类型------>", response);
                    Gson gson = new Gson();
                    type = gson.fromJson(response, ReadAddType.class);
                    if (type.getMtype() != null) {
                        mtypes = type.getMtype();
                        spinnerInfoAdapter = new SpinnerInfoAdapter(context, mtypes);
                        infoAddType.setAdapter(spinnerInfoAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上传带附件数据
     */
    private void submitDataWithAttach() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("title", infoAddTitle.getText().toString());
        requestParams.addBodyParameter("content", infoAddContent.getText().toString());
        requestParams.addBodyParameter("userid", select_userID);
        requestParams.addBodyParameter("mid", essayTypeId + "");
        KLog.d("权限查看人员------>", "");

        for (int i = 0; i < path_list.size(); i++) {
            File file = new File(path_list.get(i).replace("file://", ""));
            KLog.d("文件上传路径------>", file);
            if (file.exists()) {
                KLog.d("文件存在------>路径", path_list.get(i));
                requestParams.addBodyParameter("file" + i, file);
            } else {
                KLog.d("文件不存在----->", "");
            }
        }


        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.READ_ADD + userId, requestParams, new RequestCallBack<String>() {
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
                Toast.makeText(context, "网络错误！请连接网络后重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
