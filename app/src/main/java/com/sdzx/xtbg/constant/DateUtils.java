package com.sdzx.xtbg.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String TAG = "DateUtils";
    private static SimpleDateFormat simpleDateFormat = null;

    public static String getTimeYMDHM(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getTimeYMDHMS(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getDateYMD() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static int getDateYear() {
        simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getDateMonth() {
        simpleDateFormat = new SimpleDateFormat("M");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getDateDay() {
        simpleDateFormat = new SimpleDateFormat("d");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static String FormatTimeStamp(String TimeStamp) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(TimeStamp);
        return simpleDateFormat.format(date);
    }
    public static String getTime() {
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
