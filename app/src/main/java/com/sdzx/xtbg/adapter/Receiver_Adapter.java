package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.tools.BaseViewHolder;


/**
 * Author：Mark
 * Date：2016/1/5 0005
 * Tell：15006330640
 */
public class Receiver_Adapter extends BaseAdapter {
    private Context context;
    private String[] receivers;
    public Receiver_Adapter(Context context, String[] receivers){
        this.context = context;
        this.receivers = receivers;
    }
    @Override
    public int getCount() {
        return null == receivers ? 0 : receivers.length;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receiver,null);
            viewHolder = new ViewHolder();
            viewHolder.item_receiver = BaseViewHolder.getViewHolder(convertView,R.id.item_receiver);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String str = receivers[position];
        if(str.contains("未查阅")){
            viewHolder.item_receiver.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            viewHolder.item_receiver.setTextColor(context.getResources().getColor(R.color.green));
        }
        viewHolder.item_receiver.setText(str);
        return convertView;
    }
    class ViewHolder{
        TextView item_receiver;
    }
}
