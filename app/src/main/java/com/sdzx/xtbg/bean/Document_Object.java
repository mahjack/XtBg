package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/9 0009
 * Tell：15006330640
 */
public class Document_Object {
    private static final String TAG = "Document_Object";
    private List<Document> value;
    private Number number;

    public List<Document> getValue() {
        return value;
    }

    public void setValue(List<Document> value) {
        this.value = value;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }
}
