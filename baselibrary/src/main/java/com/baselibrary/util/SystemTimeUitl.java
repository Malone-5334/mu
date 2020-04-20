package com.baselibrary.util;

import android.app.AlarmManager;
import android.content.Context;
import android.provider.Settings;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/6/3 12:09
 * <p>
 * SystemTimeUitl
 */
public class SystemTimeUitl {

    private SystemTimeUitl() {
        throw new UnsupportedOperationException("SystemTimeUitl cannot be instantiated");
    }

    /**
     * 判断系统是否是24小时制
     *
     * @param context Context
     * @return
     */
    public static boolean is24Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    /**
     * 判断系统的时区是否是自动获取的
     *
     * @param context Context
     * @return
     */
    public static boolean isTimeZoneAuto(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AUTO_TIME_ZONE) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取系统当前的时区
     *
     * @return
     */
    public static String getDefaultTimeZone() {
        return TimeZone.getDefault().getDisplayName();
    }

    /**
     * 设置系统的时间是否需要自动获取
     *
     * @param context Context
     * @return
     */
    public static void setAutoDateTime(Context context, boolean checked) {
        Settings.Global.putInt(context.getContentResolver(),
                Settings.Global.AUTO_TIME, checked ? 1 : 0);
    }

    /**
     * 设置时区为24小时制
     *
     * @param context Context
     */
    public static void set24Hour(Context context) {
        if (!is24Hour(context)) {
            Settings.System.putString(context.getContentResolver(),
                    Settings.System.TIME_12_24, "24");
        }
    }

    /**
     * 设置时区为12小时制
     *
     * @param context Context
     * @return
     */
    public static boolean set12Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    /**
     * 设置系统日期
     *
     * @param context Context
     * @return
     */
    public static void setSysDate(Context context, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }

    /**
     * 设置系统时间
     *
     * @param context Context
     * @param hour
     * @param minute
     */
    public static void setSysTime(Context context, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }


    /**
     * 设置系统时区
     *
     * @param timeZone
     */
    public static void setTimeZone(String timeZone) {
        final Calendar now = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        now.setTimeZone(tz);
    }

}
