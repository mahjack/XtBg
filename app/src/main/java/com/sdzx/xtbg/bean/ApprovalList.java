package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Mark
 * Data:16/9/23
 */
public class ApprovalList {
    private List<approval> user;
    private List<depart> depart;
    private int tag;

    public List<ApprovalList.depart> getDepart() {
        return depart;
    }

    public void setDepart(List<ApprovalList.depart> depart) {
        this.depart = depart;
    }

    public List<approval> getUser() {
        return user;
    }

    public void setUser(List<approval> user) {
        this.user = user;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public class depart{
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
    public class approval{
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
