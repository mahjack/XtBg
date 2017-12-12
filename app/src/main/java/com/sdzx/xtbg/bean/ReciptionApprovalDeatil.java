package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Eron
 * Date: 2017/1/12
 * Time: 11:50
 */

public class ReciptionApprovalDeatil {
    /**
     * data : {"value":[{"year":"2017","jcdd":"地点","title":"事由","pcry":"配餐","bjd_days":" 到  结束，共  天。","banjie":"0","bjd_name":null,"ddld":"名称","end":null,"id":"2","depart":"办公室","is_tui":"0","lkdw":"单位","jcbz":"标准","jsfs":null,"zsbz":null,"start":null,"sort":"2","bjd_sex":null,"lkrs":"25","print":"0","month":"6","adduser":"张鹏飞","addtime":"2017-06-10 16:22:58","bjd_depart":null,"banjietime":null,"days":null,"je":"金额","jbr":"android.widget.EditText{44794760 VFED..CL .F...... 136,0-680,100 #7f0e010f app:id/activity_jiedai_add_jingban}","zpr":"主陪","bjd_zw":null}]}
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
         * value : [{"year":"2017","jcdd":"地点","title":"事由","pcry":"配餐","bjd_days":" 到  结束，共  天。","banjie":"0","bjd_name":null,"ddld":"名称","end":null,"id":"2","depart":"办公室","is_tui":"0","lkdw":"单位","jcbz":"标准","jsfs":null,"zsbz":null,"start":null,"sort":"2","bjd_sex":null,"lkrs":"25","print":"0","month":"6","adduser":"张鹏飞","addtime":"2017-06-10 16:22:58","bjd_depart":null,"banjietime":null,"days":null,"je":"金额","jbr":"android.widget.EditText{44794760 VFED..CL .F...... 136,0-680,100 #7f0e010f app:id/activity_jiedai_add_jingban}","zpr":"主陪","bjd_zw":null}]
         */
        private List<ValueEntity> value;
        private List<Send_Doc_Detail_Process_Value> value1;
        private List<Send_Doc_Detail_Process_Value> value2;
        private List<Send_Doc_Detail_Process_Value> value3;
        private List<Send_Doc_Detail_Process_Value> value4;

        public List<Send_Doc_Detail_Process_Value> getValue1() {
            return value1;
        }

        public void setValue1(List<Send_Doc_Detail_Process_Value> value1) {
            this.value1 = value1;
        }

        public List<Send_Doc_Detail_Process_Value> getValue2() {
            return value2;
        }

        public void setValue2(List<Send_Doc_Detail_Process_Value> value2) {
            this.value2 = value2;
        }

        public List<Send_Doc_Detail_Process_Value> getValue3() {
            return value3;
        }

        public void setValue3(List<Send_Doc_Detail_Process_Value> value3) {
            this.value3 = value3;
        }

        public List<Send_Doc_Detail_Process_Value> getValue4() {
            return value4;
        }

        public void setValue4(List<Send_Doc_Detail_Process_Value> value4) {
            this.value4 = value4;
        }

        public void setValue(List<ValueEntity> value) {
            this.value = value;
        }

        public List<ValueEntity> getValue() {
            return value;
        }

        public class ValueEntity {
            private String year;
            private String jcdd;
            private String title;
            private String pcry;
            private String bjd_days;
            private String banjie;
            private String bjd_name;
            private String ddld;
            private String end;
            private String id;
            private String depart;
            private String is_tui;
            private String lkdw;
            private String jcbz;
            private String jsfs;
            private String zsbz;
            private String start;
            private String sort;
            private String bjd_sex;
            private String lkrs;
            private String print;
            private String month;
            private String adduser;
            private String addtime;
            private String bjd_depart;
            private String banjietime;
            private String days;
            private String je;
            private String jbr;
            private String zpr;
            private String bjd_zw;
            private String time;
            private String hidd;
            private String zrs;
            private String bz;
            private String hydd;

            public String getHydd() {
                return hydd;
            }

