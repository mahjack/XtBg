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
import com.sdzx.xtbg.bean.Lynn_Tag;
import com.sdzx.xtbg.tools.StringUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 考勤审核人Adapter
 * Author:Eron
 * Date: 2016/5/26 0026
 * Time: 17:14
 */
public class Attendance_Approver_Adapter4 extends BaseAdapter {

    private Context mContext;
    private Lynn_Tag approver = new Lynn_Tag();

    HashMap<String, Boolean> status = new HashMap<String, Boolean>();

    public Attendance_Approver_Adapter4(Context context, Lynn_Tag data) {
        this.mContext = context;
        this.approver = data;
    }

    @Override
    public int getCount() {
        return approver.getData().getUserid() == null ? 0 : approver.getData().getUserid().size();
    }

    @Override
    public Object getItem(int position) {
        return approver.getData().getUserid().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance_approver, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (approver.getData().getUserid() != null)
            holder.approveName.setText(approver.getData().getUserid().get(position).getRealname());
        else
            holder.approveName.setText(approver.getData().getUserid().get(position).getRealname());

        if (approver.getData().getUserid() != null) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 重置，确保最多只有一项被选中
                    for (String key : status.keySet()) {
                        status.put(key, false);
                    }
                    status.put(String.valueOf(position), holder.approveCheckbox.isChecked());
                    if (holder.approveCheckbox.isChecked()) {
                        StringUtils.approve_name = approver.getData().getUserid().get(position).getRealname();
                        StringUtils.approve_uid = approver.getData().getUserid().get(position).getId();
                        Log.e("tag", "考勤审批人名字------------------------------>" + StringUtils.approve_name);
                        Log.e("tag", "考勤审批人ID---------------------------->" + StringUtils.approve_uid);
                    } else {
                        StringUtils.approve_name = "";
                        StringUtils.approve_uid = "";
                    }
                    Attendance_Approver_Adapter4.this.notifyDataSetChanged();
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
                        StringUtils.approve_name = approver.getData().getUserid().get(position).getRealname();
                        StringUtils.approve_uid = approver.getData().getUserid().get(position).getId();
                        Log.e("tag", "考勤审批人名字------------------------------>" + StringUtils.approve_name);
                        Log.e("tag", "考勤审批人ID---------------------------->" + StringUtils.approve_uid);
                    } else {
                        StringUtils.approve_name = "";
                        StringUtils.approve_uid = "";
                    }
                    Attendance_Approver_Adapter4.this.notifyDataSetChanged();
                }
            });

            holder.approveCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Log.e("tag", "选中人的名字-------------------->" + approver.getData().getUserid().get(position).getRealname());
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
        } else {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 重置，确保最多只有一项被选中
                    for (String key : status.keySet()) {
                        status.put(key, false);
                    }
                    status.put(String.valueOf(position), holder.approveCheckbox.isChecked());
                    if (holder.approveCheckbox.isChecked()) {
                        StringUtils.approve_name = approver.getData().getUserid().get(position).getRealname();
                        StringUtils.approve_uid = approver.getData().getUserid().get(position).getId();
                        Log.e("tag", "考勤审批人名字------------------------------>" + StringUtils.approve_name);
                        Log.e("tag", "考勤审批人ID---------------------------->" + StringUtils.approve_uid);
                    } else {
                        StringUtils.approve_name = "";
                        StringUtils.approve_uid = "";
                    }
                    Attendance_Approver_Adapter4.this.notifyDataSetChanged();
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
                        StringUtils.approve_name = approver.getData().getUserid().get(position).getRealname();
                        StringUtils.approve_uid = approver.getData().getUserid().get(position).getId();
                        Log.e("tag", "考勤审批人名字------------------------------>" + StringUtils.approve_name);
                        Log.e("tag", "考勤审批人ID---------------------------->" + StringUtils.approve_uid);
                    } else {
                        StringUtils.approve_name = "";
                        StringUtils.approve_uid = "";
                    }
                    Attendance_Approver_Adapter4.this.notifyDataSetChanged();
                }
            });

            holder.approveCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Log.e("tag", "选中人的名字-------------------->" + approver.getData().getUserid().get(position).getRealname());
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
        }
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
