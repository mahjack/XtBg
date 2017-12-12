package com.sdzx.xtbg.bean;

import java.io.Serializable;

/**
 * Author: Lynn
 * Date: 2016/11/5
 * 申请状态
 */

public class Lynn_ApplyStatus implements Serializable {

    /**
     * state : ok
     */

    private DataBean data;
    /**
     * data : {"state":"ok"}
     * message : 申请成功！
     * code : 1
     */

    private String message;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
