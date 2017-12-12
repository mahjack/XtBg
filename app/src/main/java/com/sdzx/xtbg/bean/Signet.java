package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Joe
 * Date: 2017/6/26
 * Time: 17:34
 */

public class Signet {

    /**
     * data : [{"banjie":"0","id":"18","stamp":null,"time":"2017-06-26","title":"事由","adduser":"65","is_tui":"0","fwdanwei":"单位"},{"banjie":"0","id":"17","stamp":null,"time":"2017-06-26","title":"事由","adduser":"65","is_tui":"0","fwdanwei":"单位"}]
     * message :
     * code : 1
     */

    private String message;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * banjie : 0
         * id : 18
         * stamp : null
         * time : 2017-06-26
         * title : 事由
         * adduser : 65
         * is_tui : 0
         * fwdanwei : 单位
         */

        private String banjie;
        private String id;
        private Object stamp;
        private String time;
        private String title;
        private String adduser;
        private String is_tui;
        private String fwdanwei;

        public String getBanjie() {
            return banjie;
        }

        public void setBanjie(String banjie) {
            this.banjie = banjie;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getStamp() {
            return stamp;
        }

        public void setStamp(Object stamp) {
            this.stamp = stamp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getIs_tui() {
            return is_tui;
        }

        public void setIs_tui(String is_tui) {
            this.is_tui = is_tui;
        }

        public String getFwdanwei() {
            return fwdanwei;
        }

        public void setFwdanwei(String fwdanwei) {
            this.fwdanwei = fwdanwei;
        }
    }
}
