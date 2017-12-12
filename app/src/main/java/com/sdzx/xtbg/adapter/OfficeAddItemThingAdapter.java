package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.OfficeItemThing;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 办公用品列表Adapter
 * Author:Eron
 * Date: 2016/8/2 0002
 * Time: 16:30
 */
public class OfficeAddItemThingAdapter extends BaseAdapter {

    private List<OfficeItemThing> itemThings = new ArrayList<>();
    private Context context;

    public OfficeAddItemThingAdapter(Context context, List<OfficeItemThing> data) {
        this.context = context;
        this.itemThings = data;
    }

    @Override
    public int getCount() {
        return itemThings == null ? 0 : itemThings.size();
    }

    @Override
    public Object getItem(int position) {
        return itemThings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_office_add_thing, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemOfficeAddThingName.setText(itemThings.get(position).getOfficeThingName());
        holder.itemOfficeAddThingSpecifica.setText(itemThings.get(position).getOfficeThingSpecifica());
        holder.itemOfficeAddThingNum.setText(itemThings.get(position).getOfficeThingNum());
        holder.itemOfficeAddThingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                itemThings.remove(position);
                notifyDataSetChanged();
                for (OfficeItemThing thing : itemThings) {
                    KLog.d("删除之后的数据------>", thing.getOfficeThingName());
                }

            }
        });
        return convertView;
    }


    static class ViewHolder {

        @Bind(R.id.item_office_add_thing_name)
        TextView itemOfficeAddThingName;
        @Bind(R.id.item_office_add_thing_specifica)
        TextView itemOfficeAddThingSpecifica;
        @Bind(R.id.item_office_add_thing_num)
        TextView itemOfficeAddThingNum;
        @Bind(R.id.item_office_add_thing_delete)
        ImageView itemOfficeAddThingDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
