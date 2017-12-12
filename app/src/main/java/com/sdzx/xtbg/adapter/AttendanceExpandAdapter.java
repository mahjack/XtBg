package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdzx.xtbg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 考勤接收人ExpandListView
 * Author:Eron
 * Date: 2016/5/25 0025
 * Time: 11:31
 */
public class AttendanceExpandAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> parentList = new ArrayList<String>();
    private List<List<String>> childList = new ArrayList<List<String>>();

    public AttendanceExpandAdapter(Context context, List<String> parent, List<List<String>> child) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.parentList = parent;
        this.childList = child;
    }

    @Override
    public int getGroupCount() {
        return parentList == null ? 0 : parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition) == null ? 0 : childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance_parent_approve, parent, false);
            parentHolder = new ParentViewHolder();
            parentHolder.expandImg = (ImageView) convertView.findViewById(R.id.parent_expand_Image);
            parentHolder.parentName = (TextView) convertView.findViewById(R.id.parent_name);
            parentHolder.childCount = (TextView) convertView.findViewById(R.id.child_count);
            convertView.setTag(parentHolder);
        } else {
            parentHolder = (ParentViewHolder) convertView.getTag();
        }
        if (!isExpanded) {
            parentHolder.expandImg.setBackgroundResource(R.mipmap.ic_expand_img);
        } else {
            parentHolder.expandImg.setBackgroundResource(R.mipmap.ic_expand_down);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance_child_approve, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.childName = (TextView) convertView.findViewById(R.id.tv_approve_name);
            childHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box_approve);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ParentViewHolder {
        ImageView expandImg;
        TextView parentName, childCount;
    }

    class ChildViewHolder {
        TextView childName;
        CheckBox checkBox;
    }

}
