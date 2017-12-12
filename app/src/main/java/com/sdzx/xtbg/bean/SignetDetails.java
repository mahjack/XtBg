package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author: Mark
 * Data:16/9/27
 */

public class SignetDetails {
    private List<SignetObj> value;
    private List<SignetAudit> value1;
    private List<SignetAudit> value2;
    private List<SignetAudit> value3;

    public List<SignetObj> getValue() {
        return value;
    }

    public void setValue(List<SignetObj> value) {
        this.value = value;
    }

    public List<SignetAudit> getValue1() {
        return value1;
    }

    public void setValue1(List<SignetAudit> value1) {
        this.value1 = value1;
    }

    public List<SignetAudit> getValue2() {
        return value2;
    }

    public void setValue2(List<SignetAudit> value2) {
        this.value2 = value2;
    }

    public List<SignetAudit> getValue3() {
        return value3;
    }

    public void setValue3(List<SignetAudit> value3) {
        this.value3 = value3;
    }
}
