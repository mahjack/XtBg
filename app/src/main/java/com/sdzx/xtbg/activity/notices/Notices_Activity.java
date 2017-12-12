package com.sdzx.xtbg.activity.notices;

import android.annotation.SuppressLint;
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

/**
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 * <p/>
 * 综合必读
 */
public class Notices_Activity extends Activity implements View.OnClickListener, AutoListView.OnRefreshListener, AutoListView.OnLoadListener {
    private static final String TAG = "Notices_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    TextView header_right;
    @ViewInject(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @ViewInject(R.id.notices_list)
    ListView notices_list;
    @ViewInject(R.id.notice_state_tv)
    TextView notice_state_tv;// 状态
    // 加载
    @ViewInject(R.id.loadView)
    LoadingView loadView;
    // 对象
    private Context context;
    private NoticesAdapter adapter;
    private int page = 0;
    private List<Ann_Object> annObjects = new ArrayList<Ann_Object>();
    private List<Ann_Object> list = new ArrayList<Ann_Object>();
    private SharedPreferences preferences;
    String id;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            ArrayList<Ann_Object> result = (ArrayList<Ann_Object>) msg.obj;
            switch (msg.what) {
                case AutoListView.REFRESH:
//                    notices_list.onRefreshComplete();
                    list.clear();
                    list.addAll(result);
                    break;
                case AutoListView.LOAD:
//                    notices_list.onLoadComplete();
                    list.addAll(result);
                    break;
            }
//            notices_list.setResultSize(result.size());
            adapter.notifyDataSetChanged();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_notices);
        ViewUtils.inject(this);
        initConstants();
        refreshData();
        initView();
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = Notices_Activity.this;
        Intent intent=getIntent();
        String mark=intent.getStringExtra("mark");
        header_title.setText(mark);
        id=intent.getStringExtra("id");
    }

    private void initView() {

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
                finish();
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
        params.addQueryStringParameter("mid", id);
        Log.e("------", preferences.getString(ConstantString.UID, ""));//act=list&uid=用户名&mid=栏目id
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.BASE_URL+"zfjc.php", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Gson gson = new Gson();
                    KLog.json("综合必读刷新------>", responseInfo.result);
                    Ann ann = gson.fromJson(responseInfo.result, Ann.class);
                    if (ann != null) {
                        if (!annObjects.isEmpty()) {
                            annObjects.clear();
                        }
                        loadView.setVisibility(View.GONE);
                        swipeLayout.setRefreshing(false);
                        annObjects = ann.getInfo();
                        initList();
                    } else {
                        // code 为空
                        swipeLayout.setRefreshing(false);
                        loadView.setVisibility(View.GONE);
                        Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    loadView.setVisibility(View.GONE);
                    notice_state_tv.setVisibility(View.VISIBLE);
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
}
