package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 联系人实体类
 * Author:Eron
 * Date: 2016/8/13 0013
 * Time: 16:20
 */
public class ContactPeople {

    private List<Depart> depart;

    private List<User> user;

    public List<Depart> getDepart() {
        return depart;
    }

    public void setDepart(List<Depart> depart) {
        this.depart = depart;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public class Depart {
        /**
         * "id": "1",
         * "pid": "0",
         * "sort": "1",
         * "name": "集团领导",
         * "type": "2",
         * "adduser": "1",
         * "addtime": "1430029841",
         * "addip": "::1",
         * "status": "0"
         */

        private String id;
        private String pid;
        private String sort;
        private String name;
        private String type;
        private String adduser;
        private String addtime;
        private String addip;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getAddip() {
            return addip;
        }

        public void setAddip(String addip) {
            this.addip = addip;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    private class User {
        /**
         * "id": "1",
         * "sort": "1",
         * "username": "cjjt",
         * "realname": "管理员",
         * "password": "81dc9bdb52d04dc20036dbd8313ed055",
         * "depart": "7",
         * "position": "管理员",
         * "islock": "0",
         * "isdong": "0",
         * "mobile": "17606331763",
         * "tel": "8787558",
         * "htel": null,
         * "lasttime": "1471071737",
         * "lastip": "192.168.20.254",
         * "lastsession": "u19poisgqajkuqa65fpcdhjtj0",
         * "adduser": "1",
         * "addtime": "1427881031",
         * "addip": "::1",
         * "isadmin": "1",
         * "iszg": "1",
         * "iszb": "1"
         */

        private String id;
        private String sort;
        private String username;
        private String realname;
        private String depart;
        private String position;
        private String mobile;
        private String tel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
