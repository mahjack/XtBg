package com.sdzx.xtbg.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Lynn
 * Date: 2016/11/8
 * 请假、休假 审批人
 */

public class Attendance_Examine implements Serializable {

    /**
     * user : [{"id":"3","depart":"1","realname":"孙金升"},{"id":"4","depart":"1","realname":"牟军善"},{"id":"5","depart":"1","realname":"于善翔"},{"id":"6","depart":"1","realname":"宋昱桦"},{"id":"7","depart":"1","realname":"赵莉"},{"id":"8","depart":"1","realname":"王兆远"},{"id":"9","depart":"1","realname":"朱江峰"},{"id":"10","depart":"1","realname":"秦玉彬"}]
     * depart : [{"id":"1","name":"市局领导"}]
     * tag : 2
     */

    private int tag;
    /**
     * id : 3
     * depart : 1
     * realname : 孙金升
     */

    private List<UserBean> user;
    /**
     * id : 1
     * name : 市局领导
     */

    private List<DepartBean> depart;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<DepartBean> getDepart() {
        return depart;
    }

    public void setDepart(List<DepartBean> depart) {
        this.depart = depart;
    }

    public static class UserBean {
        private String id;
        private String depart;
        private String realname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }

    public static class DepartBean {
        private String id;
        private String name;

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
    }
}
