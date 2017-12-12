package com.sdzx.xtbg.fragment.sendDocument;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.senddocument.SendDocumentDetailsActivity;
import com.sdzx.xtbg.adapter.SendDocumentListAdapter;
import com.sdzx.xtbg.bean.SendDocumentList;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.view.loading.LoadingView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * 发文待办
 * Author:Eron
 * Date: 2016/6/23 0023
 * Time: 22:05
 */
public class SendDocumentHandling extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.send_doc_list)
    ListView sendDocList;
    @Bind(R.id.send_doc_refreshLayout)
    SwipeRefreshLayout sendDocRefreshLayout;
    @Bind(R.id.loadView)
    LoadingView loadView;
    @Bind(R.id.tv_list_state)
    TextView tvListState;
    // 对象
    private Context context;
    private SharedPreferences preferences;
    private String search_str, uid = "";

    private SendDocumentList sendDocumentList = new SendDocumentList();
    private List<SendDocumentList.Value> values = new ArrayList<>();
    private SendDocumentListAdapter listAdapter;

    private RequestCall mCall;

    private int number = 0;

    private static final int UPDATE_UI = 1;
    private static boolean REFRESHING = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    if (number == 0) {
                        tvListState.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_doc_handling, container, false);
        ViewUtils.inject(this, view);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConstants();
        refreshData();
        initViews();
        initEvents();
    }

    private void initViews() {

        sendDocRefreshLayout.setOnRefreshListener(this);
        listAdapter = new SendDocumentListAdapter(context, values);
        sendDocList.setAdapter(listAdapter);
        sendDocRefreshLayout.setColorSchemeResources(R.color.deeppink, R.color.darkorange, R.color.mediumblue);

    }

    private void initConstants() {
        context = getActivity();
        preferences = getActivity().getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");
    }

    private void initEvents() {

        sendDocList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!REFRESHING) {
                    startActivity(new Intent(context, SendDocumentDetailsActivity.class)
                            .putExtra(ConstantString.SEND_DOC_DETAIL_ID, values.get(position).getId())
                            .putExtra(ConstantString.SEND_DOC_DETAIL_STATES, 0));// 0为办理
                }
            }
        });

    }

    private void refreshData() {
    }

    /**
     * 搜索
     */
    public void search() {
//        Intent intent = new Intent(context, Search_Activity.class);
//        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
//                case 1:
//                    search_str = data.getStringExtra("search");
//                    Log.e("检索：", search_str);
//                    adapter.getFilter().filter(search_str);
//                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 刷新请求数据
     */
    @Override
    public void onRefresh() {
        Log.e("tag", "发文代办地址----------------->" + ConstantURL.SEND_DOCUMENT_HANDLING + uid);

        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.SEND_DOCUMENT_HANDLING + preferences.getString(ConstantString.UID, ""))
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                loadView.setVisibility(View.GONE);
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("tag", "发文待办----------------------------->" + response);
                    Gson gson = new Gson();
                    sendDocumentList = gson.fromJson(response, SendDocumentList.class);
                    List<SendDocumentList.Value> list = sendDocumentList.getValue();
                    if (null == list) {
                        values.clear();
                        listAdapter.notifyDataSetChanged();
                        sendDocRefreshLayout.setRefreshing(false);
                    } else {
                        values.clear();
                        values.addAll(list);

                        number = sendDocumentList.getNumber().getNumber();
                        Message message = new Message();
                        message.what = UPDATE_UI;
                        handler.sendMessage(message);

                        listAdapter.notifyDataSetChanged();
                        loadView.setVisibility(View.GONE);
                        tvListState.setVisibility(View.GONE);
                        sendDocRefreshLayout.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadView.setVisibility(View.GONE);
        sendDocRefreshLayout.setRefreshing(false);
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mCall.cancel();
    }
}
