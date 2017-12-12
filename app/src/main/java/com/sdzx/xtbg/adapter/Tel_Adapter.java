package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Tel_Object;
import com.sdzx.xtbg.tools.BaseViewHolder;

import java.util.ArrayList;

/**
 * Author：Mark
 * Date：2016/1/19 0019
 * Tell：15006330640
 */
public class Tel_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Tel_Object> tels;
    public Tel_Adapter(Context context, ArrayList<Tel_Object> tels){
        this.context = context;
        this.tels = tels;
    }
    @Override
    public int getCount() {
        return tels == null ? 0 : tels.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tel,null);
            viewHolder = new ViewHolder();
            viewHolder.item_rl = BaseViewHolder.getViewHolder(convertView,R.id.item_rl);
            viewHolder.item_tel = BaseViewHolder.getViewHolder(convertView,R.id.item_tel);
            viewHolder.item_tel_type = BaseViewHolder.getViewHolder(convertView,R.id.item_tel_type);
            viewHolder.item_message_btn = BaseViewHolder.getViewHolder(convertView,R.id.item_message_btn);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Tel_Object object = tels.get(position);
        viewHolder.item_tel.setText(object.getTel());
        viewHolder.item_tel_type.setText(object.getType());
        if(object.getType().equals("手机")){
            viewHolder.item_message_btn.setVisibility(View.VISIBLE);
        }else {
            viewHolder.item_message_btn.setVisibility(View.GONE);
        }
        viewHolder.item_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"发送短信", Toast.LENGTH_SHORT).show();
                Uri smsToUri = Uri.parse("smsto:" + object.getTel());
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
//                intent.putExtra("sms_body", smsBody);\
                context.startActivity(intent);
            }
        });
        viewHolder.item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"拨打电话", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                //需要拨打的号码
                intent.setData(Uri.parse("tel:" + object.getTel()));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
    class ViewHolder{
        RelativeLayout item_rl;
        TextView item_tel;
        TextView item_tel_type;
        TextView item_message_btn;
    }
}
