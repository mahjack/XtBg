package com.sdzx.xtbg.bean;

/**
 * Author：Mark
 * Date：2015/12/11 0011
 * Tell：15006330640
 */
public class Person_Depart {
    private static final String TAG = "Person_Depart";
//    "id": "1",
//            "DepartName": "科室办理"
    private String id;
    private String DepartName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String departName) {
        DepartName = departName;
    }
}
