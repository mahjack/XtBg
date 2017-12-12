package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.Flow_Adapter;
import com.sdzx.xtbg.bean.Flow;
import com.sdzx.xtbg.bean.Flow_Object;
import com.sdzx.xtbg.constant.ConstantString;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Author：Mark
 * Date：2015/12/15 0015
 * Tell：15006330640
 * <p/>
 * 流程
 */
public class Flow_Dialog {
    private static final String TAG = "Flow_Dialog";
    private Context context;
    private Display display;
    private Dialog dialog;
    private ListView flow_list;
    private LinearLayout dialog_ll;
    private ArrayList<Flow_Object> objects;
    private Flow_Adapter adapter;

    public Flow_Dialog(Context context) {
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
    }

    public Flow_Dialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_flow, null);
        dialog_ll = (LinearLayout) view.findViewById(R.id.dialog_ll);
        flow_list = (ListView) view.findViewById(R.id.flow_list);
        // Dialog
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整Dialog大小
        dialog_ll.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public Flow_Dialog readData(String url, int a) {
        OkHttpUtils
                .get()
                .url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("流程图返回数据----->", response);
                    Gson gson = new Gson();
                    Flow flow = gson.fromJson(response, Flow.class);
                    if (flow != null) {
                        objects = flow.getData();
                        adapter = new Flow_Adapter(context, objects);
                        flow_list.setAdapter(adapter);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return this;
    }

    public Flow_Dialog readData(String url, String act, String uid, String id) {
//        Log.e("tag", "请假流程URL------------------>" + ConstantURL.ATTENDANCE_LEAVE_FLOW + "?act=liucheng&uid=" + uid + "&id=" + id);
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, act);
        params.addQueryStringParameter(ConstantString.UID, uid);
        params.addQueryStringParameter(ConstantString.ID, id);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    KLog.json("流程图返回数据----->", responseInfo.result);
                    Gson gson = new Gson();
                    Flow flow = gson.fromJson(responseInfo.result, Flow.class);
                    if (flow != null) {
                        objects = flow.getInfo();
                        adapter = new Flow_Adapter(context, objects);
                        flow_list.setAdapter(adapter);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        return this;
    }

    public Flow_Dialog readData(String url, String act, String uid, String id, int a) {
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, act);
        params.addQueryStringParameter(ConstantString.UID, uid);
        params.addQueryStringParameter(ConstantString.ID, id);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    KLog.json("流程图返回数据----->", responseInfo.result);
                    Gson gson = new Gson();
                    Flow flow = gson.fromJson(responseInfo.result, Flow.class);
                    if (flow != null) {
                        objects = flow.getData();
                        adapter = new Flow_Adapter(context, objects);
                        flow_list.setAdapter(adapter);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        return this;
    }

    public Flow_Dialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void show() {
        // 初始化控件
//        setLayout();
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
