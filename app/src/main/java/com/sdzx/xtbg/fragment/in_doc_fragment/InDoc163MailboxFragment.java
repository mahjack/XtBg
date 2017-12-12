package com.sdzx.xtbg.fragment.in_doc_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.in_doc.InMail163DetailActivity;
import com.sdzx.xtbg.adapter.InMail163ListAdapter;
import com.sdzx.xtbg.bean.InMail163List;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.view.loading.LoadingView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 163邮箱文件
 * Author:Eron
 * Date: 2016/7/9 0009
 * Time: 9:14
 */
public class InDoc163MailboxFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.send_doc_list)
    ListView sendDocList;
    @Bind(R.id.send_doc_refreshLayout)
    SwipeRefreshLayout sendDocRefreshLayout;
    @Bind(R.id.loadView)
    LoadingView loadView;
    @Bind(R.id.tv_list_state)
    TextView tvListState;

    private Context context;

    private InMail163ListAdapter inMail163ListAdapter;
    private List<InMail163List.Mail_163> inMail163Lists;
    private RequestCall mCall;

    private static boolean REFRESHING = false;// 判断刷新

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_doc_handling, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        context = getActivity();
    }

    private void initData() {
        getData();
    }

    private void initView() {
        inMail163Lists = new ArrayList<>();
        inMail163ListAdapter = new InMail163ListAdapter(context, inMail163Lists);
        sendDocList.setAdapter(inMail163ListAdapter);

        // 当SwipeRefreshLayout在刷新中的时候上滑出现崩溃
        sendDocList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (REFRESHING) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        sendDocRefreshLayout.setOnRefreshListener(this);
    }

    private void initEvent() {

        sendDocList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!REFRESHING) {
                    startActivity(new Intent(context, InMail163DetailActivity.class)
                            .putExtra("detailID", inMail163Lists.get(position).getId()));
                }
            }
        });
    }

    private void getData() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.IN_MAIL_163_LIST)
                .build();
        mCall.readTimeOut(60 * 1000);
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "请求服务器失败！", Toast.LENGTH_SHORT).show();
                loadView.setVisibility(View.GONE);
                sendDocRefreshLayout.setRefreshing(false);
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);

            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("163邮件列表------>", response);
                    Gson gson = new Gson();
                    InMail163List inMail163List = gson.fromJson(response, InMail163List.class);
                    if (inMail163List != null) {
                        inMail163Lists.clear();
                        List<InMail163List.Mail_163> mail_163 = inMail163List.getInfo();
                        inMail163Lists.addAll(mail_163);
                        inMail163ListAdapter.notifyDataSetChanged();
                    } else {
                        tvListState.setVisibility(View.VISIBLE);
                    }
                    loadView.setVisibility(View.GONE);
                    sendDocRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                REFRESHING = false;
            }
        });
    }

    @Override
    public void onRefresh() {
        REFRESHING = true;
        getData();
    }
}
