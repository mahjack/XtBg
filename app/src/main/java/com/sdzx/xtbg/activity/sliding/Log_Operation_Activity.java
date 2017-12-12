package com.sdzx.xtbg.activity.sliding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.sdzx.xtbg.adapter.Operation_Adapter;
import com.sdzx.xtbg.bean.Log_Operation;
import com.sdzx.xtbg.bean.Logged;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.view.AutoListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2015/12/21 0021
 * Tell：15006330640
 */
public class Log_Operation_Activity extends Activity implements View.OnClickListener {
    private static final String TAG = "Log_Operation_Activity";
    // 顶栏
    @Bind(R.id.header_back)
    TextView header_back;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_right)
    ImageView header_right;
    // 列表
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.logged_list)
    ListView logged_list;
    // 对象
    private Context context;
    private List<Logged> loggeds = new ArrayList<Logged>();
    private List<Logged> list = new ArrayList<Logged>();
    private Operation_Adapter adapter;
    private SharedPreferences preferences;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            ArrayList<Logged> result = (ArrayList<Logged>) msg.obj;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_logged);
        ButterKnife.bind(this);
        initConstants();
        readData();
        initViews();
        initEvents();
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = Log_Operation_Activity.this;
    }

    private void initViews() {
        header_title.setText(getString(R.string.logged_operation));
        header_right.setVisibility(View.GONE);
        adapter = new Operation_Adapter(context,list);
        logged_list.setAdapter(adapter);
//        swipeLayout.setColorSchemeColors(R.color.swipe_color_1,
//                R.color.swipe_color_2,
//                R.color.swipe_color_3,
//                R.color.swipe_color_4);
        swipeLayout.setSize(SwipeRefreshLayout.LARGE);
//        swipeLayout.setProgressBackgroundColor(R.color.divider_list);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readData();
            }
        });
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
    }

    private void readData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT,"list");
        params.addQueryStringParameter(ConstantString.UID, preferences.getString(ConstantString.UID,""));
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.OPERATION_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.e("测试：", responseInfo.result);
                    Gson gson = new Gson();
                    Log_Operation object = gson.fromJson(responseInfo.result, Log_Operation.class);
                    if (object != null) {
                        if (!loggeds.isEmpty()) {
                            loggeds.clear();
                        }
                        swipeLayout.setRefreshing(false);
                        loggeds = object.getHanleinfo();
                        loadData(AutoListView.REFRESH);
                    } else {
                        Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
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
    private void loadData(final int what) {
        try {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Message msg = handler.obtainMessage();
                    msg.what = what;
                    msg.obj = loggeds;
                    handler.sendMessage(msg);
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_back:
                finish();
                break;
        }
    }
}
