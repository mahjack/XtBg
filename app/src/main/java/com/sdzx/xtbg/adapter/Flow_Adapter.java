package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Flow_Object;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.ArrayList;

/**
 * Author：Mark
 * Date：2015/12/15 0015
 * Tell：15006330640
 * <p/>
 * 流程Adapter
 */
public class Flow_Adapter extends BaseAdapter {
    private static final String TAG = "Flow_Adapter";
    private Context context;
    private ArrayList<Flow_Object> objects;

    public Flow_Adapter(Context context, ArrayList<Flow_Object> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects == null ? 0 : objects.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_flow, null);
            viewHolder = new ViewHolder();
            viewHolder.item_flow_name = BaseViewHolder.getViewHolder(convertView, R.id.item_flow_name);
            viewHolder.item_flow_content = BaseViewHolder.getViewHolder(convertView, R.id.item_flow_content);
            viewHolder.item_flow_line1 = BaseViewHolder.getViewHolder(convertView, R.id.item_flow_line1);
            viewHolder.item_flow_line2 = BaseViewHolder.getViewHolder(convertView, R.id.item_flow_line2);
            viewHolder.item_flow_line3 = BaseViewHolder.getViewHolder(convertView, R.id.item_flow_line3);
            viewHolder.item_flow_point = BaseViewHolder.getViewHolder(convertView, R.id.item_flow_point);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Flow_Object object = objects.get(position);
        if (object != null) {
            if (position == 0) {
                // 最新
                viewHolder.item_flow_line1.setVisibility(View.GONE);
                viewHolder.item_flow_point.setBackground(context.getResources().getDrawable(R.drawable.shape_ovl));
            } else {
                viewHolder.item_flow_point.setBackground(context.getResources().getDrawable(R.drawable.shape_oavl_normal));
            }
            if (position == objects.size()) {
                // 第一个
                viewHolder.item_flow_line2.setVisibility(View.GONE);
                viewHolder.item_flow_line3.setVisibility(View.GONE);
            }
            viewHolder.item_flow_name.setText(object.getShoworder());
            String title = new String(object.getTitle());
            if (title.contains("等待")) {
                viewHolder.item_flow_content.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else if (title.contains("录入")) {
                viewHolder.item_flow_content.setTextColor(ContextCompat.getColor(context, R.color.green));
            } else {
                viewHolder.item_flow_content.setTextColor(ContextCompat.getColor(context, R.color.green));
            }

            if (title.contains("[")) {
                String title1[] = title.split("\\[");
                viewHolder.item_flow_content.setText(title1[0] + "\n[" + title1[1]);
            } else {
                viewHolder.item_flow_content.setText(title);
            }

        }
        return convertView;
    }

    class ViewHolder {
        TextView item_flow_name;
        TextView item_flow_content;
        View item_flow_line1; //
        View item_flow_line2;
        View item_flow_line3;
        View item_flow_point; // 圆点
    }
}
