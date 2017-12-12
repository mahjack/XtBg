package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Mark
 * Data:16/9/27
 */

public class SignetObj {
    private String id;
    private String year;
    private String month;
    private String sort;
    private String time;
    private String danwei;
    private String title;
    private String type;
    private String item;
    private String adduser;
    private String addtime;
    private String banjie;
    private String banjietime;
    private String typename;
    private String itemname;
    private String fwdanwei;
    private String yzdanwei;

    public String getFwdanwei() {
        return fwdanwei;
    }

    public void setFwdanwei(String fwdanwei) {
        this.fwdanwei = fwdanwei;
    }

    public String getYzdanwei() {
        return yzdanwei;
    }

    public void setYzdanwei(String yzdanwei) {
        this.yzdanwei = yzdanwei;
    }

    private String NewName;
    private String OldName;

    public String getNewName() {
        return NewName;
    }

    public void setNewName(String newName) {
        NewName = newName;
    }

    public String getOldName() {
        return OldName;
    }

    public void setOldName(String oldName) {
        OldName = oldName;
    }

    private List<SignetAttachment> fujian;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public List<SignetAttachment> getFujian() {
        return fujian;
    }

    public void setFujian(List<SignetAttachment> fujian) {
        this.fujian = fujian;
    }
}