package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 163邮箱列表实体类
 * Author:Eron
 * Date: 2016/7/25 0025
 * Time: 9:34
 */
public class InMail163List {

    /**
     * "title": "体育招引",
     * "mailDate": "2016-07-24 17:07:12",
     * "fromName": "buzhidao",
     * "fromBy": "dy200407@163.com",
     * "id": 14465
     */

    private List<Mail_163> info;

    public List<Mail_163> getInfo() {
        return info;
    }

    public void setInfo(List<Mail_163> info) {
        this.info = info;
    }

    public class Mail_163 {
        private String title;
        private String mailDate;
        private String fromName;
        private String fromBy;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMailDate() {
            return mailDate;
        }

        public void setMailDate(String mailDate) {
            this.mailDate = mailDate;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getFromBy() {
            return fromBy;
        }

        public void setFromBy(String fromBy) {
            this.fromBy = fromBy;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
