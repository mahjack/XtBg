package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/9 0009
 * Tell：15006330640
 */
public class Address {
    private static final String TAG = "Address";
    private List<Depart> depart;
    private List<User> user;

    public List<Depart> getDepart() {
        return depart;
    }

    public void setDepart(List<Depart> depart) {
        this.depart = depart;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
