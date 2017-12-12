package com.sdzx.xtbg.dialog;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;


public class MyDialogFragment extends DialogFragment implements
        OnClickListener, OnItemClickListener, OnDateSetListener {
	// App
	public Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		context = getActivity().getApplicationContext();


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		initTitle();
		initView();
		initData();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 初始化标题栏
	 */
	public void initTitle() {

	}

	/**
	 * 初始化控件
	 */
	public void initView() {

	}

	/**
	 * 初始化数据
	 */
	public void initData() {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	@Override
	public void onClick(View arg0) {

	}

	@Override
	public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

}
