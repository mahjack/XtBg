package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 来文机关
 * Author:Eron
 * Date: 2016/6/23 0023
 * Time: 11:56
 */
public class SpinnerDocComeDepartAdapter extends BaseAdapter {

    private Context context;
    private List<String> docComeDepart = new ArrayList<>();

    public SpinnerDocComeDepartAdapter(Context context, List<String> data) {
        this.context = context;
        this.docComeDepart = data;
    }

    @Override
    public int getCount() {
        return docComeDepart == null ? 0 : docComeDepart.size();
    }

    @Override
    public Object getItem(int position) {
        return docComeDepart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doc_come_type, null);
            holder = new ViewHolder();
            holder.item = (TextView) convertView.findViewById(R.id.doc_come_type_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item.setText(docComeDepart.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView item;
    }
}
