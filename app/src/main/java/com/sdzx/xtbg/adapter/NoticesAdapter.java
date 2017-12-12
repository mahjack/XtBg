package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Ann_Object;
import com.sdzx.xtbg.tools.BaseViewHolder;
import com.sdzx.xtbg.tools.TimeUtils;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 */
public class NoticesAdapter extends BaseAdapter {
    private static final String TAG = "NoticesAdapter";
    private Context context;
    private List<Ann_Object> objects;
    public NoticesAdapter(Context context, List<Ann_Object> objects){
        this.context = context;
        this.objects = objects;
    }
    @Override
    public int getCount() {
        return objects == null ? 0 : objects.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notices,null);
            viewHolder = new ViewHolder();
            viewHolder.notices_title = BaseViewHolder.getViewHolder(convertView, R.id.notices_title);
            viewHolder.notices_time = BaseViewHolder.getViewHolder(convertView,R.id.notices_time);
            viewHolder.notices_text = BaseViewHolder.getViewHolder(convertView,R.id.notices_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Ann_Object ann_object = objects.get(position);
        if(ann_object != null){
            if(ann_object.getTitle() != null && !ann_object.getTitle().equals("")){
                viewHolder.notices_title.setText(ann_object.getTitle());
            }else {
                viewHolder.notices_title.setText("综合必读标题");
            }
//            if(ann_object.getContent() != null && !ann_object.getContent().equals("")){
//                viewHolder.notices_text.setText(Html.fromHtml(ann_object.getContent()));
//            }else {
//                viewHolder.notices_text.setText("综合必读内容");
//            }
            try{
                if(ann_object.getAddtime() != null && !ann_object.getAddtime().equals("")){
                    viewHolder.notices_time.setText(TimeUtils.getDateToString(Long.parseLong(ann_object.getAddtime())*1000));
                }else {
                    viewHolder.notices_time.setText("刚刚");
                }
            }
            catch (Exception e){

            }


        }
        return convertView;
    }
    class ViewHolder{
        TextView notices_title;
        TextView notices_time;
        TextView notices_text;
    }
}
