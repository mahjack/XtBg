package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Document_Value3;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.List;

/**
 * 公文审核流程
 * <p>
 * Author：Mark
 * Date：2015/12/11 0011
 * Tell：15006330640
 */
public class Document_Process_Adapter extends BaseAdapter {
    private Context context;
    private List<Document_Value3> value;

    public Document_Process_Adapter(Context context, List<Document_Value3> value) {
        this.context = context;
        this.value = value;
    }

    @Override
    public int getCount() {
        return value == null ? 0 : value.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_document_process, null);
            viewHolder = new ViewHolder();
            viewHolder.document_process_text = BaseViewHolder.getViewHolder(convertView, R.id.document_process_text);
            viewHolder.document_process_name = BaseViewHolder.getViewHolder(convertView, R.id.document_process_name);
            viewHolder.document_process_time = BaseViewHolder.getViewHolder(convertView, R.id.document_process_time);
            viewHolder.document_process_iv = BaseViewHolder.getViewHolder(convertView, R.id.document_process_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Document_Value3 value3 = value.get(position);
        viewHolder.document_process_text.setText(value3.getContent());
        viewHolder.document_process_name.setText(value3.getAdduser());
        viewHolder.document_process_time.setText(value3.getAddtime());
//        Glide.with(context)
//                .load(value3.getFujian())
//                .into(viewHolder.document_process_iv);
        return convertView;
    }

    class ViewHolder {
        TextView document_process_text;
        TextView document_process_name;
        TextView document_process_time;
        ImageView document_process_iv;
    }
}
