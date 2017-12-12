package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.RegisterList;
import com.sdzx.xtbg.tools.TimeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 签到列表Adapter
 * Author:Eron
 * Date: 2016/6/29 0029
 * Time: 17:22
 */
public class RegisterListAdapter extends BaseAdapter {

    private Context context;
    private List<RegisterList.Register_List_Bean> list_been;

    public RegisterListAdapter(Context context, List<RegisterList.Register_List_Bean> data) {
        this.context = context;
        this.list_been = data;
    }

    @Override
    public int getCount() {
        return list_been == null ? 0 : list_been.size();
    }

    @Override
    public Object getItem(int position) {
        return list_been.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_register_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemRegisterListName.setText("【" + list_been.get(position).getName() + "】");
        holder.itemRegisterListTime.setText("签到时间：" + TimeUtils.getDateToString3(Long.parseLong(list_been.get(position).getAddtime()) * 1000));
        holder.itemRegisterListAddress.setText(list_been.get(position).getSign_addr());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.item_register_List_name)
        TextView itemRegisterListName;// 用户名
        @Bind(R.id.item_register_List_time)
        TextView itemRegisterListTime;
        @Bind(R.id.item_register_List_address)
        TextView itemRegisterListAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
