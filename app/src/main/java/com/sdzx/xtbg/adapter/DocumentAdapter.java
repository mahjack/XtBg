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
import com.sdzx.xtbg.dialog.Flow_Dialog;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 公文列表Adapter
 * Author：Mark
 * Date：2015/12/9 0009
 * Tell：15006330640
 */
public class DocumentAdapter extends BaseAdapter {
    private static final String TAG = "DocumentAdapter";
    private Context context;
    private List<Document> documents;
    private List<Document> filterList;
    private SharedPreferences preferences;
    private boolean deadline = false;

    public DocumentAdapter(Context context, List<Document> documents, boolean deadline) {
        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        this.context = context;
        this.documents = documents;
        this.deadline = deadline;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_document, null);
            viewHolder = new ViewHolder();
            viewHolder.item_reference = BaseViewHolder.getViewHolder(convertView, R.id.item_reference);
            viewHolder.item_title = BaseViewHolder.getViewHolder(convertView, R.id.item_title);
            viewHolder.item_units = BaseViewHolder.getViewHolder(convertView, R.id.item_units);
            viewHolder.item_time = BaseViewHolder.getViewHolder(convertView, R.id.item_time);
            viewHolder.item_flow = BaseViewHolder.getViewHolder(convertView, R.id.item_flow);
//            viewHolder.item_rebut = BaseViewHolder.getViewHolder(convertView, R.id.item_rebut);
            viewHolder.item_remaining = BaseViewHolder.getViewHolder(convertView, R.id.item_remaining);
            viewHolder.item_countdown = BaseViewHolder.getViewHolder(convertView, R.id.item_countdown);
            viewHolder.item_past = BaseViewHolder.getViewHolder(convertView, R.id.item_past);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Document document = documents.get(position);
        if (document != null) {
            // 文号
            if (document.getWenhao() != null && !document.getWenhao().equals("")) {
                viewHolder.item_reference.setText("【" + document.getWenhao() + "】");
            } else {
                viewHolder.item_reference.setText("【文号】");
            }
            // 标题
            if (document.getTitle() != null && !document.getTitle().equals("")) {
                viewHolder.item_title.setText(document.getTitle());
            } else {
                viewHolder.item_title.setText("收文标题");
            }
            // 单位
            if (document.getDanwei() != null && !document.getDanwei().equals("")) {
                viewHolder.item_units.setText(document.getDanwei());
            } else {
                viewHolder.item_units.setText("单位");
            }
            // 时间
            if (document.getSwtime() != null && !document.getSwtime().equals("")) {
                viewHolder.item_time.setText(document.getSwtime());
            } else {
                viewHolder.item_time.setText("刚刚");
            }
            if (deadline) {
                if (document.getZhuangtai().contains("已过期")) {
                    viewHolder.item_past.setVisibility(View.VISIBLE);
                    viewHolder.item_remaining.setVisibility(View.GONE);
                } else {
                    viewHolder.item_past.setVisibility(View.GONE);
                    viewHolder.item_remaining.setVisibility(View.VISIBLE);
                    viewHolder.item_countdown.setText(Html.fromHtml(document.getZhuangtai()));
                }
            } else {
                viewHolder.item_remaining.setVisibility(View.GONE);
                viewHolder.item_past.setVisibility(View.GONE);
            }

            // 流程
            viewHolder.item_flow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,"流程",Toast.LENGTH_SHORT).show();
                    new Flow_Dialog(context).builder()
                            .readData(ConstantURL.DOCUMENT_BACKLOG_URL, "liucheng", preferences.getString(ConstantString.UID, ""), document.getId())
                            .setCancelable(true)
                            .show();
                }
            });

            // 驳回
//            viewHolder.item_rebut.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "驳回", Toast.LENGTH_SHORT).show();
//                }
//            });
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

    class ViewHolder {
        TextView item_reference; //文号
        TextView item_title; // 标题
        TextView item_units; // 单位
        TextView item_time; // 时间
        TextView item_flow; // 流程
        //        TextView item_rebut; // 驳回
        LinearLayout item_remaining; // 剩余时间
        TextView item_countdown;
        ImageView item_past; // 过期
    }
}
