package com.sdzx.xtbg.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Lynn
 * Date: 2016/11/5
 *  审批状态
 */

public class Lynn_Tag implements Serializable {

    /**
     * totag : 3
     * userid : [{"id":"11","depart":"2","realname":"高华堂"}]
     * depart : [{"id":"2","name":"办公室"}]
     * tag : 2
     */

    private DataBean data;
    /**
     * data : {"totag":3,"userid":[{"id":"11","depart":"2","realname":"高华堂"}],"depart":[{"id":"2","name":"办公室"}],"tag":"2"}
     * message :
     * code : 1
     */

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
        private int totag;
        private String tag;
        /**
         * id : 11
         * depart : 2
         * realname : 高华堂
         */

        private List<UseridBean> userid;
        /**
         * id : 2
         * name : 办公室
         */

        private List<DepartBean> depart;

        public int getTotag() {
            return totag;
        }

        public void setTotag(int totag) {
            this.totag = totag;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public List<UseridBean> getUserid() {
            return userid;
        }

        public void setUserid(List<UseridBean> userid) {
            this.userid = userid;
        }

        public List<DepartBean> getDepart() {
            return depart;
        }

        public void setDepart(List<DepartBean> depart) {
            this.depart = depart;
        }

        public static class UseridBean {
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
}
