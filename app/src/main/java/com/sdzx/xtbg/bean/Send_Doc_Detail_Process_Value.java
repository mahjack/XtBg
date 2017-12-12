package com.sdzx.xtbg.bean;

/**
 * 发文详情审批人Value类
 * Author:Eron
 * Date: 2016/6/29 0029
 * Time: 16:35
 */
public class Send_Doc_Detail_Process_Value {
    private String content;
    private String adduser;
    private String addtime;
    private String fujian;

    public String getFujian() {
        return fujian;
    }

    public void setFujian(String fujian) {
        this.fujian = fujian;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
