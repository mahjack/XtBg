package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Mark
 * Data:16/9/23
 */

public class SignetObject {
    private List<Signet> info;

    public List<Signet> getInfo() {
        return info;
    }

    public void setInfo(List<Signet> info) {
        this.info = info;
    }

    public class Signet{
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
