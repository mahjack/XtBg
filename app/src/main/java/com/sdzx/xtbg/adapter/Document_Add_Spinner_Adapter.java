package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Document_Add_Doc_Come;

import java.util.ArrayList;
import java.util.List;

/**
 * 公文添加 来文方式 Adapter
 * Author：Mark
 * Date：2016/6/22 0022
 * Tell：15006330640
 */
public class Document_Add_Spinner_Adapter extends BaseAdapter {
    private Context context;
    private List<Document_Add_Doc_Come> document_add_doc_comes = new ArrayList<>();

    public Document_Add_Spinner_Adapter(Context context, List<Document_Add_Doc_Come> data) {
        this.context = context;
        this.document_add_doc_comes = data;
    }

    @Override
    public int getCount() {
        return document_add_doc_comes == null ? 0 : document_add_doc_comes.size();
    }

    @Override
    public Object getItem(int position) {
        return document_add_doc_comes.get(position);
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
        holder.item.setText(document_add_doc_comes.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView item;
    }
}
