package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Joe
 * Date: 2017/6/10
 * Time: 15:34
 */

public class AttendanceApprover2 {

    /**
     * code : 1
     * data : {"totag":0,"depart":[{"name":"办公室","id":"2"}],"user":[{"id":"4","depart":"2","realname":"马涛"}]}
     * message :
     */
    private int code;
    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public class DataEntity {
        /**
         * totag : 0
         * depart : [{"name":"办公室","id":"2"}]
         * user : [{"id":"4","depart":"2","realname":"马涛"}]
         */
        private int totag;
        private List<DepartEntity> depart;
        private List<UserEntity> user;

        public void setTotag(int totag) {
            this.totag = totag;
        }

        public void setDepart(List<DepartEntity> depart) {
            this.depart = depart;
        }

        public void setUser(List<UserEntity> user) {
            this.user = user;
        }

        public int getTotag() {
            return totag;
        }

        public List<DepartEntity> getDepart() {
            return depart;
        }

        public List<UserEntity> getUser() {
            return user;
        }

        public class DepartEntity {
            /**
             * name : 办公室
             * id : 2
             */
            private String name;
            private String id;

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }
        }

        public class UserEntity {
            /**
             * id : 4
             * depart : 2
             * realname : 马涛
             */
            private String id;
            private String depart;
            private String realname;

            public void setId(String id) {
                this.id = id;
            }

            public void setDepart(String depart) {
                this.depart = depart;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getId() {
                return id;
            }

            public String getDepart() {
                return depart;
            }

            public String getRealname() {
                return realname;
            }
        }
    }
}
