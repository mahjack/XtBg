package com.sdzx.xtbg.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.FileAdapter;
import com.sdzx.xtbg.bean.FuJian;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

@SuppressLint("ValidFragment")
public class DialogFragment_file extends MyDialogFragment {

	// Ui
	@ViewInject(R.id.header_back)
    TextView ib_left;
	@ViewInject(R.id.header_right)
    ImageView ib_right;
	@ViewInject(R.id.header_title)
    TextView title;
	@ViewInject(R.id.currPath)
    TextView tv_path;
	@ViewInject(R.id.list)
    ListView list;
	// Data
	private String root = "/";// 顶层目录
	private File[] files;
	static FileSelected fileSelected;
	@ResInject(id = R.string.fileCannotRead, type = ResType.String)
	private String str_fileCannotRead;
	@ResInject(id = R.string.fileSelected, type = ResType.String)
	private String str_fileSelected;
	public DialogFragment_file(FileSelected fileSelected) {
		this.fileSelected = fileSelected;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_file, null);
		ViewUtils.inject(this, view);
		initTitle();
		getFiles(root);
		list.setOnItemClickListener(this);
		ib_left.setOnClickListener(this);
		ib_right.setOnClickListener(this);
		return view;
	}

	@Override
	public void initTitle() {
		title.setText(str_fileSelected);
		ib_right.setVisibility(View.GONE);
	}

	public void getFiles(String path) {
		tv_path.setText(path);
		File f = new File(path);
		// 得到所有子文件和文件夹
		File[] tem = f.listFiles();
		// 如果当前的目录不是在顶层目录，就把父目录要到files数组中的第一个
		if (!path.equals(root)) {
			files = new File[tem.length + 1];
			System.arraycopy(tem, 0, files, 1, tem.length);
			files[0] = f.getParentFile();
		} else {
			files = tem;
		}
		sortFilesByDirectory(files);
		// 为ListActivity设置Adapter
		list.setAdapter(new FileAdapter(context, files,
				files.length == tem.length));
	}

	// 对文件进行排序
	private void sortFilesByDirectory(File[] files) {
		Arrays.sort(files, new Comparator<File>() {

			public int compare(File f1, File f2) {
				return Long.valueOf(f1.length()).compareTo(f2.length());
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		Window window = getDialog().getWindow();
//		window.setLayout(myApplication.getScreenWidth() - 40,
//				myApplication.getScreenHeight() * 3 / 4);
		window.setGravity(Gravity.CENTER);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long arg3) {
		File file = files[position];
		if (!file.canRead()) {
			Toast.makeText(context, str_fileCannotRead, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		if (file.isFile()) {// 为文件
			FuJian fuJian = new FuJian();
			fuJian.setName(file.getName());
			fuJian.setPath(file.getAbsolutePath());
			fileSelected.onFileSelected(fuJian);
			dismiss();
		} else {
			getFiles(file.getAbsolutePath());
		}
	}

	public interface FileSelected {
		void onFileSelected(FuJian file);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId())
		{
		case R.id.header_back:
			dismiss();
			break;
		default:
			break;

		}

	}
}
