package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Logged;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/21 0021
 * Tell：15006330640
 */
public class Operation_Adapter extends BaseAdapter {
    private static final String TAG = "Operation_Adapter";
    private Context context;
    private List<Logged> loggeds;
    public Operation_Adapter(Context context, List<Logged> loggeds){
        this.context = context;
        this.loggeds = loggeds;
    }
    @Override
    public int getCount() {
        return loggeds == null ? 0 :loggeds.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_operation, null);
            viewHolder = new ViewHolder();
            viewHolder.item_time = BaseViewHolder.getViewHolder(convertView, R.id.item_time);
            viewHolder.item_ip = BaseViewHolder.getViewHolder(convertView,R.id.item_ip);
            viewHolder.item_content = BaseViewHolder.getViewHolder(convertView,R.id.item_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Logged logged = loggeds.get(position);
        try {
            if(logged != null){
                viewHolder.item_time.setText(logged.getAddtime());
                viewHolder.item_ip.setText("IP:"+logged.getIp());
                if(logged.getHandle() != null && !logged.getHandle().equals("")){

                    viewHolder.item_content.setText(Html.fromHtml("操作内容："+logged.getHandle()));
                }else
                    viewHolder.item_content.setText("操作内容：null");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }
    class ViewHolder{
        TextView item_time;
        TextView item_ip;
        TextView item_content;
    }
}
