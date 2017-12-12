package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 163邮件详情实体类
 * Author:Eron
 * Date: 2016/7/25 0025
 * Time: 15:25
 */
public class InMail163Detail {

    private List<MailDetail> info;

    public List<MailDetail> getInfo() {
        return info;
    }

    public void setInfo(List<MailDetail> info) {
        this.info = info;
    }

    public class MailDetail {

        /**
         * {
         * "id": "32",
         * "wen": "14468",
         * "fromBy": "lswtj2611315@163.com",
         * "title": "关于开展落实安全生产监管责任专项检查的通知",
         * "fromName": "岚山区文体旅游局",
         * "mailDate": "2016-07-25 11:50:22",
         * "body": "",
         * "NewName": "http://119.184.122.213:82/upload/2512030113259.doc|",
         * "OldName": "关于开展落实安全生产监管责任.doc|"
         * }
         */

        private String id;
        private String wen;
        private String fromBy;
        private String title;
        private String fromName;
        private String mailDate;
        private String body;
        private String NewName;
        private String OldName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWen() {
            return wen;
        }

        public void setWen(String wen) {
            this.wen = wen;
        }

        public String getFromBy() {
            return fromBy;
        }

        public void setFromBy(String fromBy) {
            this.fromBy = fromBy;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getMailDate() {
            return mailDate;
        }

        public void setMailDate(String mailDate) {
            this.mailDate = mailDate;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
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
}
