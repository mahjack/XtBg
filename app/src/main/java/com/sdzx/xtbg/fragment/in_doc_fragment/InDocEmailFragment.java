package com.sdzx.xtbg.fragment.in_doc_fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.sdzx.xtbg.activity.in_doc.IntranetDocumentDetails;
import com.sdzx.xtbg.adapter.IntranetDocumentAdapter;
import com.sdzx.xtbg.bean.Document_Intranet;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.view.MySwipeRefreshLayout;
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
 * 发文查阅
 * Author:Eron
 * Date: 2016/6/23 0023
 * Time: 22:10
 */
public class InDocEmailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.send_doc_list)
    ListView sendDocList;
    @Bind(R.id.send_doc_refreshLayout)
    MySwipeRefreshLayout sendDocRefreshLayout;
    @Bind(R.id.loadView)
    LoadingView loadView;
    @Bind(R.id.tv_list_state)
    TextView tvListState;
    // 对象
    private Context context;
    private SharedPreferences preferences;
    private String search_str = "";

    private List<Document_Intranet.Intranet> values = new ArrayList<>();
    private IntranetDocumentAdapter documentAdapter;

    private RequestCall mCall;

    private int page = 1;


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
        initViews();
        initEvents();
    }

    private void initViews() {

        sendDocRefreshLayout.setOnRefreshListener(this);
        documentAdapter = new IntranetDocumentAdapter(context, values, 2);
        sendDocList.setAdapter(documentAdapter);
        sendDocRefreshLayout.setColorSchemeResources(R.color.deeppink, R.color.darkorange, R.color.mediumblue);

        sendDocRefreshLayout.setOnLoadListener(new MySwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getData(page++);
            }
        });

    }

    private void initConstants() {
        context = getActivity();
        preferences = getActivity().getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
    }

    private void initEvents() {

        sendDocList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (values.get(position).getGet() == 0) {
                    Toast.makeText(context, "未签收,请先签收", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, IntranetDocumentDetails.class)
                            .putExtra(ConstantString.ID, values.get(position).getId())
                            .putExtra(ConstantString.STATUS, 2)
                            .putExtra(ConstantString.INTRANET, values.get(position)));// 2 为内网邮件
                }
            }
        });

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
     * 获取数据
     *
     * @param page
     */
    public void getData(final int page) {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.IN_EMAIL_LIST + page)
                .build();
        mCall.readTimeOut(30 * 1000);
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
//                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    loadView.setVisibility(View.GONE);
                    KLog.json("内网文件------>", response);
                    Gson gson = new Gson();
                    Document_Intranet intranet = gson.fromJson(response, Document_Intranet.class);
                    if (intranet != null) {
                        if (page == 1) {
                            values.clear();
                        }
                        List<Document_Intranet.Intranet> list = intranet.getInfo();
                        values.addAll(list);
                        documentAdapter.notifyDataSetChanged();
                        sendDocRefreshLayout.setLoading(false);
                    } else {
                        tvListState.setVisibility(View.VISIBLE);
                    }
                    sendDocRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 刷新请求数据
     */
    @Override
    public void onRefresh() {
        sendDocRefreshLayout.setRefreshing(true);
        getData(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCall.cancel();
    }

}
