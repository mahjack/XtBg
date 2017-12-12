package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Author:Eron
 * Date: 2015/12/18 0018
 * Time: 10:53
 */
public class Mail_Attachment_Adapter extends BaseAdapter {

    Context context;
    String[] name, path;

    public Mail_Attachment_Adapter(Context context, String[] name, String[] path) {
        this.context = context;
        this.name = name;
        this.path = path;
    }

    @Override
    public int getCount() {
        return 0;
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
        return null;
    }
}
