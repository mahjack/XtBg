package com.sdzx.xtbg.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Lynn
 * Date: 2016/11/4
 * 工作餐列表
 */

public class GongzuocanList implements Serializable {

    /**
     * code : 1
     * message :
     * data : [{"id":"1","jcrs":"6","total":"200","title":"加班整理会议发言","adduser":"4","banjie":"0","is_tui":"0"}]
     */

    private int code;
    private String message;
    /**
     * id : 1
     * jcrs : 6
     * total : 200
     * title : 加班整理会议发言
     * adduser : 4
     * banjie : 0
     * is_tui : 0
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private String id;
        private String jcrs;
        private String total;
        private String title;
        private String adduser;
        private String banjie;
        private String is_tui;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJcrs() {
            return jcrs;
        }

        public void setJcrs(String jcrs) {
            this.jcrs = jcrs;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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

        public String getBanjie() {
            return banjie;
        }

        public void setBanjie(String banjie) {
            this.banjie = banjie;
        }

        public String getIs_tui() {
            return is_tui;
        }

        public void setIs_tui(String is_tui) {
            this.is_tui = is_tui;
        }
    }
}
