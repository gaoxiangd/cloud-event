/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved. This software is
 * the confidential and proprietary information of Founder. You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the agreements you entered into with Founder.
 */
/**
 *
 */
package com.welian.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for ...
 *
 * @author tyn
 * @version 1.0, 2014-10-18 下午4:09:20
 */

public class DateUtils extends DateUtil {

    private static final String DATE_SEPARATOR = ".";//日期分隔符

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;


    /**
     * 返回文字描述的日期
     *
     * @param time
     * @return
     */
    public static String getTimeFormatText(long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        StringBuffer stringBuffer = new StringBuffer();
        // 大于2天
        if (diff > (DAY_IN_MILLIS * 2)) {
            int yearthis = Calendar.getInstance().get(Calendar.YEAR);
            int year = calendar.get(Calendar.YEAR);
            int yearDif = yearthis - year;
            if (yearDif >= 1) {
                // 跨年了
                stringBuffer.append(year);
                stringBuffer.append(DATE_SEPARATOR);
            }
            int month = calendar.get(Calendar.MONTH) + 1;
            if (month < 10) {
                stringBuffer.append("0");
            }
            stringBuffer.append(month);
            stringBuffer.append(DATE_SEPARATOR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day < 10) {
                stringBuffer.append("0");
            }
            stringBuffer.append(day);
            return stringBuffer.toString();

        }
        // 大于1天,小于2天
        if (diff > DAY_IN_MILLIS && diff <= (DAY_IN_MILLIS * 2)) {
            // 如果日期只相差1,显示昨天
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int dayToday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            int difDay = dayToday - day;
            if (difDay <= 1) {
                return "昨天";
            } else {
                r = (diff / DAY_IN_MILLIS);
                int month = calendar.get(Calendar.MONTH) + 1;
                if (month < 10) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(month);
                stringBuffer.append(DATE_SEPARATOR);
                if (day < 10) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(day);
                return stringBuffer.toString();
                // return r + "天前";
            }
        }
        // 大于1小时,小于1天
        if (diff > HOUR_IN_MILLIS) {
            r = (diff / HOUR_IN_MILLIS);
            return r + "小时前";
        }
        //大于5分钟,小于一小时显示
        if (diff > (MINUTE_IN_MILLIS * 5)) {
            r = (diff / MINUTE_IN_MILLIS);
            return r + "分钟前";
        }
        // 5分钟内显示
        return "刚刚";
    }


}
