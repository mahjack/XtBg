package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/5/13 0013
 * Tell：15006330640
 */
public class Meeting_Participation {
    private List<Participation> info;

    public List<Participation> getInfo() {
        return info;
    }

    public void setInfo(List<Participation> info) {
        this.info = info;
    }

    public class Participation{
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
