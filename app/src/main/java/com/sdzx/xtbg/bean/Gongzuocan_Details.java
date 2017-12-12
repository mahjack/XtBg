package com.sdzx.xtbg.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Lynn
 * Date: 2016/11/4
 * 工作餐详细
 */

public class Gongzuocan_Details implements Serializable {

    /**
     * code : 1
     * message :
     * data : {"value":[{"id":"1","year":"2016","month":"8","sort":"1","title":"加班整理会议发言","jcrs":"6","total":"200","jsfs":"现金","adduser":"牟军善","addtime":"2016-08-29 12:06:58","banjie":"0","banjietime":null,"is_tui":"0","print":"1","depart":"市局领导","bjd_days":" 到  结束，共  天。","jtbt":null}],"value1":[],"value2":[],"value3":[],"value4":[]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * year : 2016
         * month : 8
         * sort : 1
         * title : 加班整理会议发言
         * jcrs : 6
         * total : 200
         * jsfs : 现金
         * adduser : 牟军善
         * addtime : 2016-08-29 12:06:58
         * banjie : 0
         * banjietime : null
         * is_tui : 0
         * print : 1
         * depart : 市局领导
         * bjd_days :  到  结束，共  天。
         * jtbt : null
         */

        private List<ValueBean> value;
        private List<Value1Bean> value1;
        private List<Value1Bean> value2;
        private List<Value1Bean> value3;
        private List<Value1Bean> value4;

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public List<Value1Bean> getValue1() {
            return value1;
        }

        public void setValue1(List<Value1Bean> value1) {
            this.value1 = value1;
        }

        public List<Value1Bean> getValue2() {
            return value2;
        }

        public void setValue2(List<Value1Bean> value2) {
            this.value2 = value2;
        }

        public List<Value1Bean> getValue3() {
            return value3;
        }

        public void setValue3(List<Value1Bean> value3) {
            this.value3 = value3;
        }

        public List<Value1Bean> getValue4() {
            return value4;
        }

        public void setValue4(List<Value1Bean> value4) {
            this.value4 = value4;
        }

        public static class ValueBean {
            private String id;
            private String year;
            private String month;
            private String sort;
            private String title;
            private String jcrs;
            private String total;
            private String jsfs;
            private String adduser;
            private String addtime;
            private String banjie;
            private Object banjietime;
            private String is_tui;
            private String print;
            private String depart;
            private String bjd_days;
            private Object jtbt;

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

            public String getJsfs() {
                return jsfs;
            }

            public void setJsfs(String jsfs) {
                this.jsfs = jsfs;
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

            public Object getBanjietime() {
                return banjietime;
            }

            public void setBanjietime(Object banjietime) {
                this.banjietime = banjietime;
            }

            public String getIs_tui() {
                return is_tui;
            }

            public void setIs_tui(String is_tui) {
                this.is_tui = is_tui;
            }

            public String getPrint() {
                return print;
            }

            public void setPrint(String print) {
                this.print = print;
            }

            public String getDepart() {
                return depart;
            }

            public void setDepart(String depart) {
                this.depart = depart;
            }

            public String getBjd_days() {
                return bjd_days;
            }

            public void setBjd_days(String bjd_days) {
                this.bjd_days = bjd_days;
            }

            public Object getJtbt() {
                return jtbt;
            }

            public void setJtbt(Object jtbt) {
                this.jtbt = jtbt;
            }
        }

        public static class Value1Bean {
            private String content;
            private String adduser;
            private String addtime;

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
}
