package com.sdzx.xtbg.bean;

import java.io.Serializable;

/**
 * Author: Joe
 * Date: 2017/7/10
 * Time: 10:50
 */

public class WpItemThing implements Serializable {
    private String ming;
    private String num;
    private String jin;
    private String unit;
    private String bz;

    public String getMing() {
        return ming;
    }

    public void setMing(String ming) {
        this.ming = ming;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getJin() {
        return jin;
    }

    public void setJin(String jin) {
        this.jin = jin;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
