package com.sdzx.xtbg.bean;

/**
 * Author：Mark
 * Date：2015/12/11 0011
 * Tell：15006330640
 */
public class Person_User {
    private String id;
    private String realname;
    private String depart;
    private String disabled;

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    /**
     * 标识是否可以删除
     */
    private boolean canRemove = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }
}
