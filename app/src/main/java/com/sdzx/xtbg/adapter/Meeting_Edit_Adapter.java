package com.sdzx.xtbg.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Meeting_Consult;
import com.sdzx.xtbg.constant.ConstantString;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2016/5/13 0013
 * Tell：15006330640
 */
public class Meeting_Edit_Adapter extends BaseAdapter {
    private Context context;
    private List<Meeting_Consult.Meeting> meetings;
    private SharedPreferences preferences;
    private int type;

    public Meeting_Edit_Adapter(Context context, List<Meeting_Consult.Meeting> meetings, int type) {
        this.context = context;
        this.meetings = meetings;
        this.type = type;
        preferences = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return meetings == null ? 0 : meetings.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_meeting_edit, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Meeting_Consult.Meeting meeting = meetings.get(position);
//        // 房间
//        if(null != meeting.getMeet()){
//            viewHolder.itemMeetingRoom.setText(meeting.getMeet());
//        }
        // 标题
        if(null != meeting.getTitle()){
            viewHolder.itemMeetingTitle.setText("会议名称："+meeting.getTitle());
        }
//        // 起始时间
        if(null != meeting.getTime() && !meeting.getTime().equals("")){
        viewHolder.itemMeetingStart.setText("会议时间："+meeting.getTime());
        }
//        // 截止时间
//        if(null != meeting.getEnd() && !meeting.getEnd().equals("")){
//            viewHolder.itemMeetingEnd.setText(meeting.getEnd());
//        }
        if(type == 1){
            viewHolder.itemEdit.setVisibility(View.GONE);
        }else {
            viewHolder.itemEdit.setVisibility(View.VISIBLE);
        }
//        // 流程
//        viewHolder.itemFlow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Flow_Dialog(context).builder()
//                        .readData(ConstantURL.MEETING_URL,"liucheng", preferences.getString(ConstantString.UID,""),meeting.getId())
//                        .setCancelable(true)
//                        .show();
//            }
//        });
//        // 编辑
//        viewHolder.itemEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, Meeting_Modify_Activity.class)
//                .putExtra(ConstantString.ID,meeting.getId()));
//            }
//        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_meeting_room)
        TextView itemMeetingRoom;
        @Bind(R.id.item_meeting_title)
        TextView itemMeetingTitle;
        @Bind(R.id.item_meeting_start)
        TextView itemMeetingStart;
        @Bind(R.id.item_meeting_end)
        TextView itemMeetingEnd;
        @Bind(R.id.item_meeting_office)
        TextView itemMeetingOffice;
        @Bind(R.id.item_flow)
        TextView itemFlow;
        @Bind(R.id.item_edit)
        TextView itemEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
