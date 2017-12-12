package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 发文详情
 * Author:Eron
 * Date: 2016/6/24 0024
 * Time: 14:52
 */
public class SendDocDetails {


    private List<Value> value;
    private List<Value1> value1;
    private List<Value1> value2;
    private List<Value1> value3;
    private List<Value1> value4;




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

    public List<Value1> getValue2() {
        return value2;
    }

    public void setValue2(List<Value1> value2) {
        this.value2 = value2;
    }

    public List<Value1> getValue3() {
        return value3;
    }

    public void setValue3(List<Value1> value3) {
        this.value3 = value3;
    }

    public List<Value1> getValue4() {
        return value4;
    }

    public void setValue4(List<Value1> value4) {
        this.value4 = value4;
    }

    public class Value {

        private String id;
        private String year;
        private String month;
        private String sort;
        private String type;
        private String bianhao;
        private String title;
        private String zhutici;
        private String zhusong;
        private String chaosong;
        private String fwtime;
        private String fenshu;
        private String adduser;
        private String addtime;
        private String banjie;
        private String banjietime;
        private String depart;
        private String NewName;
        private String OldName;

        public String getZhutici() {
            return zhutici;
        }

        public void setZhutici(String zhutici) {
            this.zhutici = zhutici;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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



        public String getZhusong() {
            return zhusong;
        }

        public void setZhusong(String zhusong) {
            this.zhusong = zhusong;
        }

        public String getChaosong() {
            return chaosong;
        }

        public void setChaosong(String chaosong) {
            this.chaosong = chaosong;
        }

        public String getFwtime() {
            return fwtime;
        }

        public void setFwtime(String fwtime) {
            this.fwtime = fwtime;
        }

        public String getFenshu() {
            return fenshu;
        }

        public void setFenshu(String fenshu) {
            this.fenshu = fenshu;
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

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public String getNewName() {
            return NewName;
        }

        public void setNewName(String newName) {
            NewName = newName;
        }

        public String getOldName() {
            return OldName;
        }

        public void setOldName(String oldName) {
            OldName = oldName;
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
