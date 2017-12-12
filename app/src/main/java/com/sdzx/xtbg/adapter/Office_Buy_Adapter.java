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
public class Office_Buy_Adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private SharedPreferences preferences;
    private AttendanceListBean bean = new AttendanceListBean();
    private List<AttendanceListBean.Value> listBeen = new ArrayList<AttendanceListBean.Value>();

    public Office_Buy_Adapter(Context context, List<AttendanceListBean.Value> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_office_buy_approval, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.officeApplyName.setText("申请人：" + listBeen.get(position).getUser());
        holder.officeApplyDepartment.setText("科室：" + listBeen.get(position).getDepart());
        holder.officeApprovalReason.setText("物品：" + listBeen.get(position).getContent());
        holder.officeApprovalTime.setText(listBeen.get(position).getAddtime());
//        holder.officeApprovalFlow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(mContext,"流程地址！"+listBeen.get(position).getId(),Toast.LENGTH_SHORT).show();
//                new Flow_Dialog(mContext).builder()
//                        .readData(ConstantURL.ATTENDANCE_LEAVE_FLOW, "liucheng", preferences.getString(ConstantString.UID, ""), listBeen.get(position).getId())
//                        .setCancelable(true)
//                        .show();
//            }
//        });
        holder.officeApprovalFlow.setVisibility(View.GONE);
        holder.officeApprovalEdit.setVisibility(View.GONE);

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.office_apply_name)
        TextView officeApplyName;
        @Bind(R.id.office_apply_department)
        TextView officeApplyDepartment;
        @Bind(R.id.office_approval_reason)
        TextView officeApprovalReason;// 物品
        @Bind(R.id.office_approval_time)
        TextView officeApprovalTime;
        @Bind(R.id.office_approval_flow)
        TextView officeApprovalFlow;
        @Bind(R.id.office_approval_edit)
        TextView officeApprovalEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
