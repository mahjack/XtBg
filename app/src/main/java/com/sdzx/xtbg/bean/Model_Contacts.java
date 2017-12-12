package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Created by 林炜智 on 2016/4/30.
 * 联系人
 */
public class Model_Contacts {

    /**
     * id : 26
     * name : 领导
     */

    private List<DepartBean> depart;
    /**
     * id : 1
     * username : jxw
     * realname : 管理员
     * depart : 36
     * mobile : 15006338943
     * tel : 8781164
     */

    private List<UserBean> user;

    public List<DepartBean> getDepart() {
        return depart;
    }

    public void setDepart(List<DepartBean> depart) {
        this.depart = depart;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class DepartBean {
        private String id;
        private String name;
        private boolean show;   //显示（个人使用）

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }
    }

    public static class UserBean {
        private String id;
        private String username;
        private String realname;
        private String depart;
        private String mobile;
        private String tel;
        private boolean show;   //显示（个人使用）

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }
    }
}
