package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Mail_Object;
import com.sdzx.xtbg.tools.BaseViewHolder;
import com.sdzx.xtbg.tools.TimeUtils;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 */
public class MailAdapter extends BaseAdapter {
    private static final String TAG = "MailAdapter";
    private Context context;
    private List<Mail_Object> mails;
    public MailAdapter(Context context, List<Mail_Object> mails){
        this.context = context;
        this.mails = mails;
    }
    @Override
    public int getCount() {
        return mails == null ? 0 : mails.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_received_mail,null);
            viewHolder = new ViewHolder();
            viewHolder.item_img = BaseViewHolder.getViewHolder(convertView, R.id.item_img);
            viewHolder.item_name = BaseViewHolder.getViewHolder(convertView,R.id.item_name);
            viewHolder.item_time = BaseViewHolder.getViewHolder(convertView,R.id.item_time);
            viewHolder.item_text = BaseViewHolder.getViewHolder(convertView,R.id.item_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Mail_Object mail_object = mails.get(position);
        if(null != mail_object.getTag() && !mail_object.getTag().equals("")){
            if(mail_object.getTag().equals("1")){
                viewHolder.item_img.setVisibility(View.GONE);
            }else {
                viewHolder.item_img.setVisibility(View.VISIBLE);
            }
        }
        // 标题
        if(mail_object.getTitle() != null && !mail_object.getTitle().equals("")){
            viewHolder.item_text.setText(mail_object.getTitle());
        }else {
            viewHolder.item_text.setText("收到的邮件");
        }
        // 收件人
        if(mail_object.getAdduser() != null && !mail_object.getAdduser().equals("")){
            viewHolder.item_name.setText(mail_object.getAdduser());
        }else {
            viewHolder.item_name.setText("收件人");
        }
        // 时间
        if(mail_object.getAddtime() != null && !mail_object.getAddtime().equals("")){
            viewHolder.item_time.setText(TimeUtils.getTimeForLong1(Long.parseLong(mail_object.getAddtime())*1000));
        }else {
            viewHolder.item_time.setText("刚刚");
        }
        return convertView;
    }
    class ViewHolder{
        ImageView item_img;
        TextView item_name;
        TextView item_time;
        TextView item_text;
    }
}
