package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Depart;
import com.sdzx.xtbg.bean.Tel_Object;
import com.sdzx.xtbg.bean.User;
import com.sdzx.xtbg.dialog.Card_Dialog;
import com.sdzx.xtbg.tools.BaseViewHolder;
import com.sdzx.xtbg.tools.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2016/1/3 0003
 * Tell：15006330640
 */
public class Address_Depart_Adapter extends BaseAdapter {
    private Context context;
    private List<Depart> departs;
    private List<User> users;

    public Address_Depart_Adapter(Context context, List<Depart> departs, List<User> users) {
        this.context = context;
        this.departs = departs;
        this.users = users;
    }

    @Override
    public int getCount() {
        return departs == null ? 0 : departs.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_depart, null);
            viewHolder = new ViewHolder();
            viewHolder.depart_name = BaseViewHolder.getViewHolder(convertView, R.id.depart_name);
            viewHolder.user_list = BaseViewHolder.getViewHolder(convertView, R.id.user_list);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Depart depart = departs.get(position);
        viewHolder.depart_name.setText(depart.getName());
        final ArrayList<User> users1 = new ArrayList<User>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getDepart().equals(depart.getId())) {
                users1.add(users.get(i));
            }
        }
        Address_User_Adapter adapter = new Address_User_Adapter(context, users1);
        viewHolder.user_list.setAdapter(adapter);
        viewHolder.user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e("通讯录", "手机号：" + users1.get(position).getMobile() + "座机：" + users1.get(position).getTel() + "姓名：" + users1.get(position).getRealname());
                ArrayList<Tel_Object> tels = new ArrayList<Tel_Object>();
                if (!users1.get(position).getMobile().equals("")) {
                    Tel_Object object = new Tel_Object();
                    object.setTel(users1.get(position).getMobile());
                    object.setType("手机");
                    tels.add(object);
                }
                if (!users1.get(position).getTel().equals("")) {
                    for (String str : FileUtils.convertStrToArray(users1.get(position).getTel(), "/")) {
                        Tel_Object object = new Tel_Object();
                        object.setTel(str);
                        object.setType("座机");
                        tels.add(object);
                    }
                }
//                Intent intent = new Intent(context, ChattingActivity.class);
//                intent.putExtra(ChattingActivity.RECIPIENTS, "8008933500000007");
//                intent.putExtra(ChattingActivity.CONTACT_USER, depart.getName());
//                context.startActivity(intent);
//                Intent intent = new Intent(context, ContactDetailActivity.class);
//                intent.putExtra(ContactDetailActivity.MOBILE, users1.get(position).getMobile());
//                intent.putExtra(ContactDetailActivity.DISPLAY_NAME, depart.getName());
//                intent.putParcelableArrayListExtra(ContactDetailActivity.TEL_LIST, tels);
//                context.startActivity(intent);
                new Card_Dialog(context)
                        .builder()
                        .setCancelable(true)
                        .setName(users1.get(position).getRealname(), users1.get(position).getUsername())
                        .setList(tels)
                        .show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView depart_name;
        ListView user_list;
    }
}
