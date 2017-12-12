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
import com.sdzx.xtbg.bean.AttendanceListBean;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.dialog.Flow_Dialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 请假列表Adapter
 * Author:Eron
 * Date: 2016/5/26 0026
 * Time: 8:26
 */
public class Attendance_Leave_Apply_Adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private SharedPreferences preferences;
    private AttendanceListBean bean = new AttendanceListBean();
    private List<AttendanceListBean.Value> listBeen = new ArrayList<AttendanceListBean.Value>();

    public Attendance_Leave_Apply_Adapter(Context context, List<AttendanceListBean.Value> data) {
        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        this.mContext = context;
        this.listBeen = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBeen == null ? 0 : listBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance_approval, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.applyName.setText(listBeen.get(position).getUser());
        holder.applyDepartment.setText(listBeen.get(position).getDepart());
        holder.approvalReason.setText(listBeen.get(position).getTitle());
        holder.approvalTime.setText(listBeen.get(position).getStart()+"起至"
                +listBeen.get(position).getEnd()+"共计"+listBeen.get(position).getDays()+"天");
        holder.approvalFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,"流程地址！"+listBeen.get(position).getId(),Toast.LENGTH_SHORT).show();
                new Flow_Dialog(mContext).builder()
                        .readData(ConstantURL.ATTENDANCE_LEAVE_FLOW,"liucheng", preferences.getString(ConstantString.UID,""),listBeen.get(position).getId())
                        .setCancelable(true)
                        .show();
            }
        });
        holder.approvalEdit.setVisibility(View.GONE);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.apply_name)
        TextView applyName;
        @Bind(R.id.apply_department)
        TextView applyDepartment;
        @Bind(R.id.approval_reason)
        TextView approvalReason;
        @Bind(R.id.approval_time)
        TextView approvalTime;
        @Bind(R.id.approval_flow)
        TextView approvalFlow;
        @Bind(R.id.approval_edit)
        TextView approvalEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
