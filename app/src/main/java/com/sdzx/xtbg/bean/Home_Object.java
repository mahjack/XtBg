package com.sdzx.xtbg.bean;

/**
 * Author：Mark
 * Date：2016/3/17 0017
 * Tell：15006330640
 */
public class Home_Object {
    private int name;
    private boolean isVisible;
    private int resId;
    private int unRead;
    private String event;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
