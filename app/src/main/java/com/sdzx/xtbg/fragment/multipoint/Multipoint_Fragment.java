package com.sdzx.xtbg.fragment.multipoint;

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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.DocumentAdapter;
import com.sdzx.xtbg.bean.Document;
import com.sdzx.xtbg.bean.Document_Object;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.view.AutoListView;
import com.sdzx.xtbg.view.loading.LoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2016/3/18 0018
 * Tell：15006330640
 *
 *  多点分发
 */
public class Multipoint_Fragment extends Fragment implements AutoListView.OnRefreshListener, AutoListView.OnLoadListener, View.OnClickListener{
    // 列表
    @ViewInject(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @ViewInject(R.id.backlog_list)
    ListView backlog_list;
    // 加载
    @ViewInject(R.id.loadView)
    LoadingView loadingView;
    // 暂无数据
    @ViewInject(R.id.document_no_data)
    TextView document_no_data;
    // 对象
    private Context context;
    private DocumentAdapter adapter;
    private List<Document> annObjects = new ArrayList<Document>();
    private List<Document> list = new ArrayList<Document>();
    private SharedPreferences preferences;
    private String search_str = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            ArrayList<Document> result = (ArrayList<Document>) msg.obj;
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
        };
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_fragment_backlog,container,false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConstants();
        refreshData();
        initViews();
        initEvents();
    }

    private void initConstants() {
        context = getActivity();
        preferences = getActivity().getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
    }

    private void initViews() {
        adapter = new DocumentAdapter(context,list,false);
        backlog_list.setAdapter(adapter);
        backlog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(context, Multipoint_Details_Activity.class)
//                        .putExtra("id", list.get(position).getId()));
            }
        });
        swipeLayout.setColorSchemeColors(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
        swipeLayout.setSize(SwipeRefreshLayout.LARGE);
//        swipeLayout.setProgressBackgroundColor(R.color.divider_list);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void initEvents() {
    }

    private void refreshData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT,"ddlist");
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID,""));
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.DOCUMENT_BACKLOG_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.e("测试：", responseInfo.result);
                    Gson gson = new Gson();
                    Document_Object object = gson.fromJson(responseInfo.result, Document_Object.class);
                    if (object != null) {
                        if (!annObjects.isEmpty()) {
                            annObjects.clear();
                        }
                        Log.e("读取数据", String.valueOf(object.getValue()));
                        if (object.getValue().isEmpty()) {
                            Log.e("读取数据", "---");
                            document_no_data.setVisibility(View.VISIBLE);
                        } else {
                            Log.e("读取数据", "buweikong");
                        }
                        loadingView.setVisibility(View.GONE);
                        swipeLayout.setRefreshing(false);
                        annObjects = object.getValue();
                        loadData(AutoListView.REFRESH);
                    } else {
                        Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                        loadingView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    loadingView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingView.setVisibility(View.GONE);
            }
        });
    }
    private void loadData(){}
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
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
    /**
     *  搜索
     */
    public void search() {
//        Intent intent = new Intent(context,Search_Activity.class);
//        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == getActivity().RESULT_OK){
            switch (requestCode){
                case 1:
                    search_str = data.getStringExtra("search");
                    Log.e("检索：", search_str );
                    adapter.getFilter().filter(search_str);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
