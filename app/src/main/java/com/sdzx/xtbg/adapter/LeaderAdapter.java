package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Person_User;
import com.sdzx.xtbg.tools.BaseViewHolder;
import com.sdzx.xtbg.tools.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Author：Mark
 * Date：2016/1/3 0003
 * Tell：15006330640
 *
 *  选择领导Adapter
 */
public class LeaderAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context context = null;

    /**
     * 数据集合
     */
    private List<Person_User> users = null;
    // 用于记录每个CheckBox的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    public LeaderAdapter(Context context, List<Person_User> users){
        this.context = context;
        this.users = users;
    }
    @Override
    public int getCount() {
        return users == null ? 0 : users.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.item_listView_ll = BaseViewHolder.getViewHolder(convertView,R.id.item_listView_ll);
            viewHolder.tvTitle = BaseViewHolder.getViewHolder(convertView,R.id.tvTitle);
            viewHolder.cbCheckBox = BaseViewHolder.getViewHolder(convertView,R.id.cbCheckBox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Person_User user = users.get(position);
        viewHolder.tvTitle.setText(user.getRealname());

        viewHolder.item_listView_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);

                }
                states.put(String.valueOf(position), viewHolder.cbCheckBox.isChecked());
                if(viewHolder.cbCheckBox.isChecked()){
                    StringUtils.name = user.getRealname();
                    StringUtils.user_id = user.getId();
                }else {
                    StringUtils.name = "";
                    StringUtils.user_id = "";
                }
                LeaderAdapter.this.notifyDataSetChanged();
            }
        });
        viewHolder.cbCheckBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);

                }
                states.put(String.valueOf(position), viewHolder.cbCheckBox.isChecked());
                if(viewHolder.cbCheckBox.isChecked()){
                    StringUtils.name = user.getRealname();
                    StringUtils.user_id = user.getId();
                }else {
                    StringUtils.name = "";
                    StringUtils.user_id = "";
                }
                LeaderAdapter.this.notifyDataSetChanged();
            }
        });
        viewHolder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.e("选中",user.getRealname());
                }
            }
        });
        boolean res = false;
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;

        viewHolder.cbCheckBox.setChecked(res);
        return convertView;
    }
    class ViewHolder{
        LinearLayout item_listView_ll;
        TextView tvTitle;
        CheckBox cbCheckBox;
    }
}
