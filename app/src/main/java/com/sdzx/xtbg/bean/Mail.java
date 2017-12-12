package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/9 0009
 * Tell：15006330640
 */
public class Mail {
    private static final String TAG = "Mail";
    private List<Mail_Object> mailinfo;

    public List<Mail_Object> getMailinfo() {
        return mailinfo;
    }

    public void setMailinfo(List<Mail_Object> mailinfo) {
        this.mailinfo = mailinfo;
    }
}
