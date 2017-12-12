package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 出差列表实体类
 * <p/>
 * Author:Eron
 * Date: 2016/6/2 0002
 * Time: 8:22
 */
public class AttendanceBusiness {
    /**
     * {
     * "id": "15",
     * "year": "2016",
     * "month": "6",
     * "sort": "1",
     * "user": "崔常岭",
     * "title": "测试出差流程-------------------------1",
     * "addr": "北京",
     * "is_pai": "0",
     * "jtgj": "飞机",
     * "start": "2016-06-01",
     * "end": "2016-06-08",
     * "day": "8",
     * "caruser": null,
     * "print": "0",
     * "adduser": "17",
     * "addtime": "1464761799",
     * "banjie": "0",
     * "banjietime": null,
     * "is_tui": "0"
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

    public class Value {
        private String id;
        private String year;
        private String month;
        private String sort;
        private String user;
        private String title;
        private String addr;
        private String is_pai;
        private String jtgj;
        private String start;
        private String end;
        private String day;
        private String caruser;
        private String print;
        private String adduser;
        private String addtime;
        private String banjie;
        private String banjietime;
        private String is_tui;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getIs_pai() {
            return is_pai;
        }

        public void setIs_pai(String is_pai) {
            this.is_pai = is_pai;
        }

        public String getJtgj() {
            return jtgj;
        }

        public void setJtgj(String jtgj) {
            this.jtgj = jtgj;
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

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getCaruser() {
            return caruser;
        }

        public void setCaruser(String caruser) {
            this.caruser = caruser;
        }

        public String getPrint() {
            return print;
        }

        public void setPrint(String print) {
            this.print = print;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getBanjie() {
            return banjie;
        }

        public void setBanjie(String banjie) {
            this.banjie = banjie;
        }

        public String getBanjietime() {
            return banjietime;
        }

        public void setBanjietime(String banjietime) {
            this.banjietime = banjietime;
        }

        public String getIs_tui() {
            return is_tui;
        }

        public void setIs_tui(String is_tui) {
            this.is_tui = is_tui;
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
