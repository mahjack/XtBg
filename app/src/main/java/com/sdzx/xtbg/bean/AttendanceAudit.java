package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 考勤审查实体类
 * <p>
 * Author:Eron
 * Date: 2016/5/30 0030
 * Time: 14:26
 */
public class AttendanceAudit {
    private String tag;

    private List<Depart> depart;

    private List<User> user;

    private int totag = 0;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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

    public int getTotag() {
        return totag;
    }

    public void setTotag(int totag) {
        this.totag = totag;
    }

    public class Depart {
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

    public class User {
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

}
