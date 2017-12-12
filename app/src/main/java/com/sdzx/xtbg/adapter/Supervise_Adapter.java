package com.sdzx.xtbg.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Document;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.dialog.AlertDialog;
import com.sdzx.xtbg.dialog.Flow_Dialog;
import com.sdzx.xtbg.dialog.InputAlertDialog;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2016/3/18 0018
 * Tell：15006330640
 *
 *  督办督查Adapter
 */
public class Supervise_Adapter extends BaseAdapter {
    private Context context;
    private List<Document> documents;
    private List<Document> filterList;
    private SharedPreferences preferences;
    private boolean deadline = false;
    private boolean handling = false;
    public Supervise_Adapter(Context context, List<Document> documents, boolean deadline, boolean handling){
        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        this.context = context;
        this.documents = documents;
        this.deadline = deadline;
        this.handling = handling;
    }
    @Override
    public int getCount() {
        return documents == null ? 0 : documents.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_supervise,null);
            viewHolder = new ViewHolder();
            viewHolder.item_reference = BaseViewHolder.getViewHolder(convertView, R.id.item_reference);
            viewHolder.item_title = BaseViewHolder.getViewHolder(convertView,R.id.item_title);
            viewHolder.item_units = BaseViewHolder.getViewHolder(convertView,R.id.item_units);
            viewHolder.item_time = BaseViewHolder.getViewHolder(convertView,R.id.item_time);
            viewHolder.item_flow = BaseViewHolder.getViewHolder(convertView,R.id.item_flow);
            viewHolder.item_remaining = BaseViewHolder.getViewHolder(convertView,R.id.item_remaining);
            viewHolder.item_countdown = BaseViewHolder.getViewHolder(convertView,R.id.item_countdown);
            viewHolder.item_past = BaseViewHolder.getViewHolder(convertView,R.id.item_past);
            viewHolder.item_supervise = BaseViewHolder.getViewHolder(convertView,R.id.item_supervise);
            viewHolder.item_handling = BaseViewHolder.getViewHolder(convertView,R.id.item_handling);
            viewHolder.item_supervise_situation = BaseViewHolder.getViewHolder(convertView,R.id.item_supervise_situation);
            viewHolder.item_supervise_edit = BaseViewHolder.getViewHolder(convertView,R.id.item_supervise_edit);
            viewHolder.item_supervise_cancel = BaseViewHolder.getViewHolder(convertView,R.id.item_supervise_cancel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Document document = documents.get(position);
        if(document != null){
            // 文号
            if(document.getWenhao() != null && !document.getWenhao().equals("")){
                viewHolder.item_reference.setText("【"+document.getWenhao()+"】");
            }else {
                viewHolder.item_reference.setText("【文号】");
            }
            // 标题
            if(document.getTitle() != null && !document.getTitle().equals("")){
                viewHolder.item_title.setText(document.getTitle());
            }else {
                viewHolder.item_title.setText("收文标题");
            }
            // 单位
            if(document.getDanwei() != null && !document.getDanwei().equals("")){
                viewHolder.item_units.setText(document.getDanwei());
            }else {
                viewHolder.item_units.setText("单位");
            }
            // 时间
            if(document.getSwtime() != null && !document.getSwtime().equals("")){
                viewHolder.item_time.setText(document.getSwtime());
            }else {
                viewHolder.item_time.setText("刚刚");
            }
            // 正在办理按钮
            if(handling){
                viewHolder.item_supervise.setVisibility(View.GONE);
                viewHolder.item_handling.setVisibility(View.VISIBLE);
            }else {
                viewHolder.item_supervise.setVisibility(View.VISIBLE);
                viewHolder.item_handling.setVisibility(View.GONE);
            }
            // 截止时间
            if(deadline){
                if(document.getZhuangtai().contains("已过期")){
                    viewHolder.item_past.setVisibility(View.VISIBLE);
                    viewHolder.item_remaining.setVisibility(View.GONE);
                    viewHolder.item_supervise.setVisibility(View.GONE);
                }else {
                    viewHolder.item_past.setVisibility(View.GONE);
                    viewHolder.item_remaining.setVisibility(View.VISIBLE);
                    viewHolder.item_countdown.setText(Html.fromHtml(document.getZhuangtai()));
                    if(handling){
                        viewHolder.item_supervise.setVisibility(View.GONE);
                    }else
                    viewHolder.item_supervise.setVisibility(View.VISIBLE);
                }
            }else {
                viewHolder.item_remaining.setVisibility(View.GONE);
                viewHolder.item_past.setVisibility(View.GONE);
            }
//            // 进入督办系统
//            viewHolder.item_supervise.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    context.startActivity(new Intent(context, Supervise_Submit_Activity.class)
//                    .putExtra(ConstantString.ID,document.getId()));
//                }
//            });
            viewHolder.item_flow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Flow_Dialog(context).builder()
                            .readData(ConstantURL.DOCUMENT_BACKLOG_URL,"liucheng", preferences.getString(ConstantString.UID,""),document.getId())
                            .setCancelable(true)
                            .show();
                }
            });
            // 办理情况
            viewHolder.item_supervise_situation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new InputAlertDialog(context)
                            .builder()
                            .setTitle("办结情况")
                            .setCancelable(true)
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("提交", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
            });
//            // 编辑
//            viewHolder.item_supervise_edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    context.startActivity(new Intent(context, Supervise_Submit_Activity.class)
//                            .putExtra(ConstantString.ID,document.getId()));
//                }
//            });
            // 取消督办
            viewHolder.item_supervise_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog(context)
                            .builder()
                            .setCancelable(true)
                            .setTitle("取消督办")
                            .setMsg("您确定要取消督办吗？")
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                }
            });
        }
        return convertView;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                List<Document> FilterArrayList = new ArrayList<Document>();
                if (filterList == null) {
                    filterList = documents;
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < filterList.size(); i++) {

                        if (filterList.get(i).getTitle().toLowerCase()
                                .contains(constraint.toString())
                                ) {

                            FilterArrayList.add(filterList.get(i));

                        }
                    }
                    results.count = FilterArrayList.size();
                    results.values = FilterArrayList;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                try {

                    documents = (List<Document>) results.values;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        };

        return filter;
    }
    class ViewHolder{
        TextView item_reference; //文号
        TextView item_title; // 标题
        TextView item_units; // 单位
        TextView item_time; // 时间
        TextView item_flow; // 流程
        LinearLayout item_remaining; // 剩余时间
        TextView item_countdown;
        ImageView item_past; // 过期
        TextView item_supervise; // 进入督办系统
        LinearLayout item_handling; // 正在办理按钮
        TextView item_supervise_situation; // 办理情况
        TextView item_supervise_edit; // 编辑
        TextView item_supervise_cancel; // 取消办理
    }
}
