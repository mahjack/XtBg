package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.MeetingRoom;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/4/21 0021
 * Tell：15006330640
 */
public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<MeetingRoom.Room> rooms;

    public SpinnerAdapter(Context context, List<MeetingRoom.Room> infos) {
        this.context = context;
        this.rooms = infos;
    }

    @Override
    public int getCount() {
        return rooms == null ? 0 : rooms.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, null);
            viewHolder = new ViewHolder();
            viewHolder.itemSpinnerName = BaseViewHolder.getViewHolder(convertView,R.id.item_spinner_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MeetingRoom.Room room = rooms.get(position);
        Log.e("显示社区",room.getName());
        viewHolder.itemSpinnerName.setText(room.getName());
        return convertView;
    }

    class ViewHolder {
        TextView itemSpinnerName;
    }
}
