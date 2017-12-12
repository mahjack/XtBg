package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 印鉴审批列表
 * <p/>
 * Author:Eron
 * Date: 2016/7/29 0029
 * Time: 23:41
 */
public class SignetApproveList {

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
     * "adduser": "6",
     * "addtime": "2016-07-25 21:16:13",
     * "banjie": "1",
     * "banjietime": "1469453053",
     * "typename": "法人章0",
     * "itemname": "行政0"
     */

    private List<SignetApprove> value;

    private SignetApproveNumber number;

    public SignetApproveNumber getNumber() {
        return number;
    }

    public void setNumber(SignetApproveNumber number) {
        this.number = number;
    }

    public class SignetApproveNumber {
        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }


    public List<SignetApprove> getValue() {
        return value;
    }

    public void setValue(List<SignetApprove> value) {
        this.value = value;
    }

    public class SignetApprove {

        private String id;
        private String year;
        private String month;
        private String sort;
        private String time;
        private String danwei;
        private String title;
        private String type;
        private String item;
        private String adduser;
        private String addtime;
        private String banjie;
        private String banjietime;
        private String typename;
        private String itemname;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
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
    }


}
