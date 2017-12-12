package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/5/11 0011
 * Tell：15006330640
 *
 *  会议查阅
 */
public class Meeting_Consult {
    private List<Meeting> value;

    public List<Meeting> getValue() {
        return value;
    }

    public void setValue(List<Meeting> value) {
        this.value = value;
    }

    public class Meeting{
        private String id;
        private String title;
        private String meet;
        private String time;
        private String start;
        private String end;
        private String adduser;
        private String banjie;
        private String is_tui;
        private String addr;
        private String renshu;
        private String cont;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getRenshu() {
            return renshu;
        }

        public void setRenshu(String renshu) {
            this.renshu = renshu;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMeet() {
            return meet;
        }

        public void setMeet(String meet) {
            this.meet = meet;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getBanjie() {
            return banjie;
        }

        public void setBanjie(String banjie) {
            this.banjie = banjie;
        }

        public String getIs_tui() {
            return is_tui;
        }

        public void setIs_tui(String is_tui) {
            this.is_tui = is_tui;
        }
    }
}
