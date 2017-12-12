package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.InMail163List;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 163邮件列表Adapter
 * <p>
 * Author:Eron
 * Date: 2016/6/24 0024
 * Time: 0:24
 */
public class InMail163ListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    //    private SharedPreferences preferences;
    private List<InMail163List.Mail_163> mail163Lists;
    //    private int type = 0;
//    private String url;

    public InMail163ListAdapter(Context context, List<InMail163List.Mail_163> data) {
//        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        this.context = context;
        this.mail163Lists = data;
//        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mail163Lists == null ? 0 : mail163Lists.size();
    }

    @Override
    public Object getItem(int position) {
        return mail163Lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_163_mail, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mail163Time.setText(mail163Lists.get(position).getMailDate());
        holder.mail163Title.setText(mail163Lists.get(position).getTitle());
        holder.mail163Name.setText(mail163Lists.get(position).getFromName());
        holder.mail163Address.setText(mail163Lists.get(position).getFromBy());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.mail_163_time)
        TextView mail163Time; // 时间
        @Bind(R.id.mail_163_title)
        TextView mail163Title;
        @Bind(R.id.mail_163_name)
        TextView mail163Name; // 发件人名字
        @Bind(R.id.mail_163_address)
        TextView mail163Address; // 发件人地址

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
