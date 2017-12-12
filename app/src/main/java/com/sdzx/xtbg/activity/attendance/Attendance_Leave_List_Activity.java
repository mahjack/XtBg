package com.sdzx.xtbg.activity.attendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.Attendance_Leave_Apply_Adapter;
import com.sdzx.xtbg.bean.AttendanceListBean;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.StringUtils;
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
 * 请假审批列表/我的申请列表/请假查阅列表（目前所有用户都有查阅的权限）
 * Author:Eron
 * Date: 2016/5/25 0025
 * Time: 16:49
 */
public class Attendance_Leave_List_Activity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;
    @Bind(R.id.holiday_approval_listView)
    ListView holidayApprovalListView;
    @Bind(R.id.tv_list_state)
    TextView listState;
    @Bind(R.id.holiday_approval_refreshLayout)
    SwipeRefreshLayout holidayApprovalRefreshLayout;

    private Attendance_Leave_Apply_Adapter applyAdapter;

    private AttendanceListBean bean = new AttendanceListBean();
    private List<AttendanceListBean.Value> listBeen = new ArrayList<AttendanceListBean.Value>();

    private Context mContext;
    private RequestCall mCall;

    private SharedPreferences preferences;
    private String uid = "";
    private static boolean REFRESHING = false;

    private int in_State = 0;
    private String getDataUrl = "";// 请求网址

    private int number = -1;
    private static final int UPDATE_UI = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    if (number == 0) {
                        listState.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_holiday_approval_list);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        this.mContext = Attendance_Leave_List_Activity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");

        Intent intent = getIntent();
        in_State = intent.getIntExtra(StringUtils.MY_APPROVAL_LIST, 0);
    }

    private void initData() {
        onRefresh();
    }

    private void initView() {
        if (in_State == 0) {
            headerTitle.setText("请假审批");
        } else if (in_State == 2) {
            headerTitle.setText("我的请假查询");
        } else if (in_State == 3) {
            headerTitle.setText("请假查阅");
        }
        headerRight.setVisibility(View.GONE);
        holidayApprovalRefreshLayout.setOnRefreshListener(this);
        applyAdapter = new Attendance_Leave_Apply_Adapter(mContext, listBeen);
        holidayApprovalListView.setAdapter(applyAdapter);
        holidayApprovalRefreshLayout.setColorSchemeResources(R.color.deeppink, R.color.darkorange, R.color.mediumblue);
        holidayApprovalRefreshLayout.setOnRefreshListener(this);

    }

    private void initEvent() {
        holidayApprovalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!REFRESHING) {
                    if (in_State == 0) {
                        startActivity(new Intent(mContext, Attendance_Leave_Detail.class)
                                .putExtra(ConstantString.ATTENDANCE_DETAIL_ID, listBeen.get(position).getId()));
                    } else if (in_State == 2) {
                        startActivity(new Intent(mContext, Attendance_Leave_Detail.class)
                                .putExtra(ConstantString.ATTENDANCE_DETAIL_ID, listBeen.get(position).getId())
                                .putExtra(ConstantString.ATTENDANCE_MY_LIST, 2)
                                .putExtra("qingxiujia", "xiujia"));// 若为2，则将所有有操作的布局隐藏
                    } else if (in_State == 3) {
                        startActivity(new Intent(mContext, Attendance_Leave_Detail.class)
                                .putExtra(ConstantString.ATTENDANCE_DETAIL_ID, listBeen.get(position).getId())
                                .putExtra(ConstantString.ATTENDANCE_MY_LIST, 2)
                                .putExtra("qingxiujia", "qingjia"));// 若为2，则将所有有操作的布局隐藏
                    }
                }
            }
        });


    }

    @OnClick(R.id.header_back)
    void closeThis() {
        finish();
    }

    /**
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        try {

            if (in_State == 0) {
                getDataUrl = ConstantURL.ATTENDANCE_LEAVE_LIST + uid;// 审核列表
            } else if (in_State == 2) {
                getDataUrl = ConstantURL.ATTENDANCE_LEAVE_MY_APPLY_LIST + uid;// 我的申请列表
            } else if (in_State == 3) {
                getDataUrl = ConstantURL.ATTENDANCE_LEAVE_VIEW_LIST + uid;// 请假查阅
            }

            holidayApprovalRefreshLayout.setRefreshing(true);
            REFRESHING = true;
            mCall = OkHttpUtils
                    .get()
                    .url(getDataUrl)
                    .build();
            mCall.execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void inProgress(float progress) {
                    super.inProgress(progress);
                    holidayApprovalRefreshLayout.setRefreshing(true);
                }

                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("tag", "----请假审批列表返回数据---------------------------------->" + response);
                        Gson gson = new Gson();
                        bean = gson.fromJson(response, AttendanceListBean.class);
                        List<AttendanceListBean.Value> list = bean.getValue();
                        if (null == list) {
                            listBeen.clear();
                            applyAdapter.notifyDataSetChanged();
                            holidayApprovalRefreshLayout.setRefreshing(false);
                        } else {
                            listBeen.clear();
                            listBeen.addAll(list);

                            number = bean.getNumber().getNumber();// 条数

                            Message message = new Message();
                            message.what = UPDATE_UI;
                            handler.sendMessage(message);

                            applyAdapter.notifyDataSetChanged();
                            holidayApprovalRefreshLayout.setRefreshing(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    holidayApprovalRefreshLayout.setRefreshing(false);
                    REFRESHING = false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        holidayApprovalRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mCall.cancel();
    }
}
