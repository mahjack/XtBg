package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/5/14 0014
 * Tell：15006330640
 * <p>
 * 会议室信息
 */
public class Meeting_Info {
    private List<Meeting> value;
    private List<Document_Value3> value1;
    private List<Document_Value3> value2;
    private List<Document_Value3> value3;
    private List<Document_Value3> value4;

    public List<Document_Value3> getValue4() {
        return value4;
    }

    public void setValue4(List<Document_Value3> value4) {
        this.value4 = value4;
    }

    public List<Document_Value3> getValue3() {
        return value3;
    }

    public void setValue3(List<Document_Value3> value3) {
        this.value3 = value3;
    }

    public List<Document_Value3> getValue2() {
        return value2;
    }

    public void setValue2(List<Document_Value3> value2) {
        this.value2 = value2;
    }

    public List<Meeting> getValue() {
        return value;
    }

    public void setValue(List<Meeting> value) {
        this.value = value;
    }

    public List<Document_Value3> getValue1() {
        return value1;
    }

    public void setValue1(List<Document_Value3> value1) {
        this.value1 = value1;
    }

    public class Meeting {
        private String id;
        private String year;
        private String month;
        private String sort;
        private String title;
        private String meet;
        private String time;
        private String start;
        private String end;
        private String number;
        private String zhuchi;
        private String meetfw;
        private String bz;
        private String adduser;
        private String addtime;
        private String banjie;
        private String banjietime;
        private String is_tui;
        private String depart;
        private String fanwei;
        private String addr;
        private String duixiang;
        private String renshu;
        private String anpai;
        private String lingdao;
        private String cont;
        private String time_addr;

        public String getTime_addr() {
            return time_addr;
        }

        public void setTime_addr(String time_addr) {
            this.time_addr = time_addr;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }

        public String getDuixiang() {
            return duixiang;
        }

        public void setDuixiang(String duixiang) {
            this.duixiang = duixiang;
        }

        public String getRenshu() {
            return renshu;
        }

        public void setRenshu(String renshu) {
            this.renshu = renshu;
        }

        public String getAnpai() {
            return anpai;
        }

        public void setAnpai(String anpai) {
            this.anpai = anpai;
        }

        public String getLingdao() {
            return lingdao;
        }

        public void setLingdao(String lingdao) {
            this.lingdao = lingdao;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getZhuchi() {
            return zhuchi;
        }

        public void setZhuchi(String zhuchi) {
            this.zhuchi = zhuchi;
        }

        public String getMeetfw() {
            return meetfw;
        }

        public void setMeetfw(String meetfw) {
            this.meetfw = meetfw;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getBanjie() {
            return banjie;
        }

        public void setBanjie(String banjie) {
            this.banjie = banjie;
        }

        public String getBanjietime() {
            return banjietime;
        }

        public void setBanjietime(String banjietime) {
            this.banjietime = banjietime;
        }

        public String getIs_tui() {
            return is_tui;
        }

        public void setIs_tui(String is_tui) {
            this.is_tui = is_tui;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public String getFanwei() {
            return fanwei;
        }

        public void setFanwei(String fanwei) {
            this.fanwei = fanwei;
        }
    }

    public class Opinion {
        private String content;
        private String adduser;
        private String addtime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
