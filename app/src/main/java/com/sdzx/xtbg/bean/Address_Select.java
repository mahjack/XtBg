package com.sdzx.xtbg.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/21 0021
 * Tell：15006330640
 */
public class Address_Select {
    private static final String TAG = "Address_Select";
    private ArrayList<Depart> depart;
    private ArrayList<User> user;

    public List<Depart> getDepart() {
        return depart;
    }

    public void setDepart(ArrayList<Depart> depart) {
        this.depart = depart;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }
}
