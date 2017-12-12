package com.sdzx.xtbg.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.in_doc.IntranetDocumentDetails;
import com.sdzx.xtbg.bean.Document_Intranet;
import com.sdzx.xtbg.bean.Intranet_status;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.TimeUtils;
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
 * 发文列表Adapter
 * <p/>
 * Author:Eron
 * Date: 2016/6/24 0024
 * Time: 0:24
 */
public class IntranetDocumentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Document_Intranet.Intranet> values = new ArrayList<>();
    private SharedPreferences preferences;
    private int type = 0;
    private String url;

    public IntranetDocumentAdapter(Context context, List<Document_Intranet.Intranet> data, int type) {
        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        this.context = context;
        this.values = data;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return values == null ? 0 : values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_send_document_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Document_Intranet.Intranet intranet = values.get(position);

        if (null == intranet.getFilenum() || intranet.getFilenum().equals("")) {
            holder.sendDocNo.setVisibility(View.GONE);
        } else {
            holder.sendDocNo.setVisibility(View.VISIBLE);
            holder.sendDocNo.setText("【" + intranet.getFilenum() + "】");
        }
        holder.sendDocTitle.setText(intranet.getTitle());
        holder.sendDocAddUser.setText(intranet.getSendername());
        holder.sendDocFaTime.setText(TimeUtils.getDateFromString(intranet.getAddtime(), "yyyy-MM-dd"));
        if (intranet.getGet() == 0) {
            holder.sendDocFlow.setText("签收");
            switch (type) {
                case 1:
                    url = ConstantURL.IN_DOC_LIST_SIGN;
                    break;
                case 2:
                    url = ConstantURL.IN_EMAIL_LIST_SIGN;
                    break;
            }
            holder.sendDocFlow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KLog.d("签收url------>", url);
                    KLog.d("文件ID-------->", intranet.getId());
                    String callURL = url + "&id=" + intranet.getId();
                    RequestCall mCall = OkHttpUtils.get()
                            .url(callURL)
                            .build();
                    mCall.readTimeOut(30 * 1000);
                    mCall.execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response) {
                            Log.e("内网文件签收", response);
                            Gson gson = new Gson();
                            Intranet_status status = gson.fromJson(response, Intranet_status.class);
                            if (null != status) {
                                if (status.getState().equals("ok") || status.getState().equals("OK")) {
                                    Toast.makeText(context, status.getMessage(), Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, IntranetDocumentDetails.class)
                                            .putExtra(ConstantString.ID, intranet.getId())
                                            .putExtra(ConstantString.STATUS, type)
                                            .putExtra(ConstantString.INTRANET, intranet));// 1 内网文件
                                } else {
                                    Toast.makeText(context, status.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "请求服务器失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        } else {
            holder.sendDocFlow.setText("已签收");
        }

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.send_doc_no) // 文号
                TextView sendDocNo;
        @Bind(R.id.send_doc_title) // 标题
                TextView sendDocTitle;
        @Bind(R.id.send_doc_addUser) // 发送者
                TextView sendDocAddUser;
        @Bind(R.id.send_doc_fa_time) // 时间
                TextView sendDocFaTime;
        @Bind(R.id.send_doc_flow) // 签收
                TextView sendDocFlow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
