package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 综合阅读添加文章类型
 * Author:Eron
 * Date: 2016/7/19 0019
 * Time: 17:52
 */
public class ReadAddType {

    private List<Mtype> mtype;

    public List<Mtype> getMtype() {
        return mtype;
    }

    public void setMtype(List<Mtype> mtype) {
        this.mtype = mtype;
    }

    public class Mtype {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
