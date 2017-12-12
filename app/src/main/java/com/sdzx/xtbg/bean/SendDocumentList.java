package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 发文列表实体类
 * Author:Eron
 * Date: 2016/6/24 0024
 * Time: 0:02
 */
public class SendDocumentList {

    /**
     * "id": "2",
     * "type": null,
     * "fwtime": "2016年06月23日",
     * "bianhao": "23123132",
     * "title": "士大夫士大夫",
     * "adduser": "管理员"
     */

    private List<Value> value;
    private Number number;

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public class Number {
        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }


    public class Value {
        private String id;
        private String type;
        private String fwtime;
        private String bianhao;
        private String title;
        private String adduser;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFwtime() {
            return fwtime;
        }

        public void setFwtime(String fwtime) {
            this.fwtime = fwtime;
        }

        public String getBianhao() {
            return bianhao;
        }

        public void setBianhao(String bianhao) {
            this.bianhao = bianhao;
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
    }

}
