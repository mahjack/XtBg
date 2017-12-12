package com.sdzx.xtbg.bean;

/**
 * Author: Eron
 * Date: 2016/8/31
 * Time: 0:45
 */
public class Signet_State {
    /**
     * data : {"totag":9,"userid":"","tag":"0"}
     * message :
     * code : 1
     */

    private DataBean data;
    private String message;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * totag : 9
         * userid :
         * tag : 0
         */

        private int totag;
        private String userid;
        private String tag;

        public int getTotag() {
            return totag;
        }

        public void setTotag(int totag) {
            this.totag = totag;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

//    /**
//     * "totag": 9,
//     * "user": [],
//     * "depart": [],
//     * "tag": "1"
//     */
//
//    private String tag;
//    private List<Depart> depart;
//    private List<User> user;
//    private int totag;
//
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    public int getTotag() {
//        return totag;
//    }
//
//    public void setTotag(int totag) {
//        this.totag = totag;
//    }
//
//    public List<Depart> getDepart() {
//        return depart;
//    }
//
//    public void setDepart(List<Depart> depart) {
//        this.depart = depart;
//    }
//
//    public List<User> getUser() {
//        return user;
//    }
//
//    public void setUser(List<User> user) {
//        this.user = user;
//    }
//
//    public class Depart{
//        private String id;
//        private String name;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//
//    public class User{
//        private String id;
//        private String depart;
//        private String realname;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getDepart() {
//            return depart;
//        }
//
//        public void setDepart(String depart) {
//            this.depart = depart;
//        }
//
//        public String getRealname() {
//            return realname;
//        }
//
//        public void setRealname(String realname) {
//            this.realname = realname;
//        }
//    }


}
