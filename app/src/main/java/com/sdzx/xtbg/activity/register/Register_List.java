package com.sdzx.xtbg.activity.register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.RegisterListAdapter;
import com.sdzx.xtbg.bean.RegisterList;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.constant.DateUtils;
import com.sdzx.xtbg.tools.TimeUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 签到列表
 * Author:Eron
 * Date: 2016/6/29 0029
 * Time: 16:59
 */
public class Register_List extends Activity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.register_none)
    TextView registerNone;// 无数据
    @Bind(R.id.register_list)
    ListView registerList;
    @Bind(R.id.register_progress)
    ProgressBar registerProgress;// 进度条
    @Bind(R.id.send_doc_refreshLayout)
    SwipeRefreshLayout sendDocRefreshLayout;


    private Context context;
    private SharedPreferences preferences;
    private String userId = "";
    private String userName;
    private String searchDate = "";// 检索时间

    private RegisterListAdapter adapter;
    private RegisterList registerListBean = new RegisterList();
    private List<RegisterList.Register_List_Bean> list_been = new ArrayList<>();
    private RequestCall mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();


    }

    private void initConstant() {
        context = Register_List.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");
        userName = preferences.getString(ConstantString.NAME, "");
    }

    private void initData() {
        onRefresh();
    }

    private void initView() {
        headerTitle.setText("签到列表");
        headerRight.setText("按日期检索");
        headerRight.setVisibility(View.GONE);
        sendDocRefreshLayout.setOnRefreshListener(this);
        adapter = new RegisterListAdapter(context, list_been);
        registerList.setAdapter(adapter);
        sendDocRefreshLayout.setColorSchemeResources(R.color.deeppink, R.color.darkorange, R.color.mediumblue);

    }

    @OnClick(R.id.header_right)
    void searchWithDate() {
        DatePickerDialog datePickerDialogStart = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                searchDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) :
                        (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                KLog.d("检索日期------>", searchDate);
                searchDataWithDate(searchDate);
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
        datePickerDialogStart.show();
    }

    /**
     * 按时间检索
     */
    private void searchDataWithDate(String date) {
        List<RegisterList.Register_List_Bean> registerListBeen = new ArrayList<>();
        adapter = new RegisterListAdapter(context, registerListBeen);
        registerList.setAdapter(adapter);
        for (RegisterList.Register_List_Bean bean : list_been) {
            if (TimeUtils.getDateToString3(Long.parseLong(bean.getAddtime()) * 1000).equals(date)) {
                registerListBeen.add(bean);
            }
        }
        if (registerListBeen.isEmpty()) {
            // 为空
            registerListBeen.clear();
            registerNone.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        } else {
            registerNone.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    private void initEvent() {
        headerBack.setOnClickListener(this);

    }

    @Override
    public void onRefresh() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.REGISTER_LIST + userId + "&act=list")
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
//                    KLog.json("签到列表-------->", response);
                    Gson gson = new Gson();
                    registerListBean = gson.fromJson(response, RegisterList.class);
                    List<RegisterList.Register_List_Bean> list = registerListBean.getInfo();

                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setName(userName);
                    }

                    if (null == list) {
                        list_been.clear();
                        adapter.notifyDataSetChanged();
                        sendDocRefreshLayout.setRefreshing(false);

                    } else {
                        list_been.clear();
                        list_been.addAll(list);
                        adapter.notifyDataSetChanged();
                        sendDocRefreshLayout.setRefreshing(false);
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
