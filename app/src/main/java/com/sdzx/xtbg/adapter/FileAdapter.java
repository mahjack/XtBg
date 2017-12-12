package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdzx.xtbg.R;

import java.io.File;

public class FileAdapter extends BaseAdapter {
	private File[] files;
	private boolean istop;
	private Context context;
	public FileAdapter(Context context, File[] files, boolean istop) {
		this.context = context;
		this.files = files;
		this.istop = istop;
	}

	@Override
	public int getCount() {
		return files.length;
	}

	@Override
	public Object getItem(int position) {
		return files[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		Holder holder = null;
		if (view == null) {
			holder = new Holder();
			view = View.inflate(context, R.layout.item_file_new, null);
			holder.imageView = (ImageView) view.findViewById(R.id.file_icon);
			holder.textView = (TextView) view.findViewById(R.id.file_text);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		// 设置convertView中控件的值
		setconvertViewRow(position, holder);
		return view;
	}

	private void setconvertViewRow(int position, Holder holder) {
		File f = files[position];
		holder.textView.setText(f.getName());
		if (!istop && position == 0) {// 不是在顶层目录
			// 加载后退图标
			holder.imageView.setImageResource(R.mipmap.pic_file_back);
		} else if (f.isFile()) {// 是文件
			// 加载文件图标
			holder.imageView.setImageResource(R.mipmap.pic_file_file);
		} else {// 文件夹
			// 加载文件夹图标
			holder.imageView.setImageResource(R.mipmap.pic_file_dir);
		}
	}

	class Holder {
		private ImageView imageView;
		private TextView textView;
	}
}
