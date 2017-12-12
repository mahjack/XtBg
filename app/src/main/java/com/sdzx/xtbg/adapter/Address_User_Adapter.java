package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.User;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.ArrayList;

/**
 * Author：Mark
 * Date：2016/1/3 0003
 * Tell：15006330640
 */
public class Address_User_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> users;
    public Address_User_Adapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }
    @Override
    public int getCount() {
        return null == users ? 0 : users.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact,null);
            viewHolder = new ViewHolder();
            viewHolder.header = BaseViewHolder.getViewHolder(convertView,R.id.header);
            viewHolder.tv_name = BaseViewHolder.getViewHolder(convertView,R.id.tv_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = users.get(position);
        viewHolder.header.setVisibility(View.GONE);
        viewHolder.tv_name.setText(user.getRealname());
        return convertView;
    }
    class ViewHolder{
        TextView header;
        TextView tv_name;
    }
}
