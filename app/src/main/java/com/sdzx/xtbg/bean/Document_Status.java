package com.sdzx.xtbg.bean;

/**
 * 文件办理状态
 * Author：Mark
 * Date：2015/12/10 0010
 * Tell：15006330640
 */
public class Document_Status {
    private static final String TAG = "Document_Status";
    //    "tag": "0",
//            "userid": "",
//            "totag": 2
    private String tag;
    private String userid;
    private String totag;
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTotag() {
        return totag;
    }

    public void setTotag(String totag) {
        this.totag = totag;
    }
}
