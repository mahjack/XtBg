package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.SendDocReceiver;
import com.sdzx.xtbg.tools.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发文审核人Adapter
 * Author:Eron
 * Date: 2016/5/26 0026
 * Time: 17:14
 */
public class SendDocReceiverAdapter extends BaseAdapter {

    private LayoutInflater mIayoutInflater;
    private Context mContext;
    private List<SendDocReceiver.ReceiverUser> userList = new ArrayList<SendDocReceiver.ReceiverUser>();

    HashMap<String, Boolean> status = new HashMap<String, Boolean>();

    public SendDocReceiverAdapter(Context context, List<SendDocReceiver.ReceiverUser> data) {
        this.mContext = context;
        this.userList = data;
        mIayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userList == null ? 0 : userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SendDocReceiver.ReceiverUser userObject = userList.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance_approver, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.approveName.setText(userList.get(position).getRealname());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一向被选中
                for (String key : status.keySet()) {
                    status.put(key, false);
                }
                status.put(String.valueOf(position), holder.approveCheckbox.isChecked());
                if (holder.approveCheckbox.isChecked()) {
                    StringUtils.approve_name = userList.get(position).getRealname();
                    StringUtils.approve_uid = userList.get(position).getId();
                    Log.e("tag", "考勤审批人名字------------------------------>" + StringUtils.approve_name);
                    Log.e("tag", "考勤审批人ID---------------------------->" + StringUtils.approve_uid);
                } else {
                    StringUtils.approve_name = "";
                    StringUtils.approve_uid = "";
                }
                SendDocReceiverAdapter.this.notifyDataSetChanged();
            }
        });

        holder.approveCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一向被选中
                for (String key : status.keySet()) {
                    status.put(key, false);
                }
                status.put(String.valueOf(position), holder.approveCheckbox.isChecked());
                if (holder.approveCheckbox.isChecked()) {
                    StringUtils.approve_name = userList.get(position).getRealname();
                    StringUtils.approve_uid = userList.get(position).getId();
                    Log.e("tag", "考勤审批人名字------------------------------>" + StringUtils.approve_name);
                    Log.e("tag", "考勤审批人ID---------------------------->" + StringUtils.approve_uid);
                } else {
                    StringUtils.approve_name = "";
                    StringUtils.approve_uid = "";
                }
                SendDocReceiverAdapter.this.notifyDataSetChanged();
            }
        });

        holder.approveCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e("tag", "选中人的名字-------------------->" + userList.get(position).getRealname());
                }
            }
        });

        boolean result = false;
        if (status.get(String.valueOf(position)) == null || status.get(String.valueOf(position)) == false) {
            result = false;
            status.put(String.valueOf(position), false);
        } else {
            result = true;
        }
        holder.approveCheckbox.setChecked(result);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.linearLayout)
        LinearLayout linearLayout;
        @Bind(R.id.approve_name)
        TextView approveName;
        @Bind(R.id.approve_checkbox)
        CheckBox approveCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
