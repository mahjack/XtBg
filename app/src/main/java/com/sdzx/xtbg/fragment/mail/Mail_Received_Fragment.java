package com.sdzx.xtbg.fragment.mail;

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
import com.sdzx.xtbg.activity.mail.Mail_Details_Activity;
import com.sdzx.xtbg.adapter.MailAdapter;
import com.sdzx.xtbg.bean.Mail;
import com.sdzx.xtbg.bean.Mail_Object;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.view.AutoListView;
import com.sdzx.xtbg.view.loading.LoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 * <p/>
 * 已收邮件
 */
public class Mail_Received_Fragment extends Fragment implements AutoListView.OnLoadListener, AutoListView.OnRefreshListener {
    private static final String TAG = "Mail_Received_Fragment";
    // 列表
    @ViewInject(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @ViewInject(R.id.fragment_list)
    ListView fragment_list;
    // 加载
    @ViewInject(R.id.loadView)
    LoadingView loadingView;
    // 对象
    private MailAdapter adapter;
    private Context context;

    private List<Mail_Object> annObjects = new ArrayList<Mail_Object>();
    private List<Mail_Object> list = new ArrayList<Mail_Object>();
    private SharedPreferences preferences;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            ArrayList<Mail_Object> result = (ArrayList<Mail_Object>) msg.obj;
            switch (msg.what) {
                case AutoListView.REFRESH:
//                    fragment_list.onRefreshComplete();
                    list.clear();
                    list.addAll(result);
                    break;
                case AutoListView.LOAD:
//                    fragment_list.onLoadComplete();
                    list.addAll(result);

                    break;
            }

//            fragment_list.setResultSize(result.size());
            adapter.notifyDataSetChanged();
        }

        ;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment_list, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConstants();
        initView();
        refreshData();
//        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void initConstants() {
        preferences = getActivity().getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = getActivity();
    }

    private void initView() {
        adapter = new MailAdapter(context, list);
        fragment_list.setAdapter(adapter);
        fragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(context, Mail_Details_Activity.class)
                        .putExtra(ConstantString.STATUS, 1)
                        .putExtra(ConstantString.ID, list.get(position).getId()));
                list.get(position).setTag("1");
                adapter.notifyDataSetChanged();
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void loadingData(final int what) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = annObjects;
                handler.sendMessage(msg);

            }
        }).start();
    }

    @Override
    public void onLoad() {
        loadData();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    private void refreshData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, "shou");
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID, ""));
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantURL.MAIL_HAVE_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("测试：", responseInfo.result);
                Gson gson = new Gson();
                Mail mail = gson.fromJson(responseInfo.result, Mail.class);
                if (mail != null) {
                    loadingView.setVisibility(View.GONE);
                    swipeLayout.setRefreshing(false);
                    annObjects = mail.getMailinfo();
                    loadingData(AutoListView.REFRESH);
                } else {
                    swipeLayout.setRefreshing(false);
                    loadingView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                swipeLayout.setRefreshing(false);
                loadingView.setVisibility(View.GONE);
            }
        });
    }

    private void loadData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, "shou");
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID, ""));
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantURL.MAIL_HAVE_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("测试：", responseInfo.result);
                Gson gson = new Gson();
                Mail mail = gson.fromJson(responseInfo.result, Mail.class);
                if (mail != null) {
                    annObjects = mail.getMailinfo();
                    loadingData(AutoListView.REFRESH);
                } else {

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
