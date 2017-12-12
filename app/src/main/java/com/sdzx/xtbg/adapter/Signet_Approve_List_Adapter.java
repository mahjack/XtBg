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
import com.sdzx.xtbg.bean.Signet;
import com.sdzx.xtbg.constant.ConstantString;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 印鉴审批列表Adapter
 * Author:Eron
 * Date: 2016/5/26 0026
 * Time: 8:26
 */
public class Signet_Approve_List_Adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private SharedPreferences preferences;
    private AttendanceListBean bean = new AttendanceListBean();
//    private List<SignetApproveList.SignetApprove> signetApproves = new ArrayList<>();
    private List<Signet.DataBean> signetApproves = new ArrayList<>();

    public Signet_Approve_List_Adapter(Context context, List<Signet.DataBean> data) {
        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        this.mContext = context;
        this.signetApproves = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return signetApproves == null ? 0 : signetApproves.size();
    }

    @Override
    public Object getItem(int position) {
        return signetApproves.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_signet_approval, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.signetApplyName.setText("申请人：" + signetApproves.get(position).getAdduser());
        holder.signetApplyDepartment.setText("发往单位：" + signetApproves.get(position).getFwdanwei());
        holder.signetApprovalReason.setText("事由：" + signetApproves.get(position).getTitle());
        holder.signetApprovalTime.setText("时间：" + signetApproves.get(position).getTime());
//        holder.approvalFlow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(mContext,"流程地址！"+listBeen.get(position).getId(),Toast.LENGTH_SHORT).show();
//                new Flow_Dialog(mContext).builder()
//                        .readData(ConstantURL.ATTENDANCE_FLOW, "liucheng", preferences.getString(ConstantString.UID, ""), listBeen.get(position).getId())
//                        .setCancelable(true)
//                        .show();
//            }
//        });
        holder.signetApprovalFlow.setVisibility(View.GONE);
        holder.signetApprovalEdit.setVisibility(View.GONE);

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.signet_apply_name)
        TextView signetApplyName;
        @Bind(R.id.signet_apply_department)
        TextView signetApplyDepartment;
        @Bind(R.id.signet_approval_reason)
        TextView signetApprovalReason;
        @Bind(R.id.signet_approval_time)
        TextView signetApprovalTime;
        @Bind(R.id.signet_approval_flow)
        TextView signetApprovalFlow;
        @Bind(R.id.signet_approval_edit)
        TextView signetApprovalEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
