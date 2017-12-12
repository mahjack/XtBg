package com.sdzx.xtbg.bean;

/**
 * 是否可撤回实体类
 * Author:Eron
 * Date: 2016/7/11 0011
 * Time: 10:20
 */
public class JudgeRebut {
    /**
     * "state": "ok",
     * "tag": "0",
     * "step": "1"
     */

    private String state;
    private String tag;
    private String step;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
