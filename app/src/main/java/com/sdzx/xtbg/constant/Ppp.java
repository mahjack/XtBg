package com.sdzx.xtbg.constant;



/**
 * Author: Joe
 * Date: 2017/4/25
 * Time: 14:47
 */

public class Ppp {
    private static String time;
    private static String yue;
    private static String ri;
    private static String token;

    public static String inpsw() {
        //月
        if (DateUtils.getDateMonth() < 10) {
            yue = "0" + DateUtils.getDateMonth();
        } else {
            yue = DateUtils.getDateMonth() + "";
        }
        //日
        if (DateUtils.getDateDay() < 10) {
            ri = "0" + DateUtils.getDateDay();
        } else {
            ri = DateUtils.getDateDay() + "";
        }
        time = DateUtils.getDateYear() + "-" + yue + "-" + ri ;
//        KLog.d("time------", "user"+time+"#$@%!"+"list");
        token = MD5Helper.getMD5("user"+time+"#$@%!"+"list").toLowerCase();//大写转小写
//        KLog.d("token------", token);
        return token;
    }
}
