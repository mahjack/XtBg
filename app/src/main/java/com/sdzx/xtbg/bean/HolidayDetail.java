package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 休假详情实体类
 * Author:Eron
 * Date: 2016/5/28 0028
 * Time: 15:59
 */
public class HolidayDetail {
    /**
     * {
     * "value": [
     * {
     * "id": "24",
     * "year": "2016",
     * "month": "5",
     * "sort": "18",
     * "title": "1234",
     * "start": "2016-05-28",
     * "end": "2016-05-28",
     * "days": "1",
     * "xiaojia": "",
     * "adduser": "16",
     * "addtime": "2016-05-28 15:32:47",
     * "banjie": "0",
     * "banjietime": null,
     * "is_tui": "0",
     * "user": "孙斌"
     * }
     * ],
     * "value1": [],
     * "value2": [
     * {
     * "content": "jkhkjdsfa",
     * "adduser": "孔辉",
     * "addtime": "2016-05-28 15:35:11"
     * }
     * ],
     * "value3": [
     * {
     * "content": "1234",
     * "adduser": "王雷",
     * "addtime": "2016-05-28 15:33:35"
     * }
     * ],
     * "value4": [
     * {
     * "content": "1234",
     * "adduser": "高月波",
     * "addtime": "2016-05-28 15:36:09"
     * }
     * ]
     * }
     */

    private List<Value> value;
    private List<Value1> value1;
    private List<Value2> value2;
    private List<Value3> value3;
    private List<Value4> value4;

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public List<Value1> getValue1() {
        return value1;
    }

    public void setValue1(List<Value1> value1) {
        this.value1 = value1;
    }

    public List<Value2> getValue2() {
        return value2;
    }

    public void setValue2(List<Value2> value2) {
        this.value2 = value2;
    }

    public List<Value3> getValue3() {
        return value3;
    }

    public void setValue3(List<Value3> value3) {
        this.value3 = value3;
    }

    public List<Value4> getValue4() {
        return value4;
    }

    public void setValue4(List<Value4> value4) {
        this.value4 = value4;
    }

    public class Value {
        /**
         * "id": "24",
         * "year": "2016",
         * "month": "5",
         * "sort": "18",
         * "title": "1234",
         * "start": "2016-05-28",
         * "end": "2016-05-28",
         * "days": "1",
         * "xiaojia": "",
         * "adduser": "16",
         * "addtime": "2016-05-28 15:32:47",
         * "banjie": "0",
         * "banjietime": null,
         * "is_tui": "0",
         * "user": "孙斌"
         */

        /**
         * 出差
         * {
         * "id": "25",
         * "year": "2016",
         * "month": "6",
         * "sort": "11",
         * "user": "崔常岭",
         * "title": "测试出差理由",
         * "addr": "上海",
         * "is_pai": "0",
         * "jtgj": "皮卡",
         * "start": "2016-06-02",
         * "end": "2016-06-03",
         * "day": "1",
         * "caruser": null,
         * "print": "0",
         * "adduser": "17",
         * "addtime": "2016-06-02 10:55:20",
         * "banjie": "0",
         * "banjietime": null,
         * "is_tui": "0",
         * "xiaojia": ""
         * }
         */

        /**
         * "id": "2",
         * "year": "2016",
         * "month": "7",
         * "sort": "2",
         * "time": "2016-07-25",
         * "danwei": "办公室",
         * "title": "文件",
         * "type": "1",
         * "item": "1",
         * "adduser": "王晶",
         * "addtime": "1469452573",
         * "banjie": "1",
         * "banjietime": "1469453053",
         * "typename": "法人章0",
         * "itemname": "行政0"
         */

        private String id;
        private String year;
        private String month;
        private String sort;
        private String title;
        private String start;
        private String end;
        private String days;
        private String xiaojia;
        private String adduser;
        private String addtime;
        private String banjie;
        private String banjietime;
        private String is_tui;
        private String user;

        private String addr;
        private String is_pai;
        private String jtgj;
        private String day;//出差天数

        private String time;
        private String danwei;// 用印单位
        private String typename;// 印鉴类型
        private String itemname;// 用印分类
        private String cuser;
        private String usernum;

        public String getCuser() {
            return cuser;
        }

        public void setCuser(String cuser) {
            this.cuser = cuser;
        }

        public String getUsernum() {
            return usernum;
        }

        public void setUsernum(String usernum) {
            this.usernum = usernum;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDanwei() {
            return danwei;
        }

        public void setDanwei(String danwei) {
            this.danwei = danwei;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
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

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

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

        public String getXiaojia() {
            return xiaojia;
        }

        public void setXiaojia(String xiaojia) {
            this.xiaojia = xiaojia;
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

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    public class Value1 {
        /**
         * [
         * {
         * "content": "jkhkjdsfa",
         * "adduser": "孔辉",
         * "addtime": "2016-05-28 15:35:11"
         * }
         * ]
         */
        private String content;
        private String adduser;
        private String addtime;
        private String fujian;

        public String getFujian() {
            return fujian;
        }

        public void setFujian(String fujian) {
            this.fujian = fujian;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }

    public class Value2 {
        private String content;
        private String adduser;
        private String addtime;
        private String fujian;

        public String getFujian() {
            return fujian;
        }

        public void setFujian(String fujian) {
            this.fujian = fujian;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }

    public class Value3 {
        private String content;
        private String adduser;
        private String addtime;
        private String fujian;

        public String getFujian() {
            return fujian;
        }

        public void setFujian(String fujian) {
            this.fujian = fujian;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }

    public class Value4 {
        private String content;
        private String adduser;
        private String addtime;
        private String fujian;

        public String getFujian() {
            return fujian;
        }

        public void setFujian(String fujian) {
            this.fujian = fujian;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }

}
