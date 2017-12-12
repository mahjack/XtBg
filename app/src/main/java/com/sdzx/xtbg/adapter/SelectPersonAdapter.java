package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Depart;
import com.sdzx.xtbg.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectPersonAdapter extends BaseExpandableListAdapter {

	Context context;

	private LayoutInflater inflater = null;

	private List<Depart> groupArray;

	private ArrayList<ArrayList<User>> childArray;

	private ArrayList<Map<String, Boolean>> groupCheckBox;

	private ArrayList<ArrayList<Map<String, Boolean>>> childCheckBox;

	private final String G_CB = "G_CB";

	private final String C_CB = "C_CB";

	private boolean isAll;

	ViewHolder holder = null;

	public SelectPersonAdapter(Context context,
							   List<Depart> groupArray,
							   ArrayList<ArrayList<User>> childArray,
							   ArrayList<Map<String, Boolean>> groupCheckBox,
							   ArrayList<ArrayList<Map<String, Boolean>>> childCheckBox,
							   boolean isAll) {
		this.context = context;
		this.groupArray = groupArray;
		this.childArray = childArray;
		this.groupCheckBox = groupCheckBox;
		this.childCheckBox = childCheckBox;
		this.isAll = isAll;
	}

	@Override
	public int getGroupCount() {
		if (isAll) {
			return groupArray.size();
		} else {
			return 1;
		}
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childArray.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupArray.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childArray.get(groupPosition).get(childPosition);
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
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_selectperson_group, null);
		}
		TextView groupText = (TextView) view.findViewById(R.id.groupText);
		final CheckBox gCheckBox = (CheckBox) view
				.findViewById(R.id.groupCheckBox);
		groupText.setText(groupArray.get(groupPosition).getName());
		gCheckBox.setChecked(groupCheckBox.get(groupPosition).get(G_CB));
		gCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Boolean isChecked = gCheckBox.isChecked();
				groupCheckBox.get(groupPosition).put(G_CB, isChecked);
				changChildStates(groupPosition, isChecked);
				notifyDataSetChanged();
			}

		});
		return view;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {
		ViewHolder holder = null;
		inflater = LayoutInflater.from(context);
		if (view == null) {
			view = inflater.inflate(R.layout.item_selectperson_child, null);
			holder = new ViewHolder();
			holder.tv_child = (TextView) view.findViewById(R.id.childText);
			holder.ck_child = (CheckBox) view.findViewById(R.id.childCheckBox);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_child.setText(childArray.get(groupPosition)
				.get(childPosition).getRealname());
		holder.ck_child.setChecked(childCheckBox.get(groupPosition)
				.get(childPosition).get(C_CB));
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	private void changChildStates(int groupPosition, Boolean isChecked) {
		for (int i = 0; i < childCheckBox.get(groupPosition).size(); i++) {
			childCheckBox.get(groupPosition).get(i).put(C_CB, isChecked);
		}
	}

	/**
	 * 获取发送人
	 *
	 * @return
	 */
	public String getReceivePerson() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < childArray.size(); i++) {
			for (int j = 0; j < childArray.get(i).size(); j++) {
				if (childCheckBox.get(i).get(j).get(C_CB)) {
					sb.append(childArray.get(i).get(j).getId()+"|");
				}
			}
		}
//		sb.append("|");
		return sb.toString();
	}

	/**
	 * 获取收件人名
	 *
	 * @return
	 */
	public String getReceivePersonName() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < childArray.size(); i++) {
			for (int j = 0; j < childArray.get(i).size(); j++) {
				if (childCheckBox.get(i).get(j).get(C_CB)) {
					sb.append( childArray.get(i).get(j).getRealname()+",");
				}
			}
		}
		return sb.toString();
	}

	public ArrayList<Map<String, Boolean>> getGroupBoxs() {
		return groupCheckBox;
	}

	public ArrayList<ArrayList<Map<String, Boolean>>> getChildBoxs() {
		return childCheckBox;
	}

	class ViewHolder {
		TextView tv_child;
		CheckBox ck_child;
	}
}
