package com.sdzx.xtbg.bean;

public interface LoadCallBack {

	public void onLoadSuccess(String obj);

	public void onLoadFail();

	public void onLoading(int progress);

}
