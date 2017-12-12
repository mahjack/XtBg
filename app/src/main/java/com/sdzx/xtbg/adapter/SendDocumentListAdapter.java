package com.sdzx.xtbg.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.SendDocumentList;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.dialog.Flow_Dialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发文列表Adapter
 * <p/>
 * Author:Eron
 * Date: 2016/6/24 0024
 * Time: 0:24
 */
public class SendDocumentListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<SendDocumentList.Value> values = new ArrayList<>();
    private SharedPreferences preferences;

    public SendDocumentListAdapter(Context context, List<SendDocumentList.Value> data) {
        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        this.context = context;
        this.values = data;
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

        holder.sendDocNo.setText("【" + values.get(position).getBianhao() + "】");
        holder.sendDocTitle.setText(values.get(position).getTitle());
        holder.sendDocAddUser.setText(values.get(position).getAdduser());
        holder.sendDocFaTime.setText(values.get(position).getFwtime());
        holder.sendDocFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "流程", Toast.LENGTH_SHORT).show();
                new Flow_Dialog(context).builder()
                        .readData(ConstantURL.SEND_URL,"liucheng", preferences.getString(ConstantString.UID,""),values.get(position).getId())
                        .setCancelable(true)
                        .show();
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.send_doc_no)
        TextView sendDocNo;
        @Bind(R.id.send_doc_title)
        TextView sendDocTitle;
        @Bind(R.id.send_doc_addUser)
        TextView sendDocAddUser;
        @Bind(R.id.send_doc_fa_time)
        TextView sendDocFaTime;
        @Bind(R.id.send_doc_flow)
        TextView sendDocFlow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
