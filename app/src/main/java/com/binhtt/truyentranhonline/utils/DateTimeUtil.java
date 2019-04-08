package com.binhtt.truyentranhonline.utils;

import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private static DateTimeFormatter sDateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter sDateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static DateTimeFormatter sTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss");

    public static DateTime create(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;

        if (dateTime.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return sDateFormatter.withZone(DateTimeZone.getDefault()).parseDateTime(dateTime);
        } else if (dateTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
            return sTimeFormatter.withZone(DateTimeZone.getDefault()).parseDateTime(dateTime);
        }
        return sDateTimeFormatter.withZone(DateTimeZone.getDefault()).parseDateTime(dateTime);
    }

    public static DateTime create(String timeZone, String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;
        DateTimeZone dateTimeZone = DateTimeZone.forID(timeZone);

        if (dateTime.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return sDateFormatter.withZone(dateTimeZone).parseDateTime(dateTime);
        } else if (dateTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
            return sTimeFormatter.withZone(dateTimeZone).parseDateTime(dateTime);
        }
        return sDateTimeFormatter.withZone(dateTimeZone).parseDateTime(dateTime);
    }

    public static DateTime convertToLocal(DateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.withZone(DateTimeZone.getDefault());
    }

    public static String getDate(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("yyyy/MM/dd").print(dateTime);
    }

    public static String getDateHyphen(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime);
    }

    public static String getDateEditTimeZone(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(dateTime);
    }

    public static String getDateEditTime(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").print(dateTime);
    }

    public static String getDateTimeJapan(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("MMM/dd/EEE HH:mm").print(dateTime);
    }

    public static String getDateTimeJapanese(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("MMMdd日EEEE HH:mm").print(dateTime);
    }

    public static String getDateTimeJapaneseHyphen(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("MMMdd日EEEE").print(dateTime);
    }

    public static String getDateTimeJapanHyphen(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("MMM/dd/EEE").print(dateTime);
    }

    public static String getTime(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("HH:mm:ss").print(dateTime);
    }

    public static String getDateTime(DateTime dateTime) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").print(dateTime);
    }

    public static String getDateTime(DateTime dateTime, String format) {
        if (dateTime == null) return null;
        return DateTimeFormat.forPattern(format).print(dateTime);
    }

    public static String getDate(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;
        return DateTimeFormat.forPattern("yyyy/MM/dd").print(create(dateTime));
    }

    public static String getTime(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;
        return DateTimeFormat.forPattern("HH:mm:ss").print(create(dateTime));
    }

    public static String getDateTime(String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;
        return DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").print(create(dateTime));
    }

    public static String getDate(String timeZone, String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;
        return DateTimeFormat.forPattern("yyyy/MM/dd").print(create(timeZone, dateTime));
    }

    public static String getTime(String timeZone, String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;
        return DateTimeFormat.forPattern("HH:mm:ss").print(create(timeZone, dateTime));
    }

    public static String getDateTime(String timeZone, String dateTime) {
        if (TextUtils.isEmpty(dateTime)) return null;
        return DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").print(create(timeZone, dateTime));
    }

    public static int getOld(String timeZone, String dayOfBirth) {
        if (TextUtils.isEmpty(dayOfBirth)) return 0;
        DateTime localTime = convertToLocal(create(timeZone, dayOfBirth));
        return Years.yearsBetween(localTime, DateTime.now()).getYears() + 1;
    }

    public static int getOld(String dayOfBirth) {
        if (TextUtils.isEmpty(dayOfBirth)) return 0;
        DateTime localTime = convertToLocal(create(dayOfBirth));
        return Years.yearsBetween(localTime, DateTime.now()).getYears() + 1;
    }

    public static String getStringOld(String timeZone, String dayOfBirth) {
        if (TextUtils.isEmpty(dayOfBirth)) return "0";
        DateTime localTime = convertToLocal(create(timeZone, dayOfBirth));
        return String.valueOf(Years.yearsBetween(localTime, DateTime.now()).getYears() + 1);
    }

    public static String getStringOld(String dayOfBirth) {
        if (TextUtils.isEmpty(dayOfBirth)) return "0";
        DateTime localTime = convertToLocal(create(dayOfBirth));
        return String.valueOf(Years.yearsBetween(localTime, DateTime.now()).getYears() + 1);
    }

    public static boolean isLeapYear(int year) {
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
    }
}
