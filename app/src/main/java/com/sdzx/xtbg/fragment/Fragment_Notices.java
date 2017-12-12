package com.sdzx.xtbg.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.sdzx.xtbg.activity.notices.Info_Center_Add_Activity;
import com.sdzx.xtbg.activity.notices.Notices_Details_Activity;
import com.sdzx.xtbg.adapter.NoticesAdapter;
import com.sdzx.xtbg.bean.Ann;
import com.sdzx.xtbg.bean.Ann_Object;
import com.sdzx.xtbg.bean.GetDataDao;
import com.sdzx.xtbg.bean.GetDataDaoImpl;
import com.sdzx.xtbg.bean.LoadCallBack;
import com.sdzx.xtbg.bean.RequestVo;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.view.AutoListView;
import com.sdzx.xtbg.view.loading.LoadingView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Notices extends Fragment implements View.OnClickListener, AutoListView.OnRefreshListener, AutoListView.OnLoadListener {


    @Bind(R.id.header_back)
    TextView header_back;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_right)
    TextView header_right;
    @Bind(R.id.notices_list)
    ListView notices_list;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.loadView)
    LoadingView loadView;
    @Bind(R.id.header2)
    LinearLayout header2;
    @Bind(R.id.notice_state_tv)
    TextView noticeStateTv;

    // 对象
    private Context context;
    private NoticesAdapter adapter;
    private int page = 0;
    private List<Ann_Object> annObjects = new ArrayList<Ann_Object>();
    private List<Ann_Object> list = new ArrayList<Ann_Object>();
    private SharedPreferences preferences;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            ArrayList<Ann_Object> result = (ArrayList<Ann_Object>) msg.obj;
            switch (msg.what) {
                case AutoListView.REFRESH:
                    list.clear();
                    list.addAll(result);
                    break;
                case AutoListView.LOAD:
                    list.addAll(result);
                    break;
            }
            adapter.notifyDataSetChanged();
        }


    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sliding_notices, container, false);
        ButterKnife.bind(this, view);
        header2.setVisibility(View.GONE);
        initConstants();
        refreshData();
        initView();
        return view;
    }

    private void initConstants() {
        preferences = getActivity().getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = getActivity();
    }

    private void initView() {
        header_title.setText(getString(R.string.home_read));
        header_back.setOnClickListener(this);
        header_right.setText("添加");
        header_right.setVisibility(View.GONE);
        header_right.setOnClickListener(this);
        notices_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, Notices_Details_Activity.class);
                intent.putExtra(ConstantString.ID, annObjects.get(position).getId());
                intent.putExtra(ConstantString.TITLE, annObjects.get(position).getTitle());
                intent.putExtra(ConstantString.TIME, annObjects.get(position).getAddtime());
//                intent.putExtra("content",annObjects.get(position).getContent());
//                Log.e("并读内容：",annObjects.get(position).getContent());
                startActivity(intent);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:

                break;
            case R.id.header_right:
                startActivity(new Intent(context, Info_Center_Add_Activity.class));
                break;
        }
    }

    // 刷新
    private void refreshData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, "list");
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID, ""));
        Log.e("------", preferences.getString(ConstantString.UID, ""));
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantURL.READ_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    swipeLayout.setRefreshing(false);
                    Gson gson = new Gson();
                    KLog.json("综合必读刷新------>", responseInfo.result);
                    Ann ann = gson.fromJson(responseInfo.result, Ann.class);
                    if (ann != null) {
                        if (!annObjects.isEmpty()) {
                            annObjects.clear();
                        }
                        KLog.d("运行了");
                        noticeStateTv.setVisibility(View.GONE);
                        loadView.setVisibility(View.GONE);
                        swipeLayout.setRefreshing(false);
                        annObjects = ann.getInfo();
                        initList();
                    } else {
                        noticeStateTv.setVisibility(View.VISIBLE);
                        // code 为空
                        swipeLayout.setRefreshing(false);
                        loadView.setVisibility(View.GONE);
                        Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    loadView.setVisibility(View.GONE);
                    noticeStateTv.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                swipeLayout.setRefreshing(false);
                loadView.setVisibility(View.GONE);
            }
        });
    }

    private void initList() {
        adapter = new NoticesAdapter(context, annObjects);
        notices_list.setAdapter(adapter);
    }

    // 加载
    private void loadData() {
        GetDataDao getDataDao = new GetDataDaoImpl();
        RequestVo requestVo = new RequestVo();

        requestVo.setRequestUrl(ConstantURL.READ_URL);

        requestVo.setContext(context);

        requestVo.setHttpMethod(HttpRequest.HttpMethod.POST);

        RequestParams requestParams = new RequestParams();

        requestParams.addBodyParameter("act", "list");

        requestParams.addBodyParameter("uid", preferences.getString(ConstantString.UID, ""));

        requestVo.setRequestParams(requestParams);

        getDataDao.getData(requestVo, new LoadCallBack() {
                    @Override
                    public void onLoadSuccess(String obj) {
                        Gson gson = new Gson();
                        KLog.d("综合必读------>", obj);
                        Ann ann = gson.fromJson(obj, Ann.class);
                        if (ann != null) {
                            if (!annObjects.isEmpty()) {
                                annObjects.clear();
                            }
                            annObjects = ann.getInfo();
                            loadData(AutoListView.REFRESH);
                        } else {
                            // code 为空
                            Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLoadFail() {

                    }

                    @Override
                    public void onLoading(int progress) {

                    }
                }
        );
    }

    private void loadData(final int what) {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
