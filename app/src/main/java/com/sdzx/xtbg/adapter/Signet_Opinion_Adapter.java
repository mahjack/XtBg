package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.SignetAudit;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.List;


/**
 * 公文审核流程
 * <p/>
 * Author：Mark
 * Date：2015/12/11 0011
 * Tell：15006330640
 */
public class Signet_Opinion_Adapter extends BaseAdapter {
    private Context context;
    private List<SignetAudit> audits;

    public Signet_Opinion_Adapter(Context context, List<SignetAudit> audits) {
        this.context = context;
        this.audits = audits;
    }

    @Override
    public int getCount() {
        return audits == null ? 0 : audits.size();
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SignetAudit value3 = audits.get(position);
        viewHolder.document_process_text.setText(value3.getContent());
        viewHolder.document_process_name.setText(value3.getAdduser());
        viewHolder.document_process_time.setText(value3.getAddtime());
        return convertView;
    }

    class ViewHolder {
        TextView document_process_text;
        TextView document_process_name;
        TextView document_process_time;
    }
}
