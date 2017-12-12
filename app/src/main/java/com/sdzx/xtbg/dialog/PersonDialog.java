package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.LeaderAdapter;
import com.sdzx.xtbg.adapter.PersonAdapter;
import com.sdzx.xtbg.bean.Person;
import com.sdzx.xtbg.bean.Person_User;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.StringUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author：Mark
 * Date：2015/12/11 0011
 * Tell：15006330640
 */
public class PersonDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private LinearLayout alert_layout;
    private RelativeLayout btnAll;
    private TextView dialog_text;
    private ListView lvListView;
    private TextView alert_title, dialog_noData;
    private Button alert_cancel, alert_ensure;
    private ProgressBar dialog_progress;
    private PersonAdapter adapter;
    private LeaderAdapter adapter1;
    private boolean isSingled = false;
    List<Person_User> users = new ArrayList<Person_User>();

    public PersonDialog(Context context) {
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.display = manager.getDefaultDisplay();
    }

    public PersonDialog readData(String act, String id) {
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, act);
        params.addQueryStringParameter(ConstantString.UID, id);
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.DOCUMENT_BACKLOG_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                Person person = gson.fromJson(responseInfo.result, Person.class);
                if (person != null) {
                    users = person.getUser();
                    adapter = new PersonAdapter(context, users);
                    lvListView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                StringUtils.isNo = true;
                Log.e("无数据", StringUtils.isNo + "");
                dialog_noData.setVisibility(View.VISIBLE);
            }
        });
        return this;
    }

    public PersonDialog readData(final String act, String uid, String id) {
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, act);
        params.addQueryStringParameter(ConstantString.UID, uid);
        params.addQueryStringParameter(ConstantString.ID, id);
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.DOCUMENT_BACKLOG_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dialog_progress.setVisibility(View.GONE);
                KLog.json("接收人------>", responseInfo.result);
                Gson gson = new Gson();
                Person person = gson.fromJson(responseInfo.result, Person.class);
                if (person != null) {
                    users = person.getUser();
                    if (null == users || users.isEmpty()) {
                        StringUtils.isNo = true;
                        Log.e("无数据", StringUtils.isNo + "");
                        dialog_noData.setVisibility(View.VISIBLE);
                    } else {
                        dialog_noData.setVisibility(View.GONE);
                        if (act.equals("groupld")) {
                            adapter1 = new LeaderAdapter(context, users);
                            lvListView.setAdapter(adapter1);
                        } else {
                            adapter = new PersonAdapter(context, users);
                            lvListView.setAdapter(adapter);
                        }
                    }
                } else {
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialog_progress.setVisibility(View.GONE);
                StringUtils.isNo = true;
                Log.e("无数据", StringUtils.isNo + "");
                dialog_noData.setVisibility(View.VISIBLE);
            }
        });
        return this;
    }

    /**
     * 通知中心接收人，所有人
     *
     * @param url
     * @return
     */
    public PersonDialog readData(String url) {
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dialog_progress.setVisibility(View.GONE);
                KLog.json("通知中心所有人------>", responseInfo.result);
                Gson gson = new Gson();
                Person person = gson.fromJson(responseInfo.result, Person.class);
                if (person != null) {
                    users = person.getUser();
                    if (null == users || users.isEmpty()) {
                        StringUtils.isNo = true;
                        Log.e("无数据", StringUtils.isNo + "");
                        dialog_noData.setVisibility(View.VISIBLE);
                    } else {
                        dialog_noData.setVisibility(View.GONE);
//                        if (act.equals("groupld")) {
//                            adapter1 = new LeaderAdapter(context, users);
//                            lvListView.setAdapter(adapter1);
//                        } else {
                        adapter = new PersonAdapter(context, users);
                        lvListView.setAdapter(adapter);
//                        }
                    }
                } else {
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialog_progress.setVisibility(View.GONE);
                StringUtils.isNo = true;
                Log.e("无数据", StringUtils.isNo + "");
                dialog_noData.setVisibility(View.VISIBLE);
            }
        });
        return this;
    }

    /**
     * 会议接收人
     *
     * @return
     */
    public PersonDialog readMeetReceiver() {
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantURL.meeting_ADD_SHENPIREN, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dialog_progress.setVisibility(View.GONE);
                KLog.json("会议接收人------>", responseInfo.result);
                Gson gson = new Gson();
                Person person = gson.fromJson(responseInfo.result, Person.class);
                if (person != null) {
                    users = person.getUser();
                    if (null == users || users.isEmpty()) {
                        StringUtils.isNo = true;
                        KLog.d("无数据", StringUtils.isNo + "");
                        dialog_noData.setVisibility(View.VISIBLE);
                    } else {
                        dialog_noData.setVisibility(View.GONE);
                        adapter = new PersonAdapter(context, users);
                        lvListView.setAdapter(adapter);
                    }
                } else {
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialog_progress.setVisibility(View.GONE);
                StringUtils.isNo = true;
                KLog.d("无数据", StringUtils.isNo + "");
                dialog_noData.setVisibility(View.VISIBLE);
            }
        });
        return this;
    }

    // 督察督办接口
    public PersonDialog readData(String url, final String act, String uid, String id) {
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        params.addQueryStringParameter(ConstantString.ACT, act);
        params.addQueryStringParameter(ConstantString.UID, uid);
        params.addQueryStringParameter(ConstantString.ID, id);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dialog_progress.setVisibility(View.GONE);
//                Log.e("数据", responseInfo.result);
                Gson gson = new Gson();
                Person person = gson.fromJson(responseInfo.result, Person.class);
                if (person != null) {
                    users = person.getUser();
                    if (null == users || users.isEmpty()) {
                        StringUtils.isNo = true;
                        Log.e("无数据", StringUtils.isNo + "");
                        dialog_noData.setVisibility(View.VISIBLE);
                    } else {
                        dialog_noData.setVisibility(View.GONE);
                        adapter1 = new LeaderAdapter(context, users);
                        lvListView.setAdapter(adapter1);
                        if (act.equals("groupfg")) {
                            adapter1 = new LeaderAdapter(context, users);
                            lvListView.setAdapter(adapter1);
                        } else {
                            adapter = new PersonAdapter(context, users);
                            lvListView.setAdapter(adapter);
                        }
                    }
                } else {
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialog_progress.setVisibility(View.GONE);
                StringUtils.isNo = true;
                Log.e("无数据", StringUtils.isNo + "");
                dialog_noData.setVisibility(View.VISIBLE);
            }
        });
        return this;
    }

    public PersonDialog builder() {
        // 获取布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_person, null);
        // 获取控件
        alert_layout = (LinearLayout) view.findViewById(R.id.alert_layout);
        alert_title = (TextView) view.findViewById(R.id.dialog_title);// 标题
        alert_cancel = (Button) view.findViewById(R.id.alert_cancel); // 取消
        alert_ensure = (Button) view.findViewById(R.id.alert_ensure); // 确定
        btnAll = (RelativeLayout) view.findViewById(R.id.btnAll); // 全选
        dialog_text = (TextView) view.findViewById(R.id.dialog_text);
        lvListView = (ListView) view.findViewById(R.id.lvListView); // 列表
        dialog_noData = (TextView) view.findViewById(R.id.dialog_noData);
        dialog_progress = (ProgressBar) view.findViewById(R.id.dialog_progress);
        // 定义Dialog布局参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整Dialog大小
        alert_layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), (int) (display.getHeight() * 0.85)));
        lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() instanceof PersonAdapter.ViewHolder) {
                    PersonAdapter.ViewHolder holder = (PersonAdapter.ViewHolder) view.getTag();
                    // 会自动出发CheckBox的checked事件
                    holder.cbCheck.toggle();
                }
            }
        });
        // 全选
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isNo) {
                    if (dialog_text.getText().toString().trim().equals("全选")) {
                        // 所有项目全部选中
                        adapter.configCheckMap(true);
                        adapter.notifyDataSetChanged();
                        dialog_text.setText("全不选");
                    } else {
                        // 所有项目全部不选中
                        adapter.configCheckMap(false);
                        adapter.notifyDataSetChanged();
                        dialog_text.setText("全选");
                    }
                }
            }
        });
        return this;
    }

    public PersonDialog setTitle(String title) {
        if ("".equals(title)) {
            alert_title.setText("标题");
        } else {
            alert_title.setText(title);
        }
        return this;
    }

    public PersonDialog setSingle(boolean isSingle) {
        isSingled = isSingle;
        if (isSingled) {
            btnAll.setVisibility(View.GONE);
        } else {
            btnAll.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public PersonDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public PersonDialog setPositiveButton(String text, final View.OnClickListener listener) {
        if ("".equals(text)) {
            alert_ensure.setText("确定");
        } else {
            alert_ensure.setText(text);
        }
        alert_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSingled) {

                } else {
                    if (StringUtils.isNo) {

                    } else {
                        Log.e("有数据", "");
                        StringUtils.name = "";
                        StringUtils.user_id = "";
            /*
             * 删除算法最复杂,拿到checkBox选择寄存map
			 */
                        Map<Integer, Boolean> map = adapter.getCheckMap();

                        // 获取当前的数据数量
                        int count = adapter.getCount();
                        // 进行遍历
                        for (int i = 0; i < count; i++) {
                            // 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
                            int position = i - (count - adapter.getCount());
                            if (map.get(i) != null && map.get(i)) {
                                Person_User bean = (Person_User) adapter.getItem(position);
                                StringUtils.name += bean.getRealname() + "、";
                                StringUtils.user_id += bean.getId() + "|";
//                        if (bean.isCanRemove()) {
//                            adapter.getCheckMap().remove(i);
//                            adapter.remove(position);
//                        } else {
//                            map.put(position, false);
//                        }
                            }
                        }
                    }
                }
                Log.e("选中A", StringUtils.name + "----" + StringUtils.user_id);
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public PersonDialog setNegativeButton(String text, final View.OnClickListener listener) {
        if ("".equals(text)) {
            alert_cancel.setText("取消");
        } else {
            alert_cancel.setText(text);
        }
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
