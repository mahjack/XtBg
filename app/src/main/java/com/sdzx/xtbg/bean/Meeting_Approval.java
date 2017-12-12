package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/5/13 0013
 * Tell：15006330640
 */
public class Meeting_Approval {
    private List<Depart> depart;
    private List<User> user;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Depart> getDepart() {
        return depart;
    }

    public void setDepart(List<Depart> depart) {
        this.depart = depart;
    }

    public class Depart{
        private String id;
        private String DepartName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDepartName() {
            return DepartName;
        }

        public void setDepartName(String departName) {
            DepartName = departName;
        }
    }
    public class User {
        private String id;
        private String realname;
        private String depart;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
