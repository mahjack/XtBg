package com.sdzx.xtbg.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Mark
 * Date：2016/1/19 0019
 * Tell：15006330640
 */
public class Tel_Object implements Parcelable {
    private String tel;
    private String type;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tel);
        dest.writeString(type);
    }
}
