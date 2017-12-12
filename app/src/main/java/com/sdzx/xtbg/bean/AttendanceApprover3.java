package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Joe
 * Date: 2017/6/12
 * Time: 9:06
 */

public class AttendanceApprover3 {

    /**
     * data : {"totag":2,"tag":"0","depart":[{"name":"工委、管委","id":"1"}],"userid":[{"id":"13","depart":"1","realname":"张永祥"},{"id":"3","depart":"1","realname":"牟敦谊"},{"id":"38","depart":"1","realname":"张守健"},{"id":"14","depart":"1","realname":"杨世顺"},{"id":"15","depart":"1","realname":"杨宁"},{"id":"16","depart":"1","realname":"卢振赛"}]}
     */
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * totag : 2
         * tag : 0
         * depart : [{"name":"工委、管委","id":"1"}]
         * userid : [{"id":"13","depart":"1","realname":"张永祥"},{"id":"3","depart":"1","realname":"牟敦谊"},{"id":"38","depart":"1","realname":"张守健"},{"id":"14","depart":"1","realname":"杨世顺"},{"id":"15","depart":"1","realname":"杨宁"},{"id":"16","depart":"1","realname":"卢振赛"}]
         */
        private int totag;
        private String tag;
        private List<DepartEntity> depart;
        private List<UseridEntity> userid;

        public void setTotag(int totag) {
            this.totag = totag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setDepart(List<DepartEntity> depart) {
            this.depart = depart;
        }

        public void setUserid(List<UseridEntity> userid) {
            this.userid = userid;
        }

        public int getTotag() {
            return totag;
        }

        public String getTag() {
            return tag;
        }

        public List<DepartEntity> getDepart() {
            return depart;
        }

        public List<UseridEntity> getUserid() {
            return userid;
        }

        public class DepartEntity {
            /**
             * name : 工委、管委
             * id : 1
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

        public class UseridEntity {
            /**
             * id : 13
             * depart : 1
             * realname : 张永祥
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