            public void setHydd(String hydd) {
                this.hydd = hydd;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getHidd() {
                return hidd;
            }

            public void setHidd(String hidd) {
                this.hidd = hidd;
            }

            public String getZrs() {
                return zrs;
            }

            public void setZrs(String zrs) {
                this.zrs = zrs;
            }

            public String getBz() {
                return bz;
            }

            public void setBz(String bz) {
                this.bz = bz;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public void setJcdd(String jcdd) {
                this.jcdd = jcdd;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setPcry(String pcry) {
                this.pcry = pcry;
            }

            public void setBjd_days(String bjd_days) {
                this.bjd_days = bjd_days;
            }

            public void setBanjie(String banjie) {
                this.banjie = banjie;
            }

            public void setBjd_name(String bjd_name) {
                this.bjd_name = bjd_name;
            }

            public void setDdld(String ddld) {
                this.ddld = ddld;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setDepart(String depart) {
                this.depart = depart;
            }

            public void setIs_tui(String is_tui) {
                this.is_tui = is_tui;
            }

            public void setLkdw(String lkdw) {
                this.lkdw = lkdw;
            }

            public void setJcbz(String jcbz) {
                this.jcbz = jcbz;
            }

            public void setJsfs(String jsfs) {
                this.jsfs = jsfs;
            }

            public void setZsbz(String zsbz) {
                this.zsbz = zsbz;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public void setBjd_sex(String bjd_sex) {
                this.bjd_sex = bjd_sex;
            }

            public void setLkrs(String lkrs) {
                this.lkrs = lkrs;
            }

            public void setPrint(String print) {
                this.print = print;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public void setAdduser(String adduser) {
                this.adduser = adduser;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setBjd_depart(String bjd_depart) {
                this.bjd_depart = bjd_depart;
            }

            public void setBanjietime(String banjietime) {
                this.banjietime = banjietime;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public void setJe(String je) {
                this.je = je;
            }

            public void setJbr(String jbr) {
                this.jbr = jbr;
            }

            public void setZpr(String zpr) {
                this.zpr = zpr;
            }

            public void setBjd_zw(String bjd_zw) {
                this.bjd_zw = bjd_zw;
            }

            public String getYear() {
                return year;
            }

            public String getJcdd() {
                return jcdd;
            }

            public String getTitle() {
                return title;
            }

            public String getPcry() {
                return pcry;
            }

            public String getBjd_days() {
                return bjd_days;
            }

            public String getBanjie() {
                return banjie;
            }

            public String getBjd_name() {
                return bjd_name;
            }

            public String getDdld() {
                return ddld;
            }

            public String getEnd() {
                return end;
            }

            public String getId() {
                return id;
            }

            public String getDepart() {
                return depart;
            }

            public String getIs_tui() {
                return is_tui;
            }

            public String getLkdw() {
                return lkdw;
            }

            public String getJcbz() {
                return jcbz;
            }

            public String getJsfs() {
                return jsfs;
            }

            public String getZsbz() {
                return zsbz;
            }

            public String getStart() {
                return start;
            }

            public String getSort() {
                return sort;
            }

            public String getBjd_sex() {
                return bjd_sex;
            }

            public String getLkrs() {
                return lkrs;
            }

            public String getPrint() {
                return print;
            }

            public String getMonth() {
                return month;
            }

            public String getAdduser() {
                return adduser;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getBjd_depart() {
                return bjd_depart;
            }

            public String getBanjietime() {
                return banjietime;
            }

            public String getDays() {
                return days;
            }

            public String getJe() {
                return je;
            }

            public String getJbr() {
                return jbr;
            }

            public String getZpr() {
                return zpr;
            }

            public String getBjd_zw() {
                return bjd_zw;
            }
        }
    }



//    class DataEntity{
//        private List<ValueEntity> value;
//        private List<Send_Doc_Detail_Process_Value> value1;
//        private List<Send_Doc_Detail_Process_Value> value2;
//        private List<Send_Doc_Detail_Process_Value> value3;
//        private List<Send_Doc_Detail_Process_Value> value4;
//
//        public List<Send_Doc_Detail_Process_Value> getValue1() {
//            return value1;
//        }
//
//        public void setValue1(List<Send_Doc_Detail_Process_Value> value1) {
//            this.value1 = value1;
//        }
//
//        public List<Send_Doc_Detail_Process_Value> getValue2() {
//            return value2;
//        }
//
//        public void setValue2(List<Send_Doc_Detail_Process_Value> value2) {
//            this.value2 = value2;
//        }
//
//        public List<Send_Doc_Detail_Process_Value> getValue3() {
//            return value3;
//        }
//
//        public void setValue3(List<Send_Doc_Detail_Process_Value> value3) {
//            this.value3 = value3;
//        }
//
//        public List<Send_Doc_Detail_Process_Value> getValue4() {
//            return value4;
//        }
//
//        public void setValue4(List<Send_Doc_Detail_Process_Value> value4) {
//            this.value4 = value4;
//        }
//
//        public void setValue(List<ValueEntity> value) {
//            this.value = value;
//        }
//
//        public List<ValueEntity> getValue() {
//            return value;
//        }
//
//        public class ValueEntity {
//            /**
//             * jcbz : 就餐标准
//             * year : 2017
//             * jsfs : 3
//             * start : 2017-01-12
//             * zsbz : 住宿标准
//             * sort : 5
//             * title : 事由
//             * pcry : 陪餐人员
//             * bjd_sex : 性别
//             * banjie : 0
//             * print : 0
//             * month : 1
//             * bjd_name : 姓名
//             * adduser : 苗凤莲
//             * bjd_depart : 902测试单位名称
//             * addtime : 2017-01-12 09:04:00
//             * banjietime : null
//             * days : 2
//             * end : 2017-01-13
//             * id : 5
//             * is_tui : 0
//             * bjd_zw : 职务
//             */
//            private String jcbz;
//            private String year;
//            private String jsfs;
//            private String start;
//            private String zsbz;
//            private String sort;
//            private String title;
//            private String pcry;
//            private String bjd_sex;
//            private String banjie;
//            private String print;
//            private String month;
//            private String bjd_name;
//            private String adduser;
//            private String bjd_depart;
//            private String addtime;
//            private String banjietime;
//            private String days;
//            private String end;
//            private String id;
//            private String is_tui;
//            private String bjd_zw;
//
//            public void setJcbz(String jcbz) {
//                this.jcbz = jcbz;
//            }
//
//            public void setYear(String year) {
//                this.year = year;
//            }
//
//            public void setJsfs(String jsfs) {
//                this.jsfs = jsfs;
//            }
//
//            public void setStart(String start) {
//                this.start = start;
//            }
//
//            public void setZsbz(String zsbz) {
//                this.zsbz = zsbz;
//            }
//
//            public void setSort(String sort) {
//                this.sort = sort;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public void setPcry(String pcry) {
//                this.pcry = pcry;
//            }
//
//            public void setBjd_sex(String bjd_sex) {
//                this.bjd_sex = bjd_sex;
//            }
//
//            public void setBanjie(String banjie) {
//                this.banjie = banjie;
//            }
//
//            public void setPrint(String print) {
//                this.print = print;
//            }
//
//            public void setMonth(String month) {
//                this.month = month;
//            }
//
//            public void setBjd_name(String bjd_name) {
//                this.bjd_name = bjd_name;
//            }
//
//            public void setAdduser(String adduser) {
//                this.adduser = adduser;
//            }
//
//            public void setBjd_depart(String bjd_depart) {
//                this.bjd_depart = bjd_depart;
//            }
//
//            public void setAddtime(String addtime) {
//                this.addtime = addtime;
//            }
//
//            public void setBanjietime(String banjietime) {
//                this.banjietime = banjietime;
//            }
//
//            public void setDays(String days) {
//                this.days = days;
//            }
//
//            public void setEnd(String end) {
//                this.end = end;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public void setIs_tui(String is_tui) {
//                this.is_tui = is_tui;
//            }
//
//            public void setBjd_zw(String bjd_zw) {
//                this.bjd_zw = bjd_zw;
//            }
//
//            public String getJcbz() {
//                return jcbz;
//            }
//
//            public String getYear() {
//                return year;
//            }
//
//            public String getJsfs() {
//                return jsfs;
//            }
//
//            public String getStart() {
//                return start;
//            }
//
//            public String getZsbz() {
//                return zsbz;
//            }
//
//            public String getSort() {
//                return sort;
//            }
//
//            public String getTitle() {
//                return title;
//            }
//
//            public String getPcry() {
//                return pcry;
//            }
//
//            public String getBjd_sex() {
//                return bjd_sex;
//            }
//
//            public String getBanjie() {
//                return banjie;
//            }
//
//            public String getPrint() {
//                return print;
//            }
//
//            public String getMonth() {
//                return month;
//            }
//
//            public String getBjd_name() {
//                return bjd_name;
//            }
//
//            public String getAdduser() {
//                return adduser;
//            }
//
//            public String getBjd_depart() {
//                return bjd_depart;
//            }
//
//            public String getAddtime() {
//                return addtime;
//            }
//
//            public String getBanjietime() {
//                return banjietime;
//            }
//
//            public String getDays() {
//                return days;
//            }
//
//            public String getEnd() {
//                return end;
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public String getIs_tui() {
//                return is_tui;
//            }
//
//            public String getBjd_zw() {
//                return bjd_zw;
//            }
//        }
//    }

}
