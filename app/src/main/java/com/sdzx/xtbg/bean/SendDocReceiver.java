package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 发文管理接收人
 * Author:Eron
 * Date: 2016/6/25 0025
 * Time: 18:26
 */
public class SendDocReceiver {

    private List<ReceiverUser> user;

    public List<ReceiverUser> getUser() {
        return user;
    }

    public void setUser(List<ReceiverUser> user) {
        this.user = user;
    }

    public class ReceiverUser {
        private String id;
        private String realname;

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
    }

}
