package com.sdzx.xtbg.tools;

import android.text.format.Time;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    private static SimpleDateFormat simpleDateFormat;
    /*
         * 时间戳转时间
         */
    public static String getStrTime(String cc_time) {
        if (cc_time != null && !"".equals(cc_time) && !"null".equals(cc_time)) {
            String re_StrTime = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            // 例如：cc_time=1291778220
            long lcc_time = Long.valueOf(cc_time);
            long time = lcc_time - 8 * 60 * 60;
            re_StrTime = sdf.format(new Date(time * 1000L));
            return re_StrTime;
        } else {
            return "";
        }
    }

    /**
     * 时间转时间戳
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static long string2Timestamp(String dateString)
            throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .parse(dateString);
        long temp = date1.getTime();// JAVA的时间戳长度是13位
        return temp;
    }
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

    public static String getTimeForLong(long time){
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }
    public static String getTimeForLong1(long time){
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }
    /**
     *  聊天 时间 String
     */
    public static String getChatTime(long time) throws ParseException {
        String chatTime;
        Date d = new Date(time);
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse(getToday());
        Integer year;
        Integer month;
        Integer day;
        Calendar birthdayCalender = Calendar.getInstance();
        birthdayCalender.setTime(d);
        Calendar todayCalender = Calendar.getInstance();
        todayCalender.setTime(today);
        day = todayCalender.get(Calendar.DAY_OF_MONTH) - birthdayCalender.get(Calendar.DAY_OF_MONTH);
        month = todayCalender.get(Calendar.MONTH) - birthdayCalender.get(Calendar.MONTH);
        year = todayCalender.get(Calendar.YEAR) - birthdayCalender.get(Calendar.YEAR);
        if(day<0){
            month -= 1;
            todayCalender.add(Calendar.MONTH,-1);//得到上一个月，用来得到上个月的天数。
            day = day + todayCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        if(year != 0){
            chatTime = getDateToString1(time);
            return chatTime;
        }else if(month >= 1){
            chatTime = getDateToString1(time);
            return chatTime;
        }else if(day > 7 && month == 0){
            chatTime = getDateToString1(time);
            return chatTime;
        }else if(1 < day && day <= 7 && month == 0){
            chatTime = getWeek(getDateToString3(time));
            return chatTime;
        }else if(day == 1 && month == 0){
            chatTime = "昨天";
            return chatTime;
        }else if(day == 0 && month == 0){
            chatTime = getDateToString2(time);
            return chatTime;
        } else {
            chatTime = "12:00";
            return chatTime;
        }
    }
    /**
     *  聊天 时间 String
     */
    public static String getCommentTime(long time) throws ParseException {
        String chatTime="";
        Date d = new Date(time);
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse(getToday());
        Integer year;
        Integer month;
        Integer day;
        Calendar birthdayCalender = Calendar.getInstance();
        birthdayCalender.setTime(d);
        Calendar todayCalender = Calendar.getInstance();
        todayCalender.setTime(today);
        day = todayCalender.get(Calendar.DAY_OF_MONTH) - birthdayCalender.get(Calendar.DAY_OF_MONTH);
        month = todayCalender.get(Calendar.MONTH) - birthdayCalender.get(Calendar.MONTH);
        year = todayCalender.get(Calendar.YEAR) - birthdayCalender.get(Calendar.YEAR);

        Time time2 = new Time("GMT+8");
        time2.setToNow();

        int minute = time2.minute;
        int hour = time2.hour;
        int sec = time2.second;

        Time time3 = new Time("GMT+8");
        time3.set(time);

        int commentminute = time3.minute;
        int commenthour = time3.hour;
        int commentsec = time3.second;

        hour = hour - commenthour;
        minute = minute - commentminute;
        sec = sec - commentsec;

        if(day<0){
            month -= 1;
            todayCalender.add(Calendar.MONTH,-1);//得到上一个月，用来得到上个月的天数。
            day = day + todayCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        if(year != 0){
            chatTime = year+"年以前";
            return chatTime;
        }else if(month >= 1){
            if (month == 1){
                chatTime = day+"天前";
                return chatTime;
            }
            chatTime = getDateToString(time);
            return chatTime;
        }else if(day > 7 && month == 0){
            chatTime = day+"天以前";
            return chatTime;
        }else if(1 < day && day <= 7 && month == 0){
            chatTime = day+"天以前";
            return chatTime;
        }else if(day == 1 && month == 0){
            chatTime = "昨天";
            return chatTime;
        }else if(day == 0 && month == 0){
            if ( hour > 0 ){
                chatTime = hour+"小时以前";
                return chatTime;
            }
            else if (hour == 0){
                  if (minute > 0){
                      chatTime  = minute + "分钟以前";
                      return chatTime;
                  }
                  else if(minute == 0){
                      if (sec > 0){
                          chatTime  = sec + "秒以前";
                          return chatTime;
                      }
                      else if (sec == 0) {
                          chatTime = "";
                          return chatTime;
                      }
                  }
            }

        } else {
            chatTime = "12:00";
            return chatTime;
        }
        return chatTime;
    }

    // 根据日期获得 星期几
    public static String getWeek(String time){
        String Week = "星期";
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == 1){
            Week += "日";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == 2){
            Week += "一";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == 3){
            Week += "二";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == 4){
            Week += "三";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == 5){
            Week += "四";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == 6){
            Week += "五";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == 7){
            Week += "六";
        }
        return Week;
    }

    /**
     * 获取当前时间 String
     */
    public static String getToday(){
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return year+"-"+month+"-"+day;
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(d);
    }

    /**
     *  时间戳 -- 字符串
     * @param time
     * @param type
     * @return
     */
    public static String getDateToString(long time, String type){
        Date d = new Date(time);
        simpleDateFormat = new SimpleDateFormat(type);
        return simpleDateFormat.format(d);
    }

    /**
     *
     * @param time
     * @return
     */
    public static String getDateToString1(long time){
        Date d = new Date(time);
        simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(d);
    }
    /**
     *
     * @param time
     * @return
     */
    public static String getDateToString2(long time){
        Date d = new Date(time);
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(d);
    }
    /**
     *
     * @param time
     * @return
     */
    public static String getDateToString3(long time){
        Date d = new Date(time);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(d);
    }

    /**
     *  获取时间 13:30
     * @param str
     * @return
     */
    public static String getTimeFromString(String str){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                    .parse(str);
            Calendar todayCalender = Calendar.getInstance();
            todayCalender.setTime(date);
            int hour = todayCalender.get(Calendar.HOUR_OF_DAY);
            int min = todayCalender.get(Calendar.MINUTE);
            return hour+":"+min;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getDateFromString(String time){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDateFormat.format(date);
    }
    public static String getDateFromString(String time, String type) {
        simpleDateFormat = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDateFormat.format(date);
    }

        /**
         * @param time
         * @return
         *
         *  获取天
         */
    public static int getDateFromString1(String time){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            Calendar todayCalender = Calendar.getInstance();
            todayCalender.setTime(date);
            return todayCalender.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     *  判定上午下午
     */
    public static String getAMFromString(String string){
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                    .parse(string);
            Calendar todayCalender = Calendar.getInstance();
            todayCalender.setTime(date1);
            int hour = todayCalender.get(Calendar.HOUR_OF_DAY);
            Log.e("显示上午","--"+hour);
            if(hour < 12){
                return "上午";
            }else {
                return "下午";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "上午";
        }
    }
}
