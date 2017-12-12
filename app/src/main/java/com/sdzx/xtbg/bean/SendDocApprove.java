package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 核稿实体类
 * Author:Eron
 * Date: 2016/6/25 0025
 * Time: 20:34
 */
public class SendDocApprove {
    /**
     * {
     * "tag": "0",
     * "tagname": "核稿",
     * "status": "1",
     * "userid": [
     * {
     * "id": "5",
     * "realname": "杨青"
     * },
     * {
     * "id": "4",
     * "realname": "刘军"
     * },
     * {
     * "id": "3",
     * "realname": "安盛传"
     * }
     * ],
     * "totag": "1"
     * }
     */

    public String tag="0";
//    public String depart="0";
    public String status="0";
    public String totag="0";
    public List<UserID> user;

    public SendDocApprove() {
    }

//    public String getDepart() {
//        return depart;
//    }
//
//    public void setDepart(String depart) {
//        this.depart = depart;
//    }

    public List<UserID> getUser() {
        return user;
    }

    public void setUser(List<UserID> user) {
        this.user = user;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotag() {
        return totag;
    }

    public void setTotag(String totag) {
        this.totag = totag;
    }



    public class UserID {
        public String id="";
        public String realname="";

        public UserID() {
        }

        public UserID(String id, String realname) {
            this.id = id;
            this.realname = realname;
        }

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
