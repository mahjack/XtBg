package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/3/18 0018
 * Tell：15006330640
 */
public class Public_Object <T> {
    private List<T> value;
    private Number number;

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }
}
