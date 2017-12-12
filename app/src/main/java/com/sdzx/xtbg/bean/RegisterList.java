package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 签到列表实体类
 * Author:Eron
 * Date: 2016/6/29 0029
 * Time: 17:15
 */
public class RegisterList {
    /**
     * "id": "2",
     * "uid": "3",
     * "coord": "35.460573、119.54153",
     * "year": "2016",
     * "month": "6",
     * "date": "2016-06-25",
     * "addtime": "1466845022",
     * "sign_time": "1970-01-01",
     * "sign_addr": "山东省日照市东港区至爱路靠近日照职业技术学院图书楼"
     */

    private List<Register_List_Bean> info;

    public List<Register_List_Bean> getInfo() {
        return info;
    }

    public void setInfo(List<Register_List_Bean> info) {
        this.info = info;
    }

    public class Register_List_Bean {
        private String id;
        private String uid;
        private String coord;
        private String year;
        private String month;
        private String date;
        private String addtime;
        private String sign_time;
        private String sign_addr;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCoord() {
            return coord;
        }

        public void setCoord(String coord) {
            this.coord = coord;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getSign_addr() {
            return sign_addr;
        }

        public void setSign_addr(String sign_addr) {
            this.sign_addr = sign_addr;
        }
    }

}
