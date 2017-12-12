package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 考勤实体类
 * Author:Eron
 * Date: 2016/5/26 0026
 * Time: 8:33
 */
public class AttendanceListBean {
    /**
     * {
     * "id": "12",
     * "title": "ceshi sdkf;lkasd;lfkl;sadf",
     * "start": "2016-05-26",
     * "end": "2016-05-27",
     * "days": "1",
     * "adduser": "17",
     * "banjie": "0",
     * "is_tui": "0",
     * "user": "崔常岭",
     * "depart": "办公室"
     * }
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

    /**
     * 办公用品采购审批
     * "id": "2",
     * "year": "2016",
     * "month": "7",
     * "day": "29",
     * "bianhao": "2016072901",
     * "sort": "1",
     * "user": "王晶",
     * "yusuan": "2000元",
     * "content": "\r\n办公室采购值班用的被褥",
     * "adduser": "6",
     * "addtime": "1469803422",
     * "banjie": "1",
     * "banjietime": "1469803464",
     * "bz": null
     */

    public class Value {
        private String id;
        private String title;
        private String start;
        private String end;
        private String days;
        private String adduser;
        private String banjie;
        private String is_tui;

        private String user;
        private String depart;
        private String addtime;
        private String content;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
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

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }
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


}
