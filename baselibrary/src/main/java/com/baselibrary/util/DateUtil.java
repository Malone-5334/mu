package com.baselibrary.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/11/2 13:02
 * <p>
 * DateUtil
 */

public class DateUtil {

    public static final String[][] WEEK_NAME = {
            {"周日", "周一", "周二", "周三", "周四", "周五", "周六"},
            {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}};
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期格式：yyyy-MM
     **/
    public static final String DF_YYYY_MM = "yyyy-MM";
    /**
     * 日期格式：yyyy-MM
     **/
    public static final String DF_YYYYMM = "yyyy_MM";
    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";
    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";
    /**
     * 日期格式：yyyy年mm月dd日
     */
    public static final String DF_YYYYMMDD = "yyyy年MM月dd日";
    /**
     * 1分钟
     */
    private final static long minute = 60 * 1000;
    /**
     * 1小时
     */
    private final static long hour = 60 * minute;
    /**
     * 1天
     */
    private final static long day = 24 * hour;
    /**
     * 月
     */
    private final static long month = 31 * day;
    /**
     * 年
     */
    private final static long year = 12 * month;
    static SimpleDateFormat format;

    private DateUtil() {
        throw new UnsupportedOperationException("DateUtil cannot be instantiated");
    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static Date currentDate() {
        return new Date();
    }

    /**
     * 日期date1和日期date2相差年数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentYears(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        return year2 - year1;
    }

    public static int differentYears(String str1, String str2) {
        String[] s1 = str1.split("-");
        String[] s2 = str2.split("-");
        int y1 = Integer.valueOf(s1[0]);
        int m1 = Integer.valueOf(s1[1]);
        int d1 = Integer.valueOf(s1[2]);
        int y2 = Integer.valueOf(s2[0]);
        int m2 = Integer.valueOf(s2[1]);
        int d2 = Integer.valueOf(s2[2]);
        return y2 > y1 ? ((m2 > m1 || (m2 == m1 && d2 >= d1)) ? (y2 - y1) : (y2 - y1 - 1)) : 0;
    }

    /**
     * 日期date1和日期date2相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else {
                    timeDistance += 365;
                }
            }
            return timeDistance + Math.abs((day2 - day1));
        } else {
            return day2 - day1;
        }
    }

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String formatFriendly(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(long dateL) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date date = new Date(dateL);
        return sdf.format(date);
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(long dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param date     日期
     * @param formater
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(Date date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(date);
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @return java.util.date日期类型
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {

        }
        return returnDate;

    }

    /**
     * 验证日期是否比当前日期早
     *
     * @param target1 比较时间1
     * @param target2 比较时间2
     * @return true 则代表target1比target2晚或等于target2，否则比target2早
     */
    public static boolean compareDate(Date target1, Date target2) {
        boolean flag = false;
        try {
            String target1DateTime = formatDateTime(target1, DF_YYYY_MM_DD_HH_MM_SS);
            String target2DateTime = formatDateTime(target2, DF_YYYY_MM_DD_HH_MM_SS);
            if (target1DateTime.compareTo(target2DateTime) <= 0) {
                flag = true;
            }
        } catch (Exception e) {
            System.out.println("比较失败，原因：" + e.getMessage());
        }
        return flag;
    }

    /**
     * 对日期进行增加操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return hour小时后
     */
    public static Date addDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }
        return new Date(target.getTime() + (long) (hour * 60 * 60 * 1000));
    }

    /**
     * 对日期进行相减操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return
     */
    public static Date subDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }

        return new Date(target.getTime() - (long) (hour * 60 * 60 * 1000));
    }

    /**
     * 获取系统时间的方法:月/日 时:分:秒
     */
    public static String getFormateDate() {
        Calendar calendar = Calendar.getInstance();
        int month = (calendar.get(Calendar.MONTH) + 1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String systemTime = (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day) + "  " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
        return systemTime;
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取系统时间  月
     *
     * @return
     */
    public static int getMonthOfYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取系统时间 日
     *
     * @return
     */
    public static int getDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取系统时间的方法:时:分:秒
     */
    public static String getHourAndMinute() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    /**
     * 获取系统时间的方法:时
     */
    public static String getHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return ((hour < 10 ? "0" + hour : hour) + "");
    }

    /**
     * 获取系统时间的方法:分
     */
    public static String getMinute() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        return ((minute < 10) ? "0" + minute : minute + "");
    }

    public static String getYYYYmm() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DF_YYYYMM);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 将2017-07-10 00:00:00 换2017-07-10
     *
     * @param strDate
     * @return
     */
    public static String strFormatStr(String strDate) {
        if (strDate.equals("")) {
            return "";
        }
        return dateToStr(strToDate(strDate));
    }

    /**
     * 2015-01-07 15:05:34
     *
     * @param strDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDateHHMMSS(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 2015-01-07
     *
     * @param strDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * @param strDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDateDorp(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String[] splitDate() {
        String date = getSWAHDate();
        return date.split(" ");
    }

    /**
     * 传入一个long转化为String
     */
    @SuppressLint("SimpleDateFormat")
    public static String longParserString(long param) {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(param));
    }


    /**
     * 传入一个String转化为long
     */
    @SuppressLint("SimpleDateFormat")
    public static Long stringParserLong(String param) throws ParseException {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(param).getTime();
    }

    /**
     * 当前时间转换为long
     */
    @SuppressLint("SimpleDateFormat")
    public static Long currentDateParserLong() throws ParseException {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(format.format(Calendar.getInstance().getTime())).getTime();
    }

    /**
     * 当前时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime() {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateHHMM() {
        format = new SimpleDateFormat("HH:mm");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前时间
     *
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateHHMMSS() {
        format = new SimpleDateFormat("HH:mm:ss");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateString() {
        format = new SimpleDateFormat("yyyyMMddHHmmSSS");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前日期
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(Calendar.getInstance().getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getSWAHDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(Calendar.getInstance().getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static Long stringToLongD(String param) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(param.substring(0, param.length() - 4)).getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static Long stringToLong(String param) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        return format.parse(param).getTime();
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取当月天数
     */
    public static int getMonthday() {
        return getMonthday(getCurrentDate());
    }

    /**
     * 获取当月天数
     */
    public static int getMonthday(String ddate) {
        int year = Integer.valueOf(ddate.split("-")[0]);
        int month = Integer.valueOf(ddate.split("-")[1]);
        if (month == 2) {
            return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) ? 29 : 28;
        } else {
            return (int) (Math.ceil(Math.abs(month - 7.5)) % 2 + 30);
        }
    }

    /**
     * 获取上个月的天数
     *
     * @param ddate
     * @return
     */
    public static int getLastMonthDays(String ddate) {
        return getMonthday(getLastMonthFirstOrLastDay(ddate, "first"));
    }

    /**
     * 获取下个月的天数
     *
     * @param ddate 当前时间  YYYY-MM-DD
     * @return
     */
    private static int getNextMonthDays(String ddate) {
        int year = Integer.valueOf(ddate.split("-")[0]);
        int month = Integer.valueOf(ddate.split("-")[1]);
        month++;
        if (month == 13) {
            year++;
            month = 1;
        }
        return getMonthday(year + "-" + month + "-" + "01");
    }

    /**
     * 获取本月的第一天
     *
     * @param ddate
     * @return
     */
    public static String getMonthFirstDay(String ddate) {
        int year = Integer.valueOf(ddate.split("-")[0]);
        int month = Integer.valueOf(ddate.split("-")[1]);
        return year + "-" + month + "-" + "01";
    }

    /**
     * 获取上个月的第一天或最后一天
     *
     * @param ddate
     * @param flag  "first" -- 第一天
     *              "last" -- 最后一天
     * @return
     */
    public static String getLastMonthFirstOrLastDay(String ddate, String flag) {
        int year = Integer.valueOf(ddate.split("-")[0]);
        int month = Integer.valueOf(ddate.split("-")[1]);
        month--;
        if (month == 0) {
            year--;
            month = 12;
        }
        switch (flag) {
            default:
            case "first":
                return year + "-" + month + "-" + "01";
            case "last":
                return year + "-" + month + "-" + getLastMonthDays(ddate);
        }

    }

    /**
     * 获取上个月的今天
     *
     * @param ddate YYYY-MM-DD
     * @return
     */
    public static String getNowOfLastMonth(String ddate) {
        // Date Format will be display
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar aGregorianCalendar = new GregorianCalendar();
        // Get last month GregorianCalendar object
        aGregorianCalendar.set(Calendar.MONTH, aGregorianCalendar
                .get(Calendar.MONTH) - 1);
        // Format the date to get year and month
        String nowOfLastMonth = aSimpleDateFormat
                .format(aGregorianCalendar.getTime());
        return nowOfLastMonth;
    }

    /**
     * 获取指定日期的前/后几天
     *
     * @param ddate YYYY-mm-dd
     * @param day   {1(后一天)   -1（前一天）}
     * @return
     */
    public static String getAddOrReduceDay(String ddate, int day) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(stringToDate(ddate, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, day);
        Date date = calendar.getTime();
        return dateToStr(date);
    }


    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }


    public static String longToString(Long date, String stringFormat) {
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);
        String str = format.format(date);
        return str;
    }


    /**
     * string类型转换为date类型
     *
     * @param strTime    要转换的string类型的时间
     * @param formatType 格式必须要与formatType的时间格式相同
     * @return
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType  要转换的时间格式
     * @return
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * date类型转换为String类型
     *
     * @param data       date类型转换为String类型
     * @param formatType 格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * string类型转换为long类型
     * strTime的时间格式和formatType的时间格式必须相同
     *
     * @param strTime    要转换的String类型的时间
     * @param formatType 时间格式
     * @return
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * date类型转换为long类型
     *
     * @param date 要转换的date类型的时间
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getLongTime(String time) {
        long ct = 0;
        try {
            format = new SimpleDateFormat("HH:mm:ss");
            ct = format.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ct;
    }

    /**
     * 判断两日期是否同一天
     *
     * @param str1
     * @param str2
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isSameDay(String str1, String str2) {

        Date day1 = null, day2 = null;
        day1 = strToDate(str1);
        day2 = strToDate(str2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String ds1 = sdf.format(day1);

        String ds2 = sdf.format(day2);

        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取两个日期的时间差
     */
    @SuppressLint("SimpleDateFormat")
    public static int getTimeInterval(String date, int flag) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int interval = 0;
        try {
            Date currentTime = new Date();
            Date beginTime = dateFormat.parse(date);
            if (flag == 0) {
                interval = (int) ((beginTime.getTime() - currentTime.getTime()) / (1000));
            } else if (flag == 1) {
                interval = (int) (Math.abs(beginTime.getTime() - currentTime.getTime()) / (1000));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }

    /**
     * 获取两个日期的时间差 几日几小时几分
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeIntervalStr(int date) {
        int mins = date / 60;
        int min = mins % 60;
        int day = mins / 60 / 24;
        int hour = (mins / 60) % 24;
        StringBuffer stringBuffer = new StringBuffer();
        if (day > 0)
            stringBuffer.append(day + "天");
        if (hour > 0)
            stringBuffer.append(hour + "小时");
        if (min > 0)
            stringBuffer.append(min + "分钟");
        if (day == 0 && hour == 0 && min == 0) {
            stringBuffer.append("不足一分钟");
        }
        return stringBuffer.toString();
    }

    /**
     * 获取两个日期的时间差 yyyy.MM.dd HH.mm.ss
     */
    @SuppressLint("SimpleDateFormat")
    public static int getInterval(String bDate, String eDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        int interval = 0;
        try {
            Date currentTime = dateFormat.parse(eDate);
            Date beginTime = dateFormat.parse(bDate);
            interval = (int) ((beginTime.getTime() - currentTime.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }

    /**
     * 两个时间之差 求出一个long Time
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            Date currentTime = new Date();
            Date getdate = df.parse(date);
            diff = getdate.getTime() - currentTime.getTime();

        } catch (Exception e) {
        }
        return diff;
    }

    /**
     * 计算时间差返回分钟
     *
     * @param ddata1 HH:mm
     * @param ddata2 HH:mm
     * @return
     */
    public static long getTimeMin(String ddata1, String ddata2, String flag) {
        if (ddata1.equals(ddata2)) {
            if (flag.equals("u")) {
                return 1;
            } else {
                return 0;
            }
        }
        long diff = getTime(ddata2) - getTime(ddata1);
        if (flag.equals("u")) {
            return (long) Math.ceil(diff / 60000.0f);
        } else {
            return (long) Math.floor(diff / 60000.0f);
        }
    }

    /**
     * 返回时间差 HH:mm:ss
     *
     * @param ddata1
     * @param ddata2
     * @return
     */
    public static String getTimeHHmm(String ddata1, String ddata2) {
        if (ddata1.equals(ddata2)) {
            return "00:01:00";
        }
        long diff = getTime(ddata2) - getTime(ddata1);
        if (diff <= 0) {
            return "00:01:00";
        }
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
        return dateFormat.format(diff);
    }


    /**
     * 比较日期
     *
     * @param date1
     * @param date2
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int compareDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() >= dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 算出系统时间星期几
     */
    public static String formatDate(int i) {
        return formatDate(getCurrentDate(), i);
    }

    /**
     * 传入时间算出星期几
     *
     * @param str
     * @return
     */
    public static String formatDate(String str) {
        return formatDate(str, 0);
    }

    /**
     * 传入时间算出星期几
     *
     * @param str
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String str, int i) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(str);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return WEEK_NAME[i][w];
    }

    /**
     * 判断传入日期是否为工作日 (是否为周六周日的判断)
     *
     * @param ddate 传入日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isWorkday(String ddate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(ddate);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w > 0 && w < 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断传入日期是否为工作日
     * 如果在include中则直接返回ture
     *
     * @param ddate   传入日期
     * @param include 特殊工作日列表
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isWorkday(String ddate, List<String> include) {
        if (include.contains(ddate.substring(5))) {
            return true;
        } else {
            return isWorkday(ddate);
        }
    }

    /**
     * 判断传入日期是否为工作日
     * 如果在include中则直接返回ture
     * 如果在exclude中直接返回false
     *
     * @param ddate   传入日期
     * @param include 特殊工作日列表
     * @param exclude 特殊节假日列表
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isWorkday(String ddate, List<String> include, List<String> exclude) {
        if (exclude.contains(ddate.substring(5))) {
            return false;
        } else {
            return isWorkday(ddate, include);
        }
    }
}
