package com.sdzx.xtbg.bean;

/**
 * Author：Mark
 * Date：2015/12/8 0008
 * Tell：15006330640
 */
public class Login {
    private static final String TAG = "Login";
    private String state;
    private String uid;
    private String name;
    private String ddRole; // 1：具有多点分发的权限
    private String dcRole; // 1: 具有督查督办的权限

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDdRole() {
        return ddRole;
    }

    public void setDdRole(String ddRole) {
        this.ddRole = ddRole;
    }

    public String getDcRole() {
        return dcRole;
    }

    public void setDcRole(String dcRole) {
        this.dcRole = dcRole;
    }
}
