package com.sdzx.xtbg.bean;

/**
 * 公文添加基础数据
 * Author:Eron
 * Date: 2016/6/23 0023
 * Time: 15:32
 */
public class DocumentAddBasic {
    private Niban niban;

    public Niban getNiban() {
        return niban;
    }

    public void setNiban(Niban niban) {
        this.niban = niban;
    }

    public class Niban {
        private String name;
        private String user;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

}
