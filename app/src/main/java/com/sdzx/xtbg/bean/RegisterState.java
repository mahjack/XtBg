package com.sdzx.xtbg.bean;

/**
 * 签到返回状态
 * Author:Eron
 * Date: 2016/6/24 0024
 * Time: 11:11
 */
public class RegisterState {
    private int state;
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
