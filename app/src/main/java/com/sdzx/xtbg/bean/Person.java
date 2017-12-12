package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/11 0011
 * Tell：15006330640
 */
public class Person {
    private static final String TAG = "Person";
    private List<Person_Depart> depart;
    private List<Person_User> user;

    public List<Person_Depart> getDepart() {
        return depart;
    }

    public void setDepart(List<Person_Depart> depart) {
        this.depart = depart;
    }

    public List<Person_User> getUser() {
        return user;
    }

    public void setUser(List<Person_User> user) {
        this.user = user;
    }
}
