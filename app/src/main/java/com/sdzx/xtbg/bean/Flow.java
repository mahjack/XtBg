package com.sdzx.xtbg.bean;

import java.util.ArrayList;

/**
 * Author：Mark
 * Date：2015/12/16 0016
 * Tell：15006330640
 */
public class Flow {
    private static final String TAG = "Flow";
    private ArrayList<Flow_Object> info;
    private ArrayList<Flow_Object> data;

    public ArrayList<Flow_Object> getData() {
        return data;
    }

    public void setData(ArrayList<Flow_Object> data) {
        this.data = data;
    }

    public ArrayList<Flow_Object> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Flow_Object> info) {
        this.info = info;
    }
}
