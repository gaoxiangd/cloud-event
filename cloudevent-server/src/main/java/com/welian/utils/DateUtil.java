package com.welian.utils;

import org.sean.framework.util.Logger;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by memorytale on 2017/4/24.
 */
public class DateUtil {

    public static final Long ONE_DAY_LONG=24*3600*1000L;

    private static final Logger logger = Logger.newInstance(DateUtil.class);

    public static int getIntervalDays(Date fDate, Date oDate) {
        if (null == fDate || null == oDate) {
            return -1;

        }
        long intervalMilli = oDate.getTime() - fDate.getTime();

        return (int) (intervalMilli / (24 * 60 * 60 * 1000));

    }

    public static int daysOfTwo(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(oDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;

    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static String DATE_FORMAT = "yyyy-MM-dd";

    // 长日期格式
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转换成 02/21 周六 格式字符串（app端用）
     */
    public static String getWeekAndDate(Long time){
        String startDateStr="";
        SimpleDateFormat format =  new SimpleDateFormat("MM/dd");
        String d = format.format(time);
        try {
            SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d1 = format1.format(time);
            Date date=format1.parse(d1);
            String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            startDateStr= weekDaysName[intWeek];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startDateStr=d+" "+startDateStr;
        return startDateStr;
    }


    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param date
     * @param format
     * @return
     */
    public static long convert2long(String date, String format) {
        try {
            if (StringUtils.isNotBlank(date)) {
                if (StringUtils.isBlank(format))
                    format = DateUtil.TIME_FORMAT;
                SimpleDateFormat sf = new SimpleDateFormat(format);
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 将长整型数字转换为日期格式的字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String convert2String(long time, String format) {
        if (time > 0l) {
            if (StringUtils.isBlank(format))
                format = DateUtil.TIME_FORMAT;
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date date = new Date(time);
            return sf.format(date);
        }
        return "";
    }

    /**
     * 获取当前系统的日期
     *
     * @return
     */
    public static long curTimeMillis() {
        return System.currentTimeMillis();
    }


    /**
     * 清空Calendar的某些时间
     *
     * @param c
     * @param fields
     */
    public static void clearCalendar(Calendar c, int... fields) {
        for (int f : fields) {
            c.set(f, 0);
        }
    }


    public static String getIncreaseDate(Date startDate, int days, String format) {
        Calendar cal_start;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            cal_start = getCalendar();
            cal_start.setTime(startDate);
            cal_start.add(Calendar.DAY_OF_YEAR, days);
            return dateFormat.format(cal_start.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getFormatedDate(Date date, String format) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Get date object of current
     *
     * @return
     */
    public static Date getNowDate() {
        return getCalendar().getTime();
    }


    /**
     * Get default time zone of China
     *
     * @return default time zone
     */
    public static TimeZone getDefaultTimeZone() {
        return TimeZone.getTimeZone("GMT+8");
    }

    /**
     * Get calendar instance with default time zone of GMT+8
     *
     * @return Calendar instance
     */
    public static Calendar getCalendar() {
        TimeZone.setDefault(getDefaultTimeZone());
        return Calendar.getInstance();
    }


    /**
     * 获得当天24点时间 单位：毫秒
     *
     * @return
     */
    public static Long getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis());
    }

    /**
     * 获得当天0点时间 单位：毫秒
     *
     * @return
     */
    public static Long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() );
    }

    /**
     * 获取当前时间，几天后的0点的时间戳 单位：毫秒
     *
     * @return
     */
    public static Long getPassDayBeginTime(Integer day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.getTime());
    }

    /**
     * 获得本周一0点时间
     *
     * @return
     */
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return cal.getTime();
    }

    /**
     * 获得不包含天数的时间字符串
     * @param time
     * @return
     */
    public static String getStrTimeNotDay(Long time){
        SimpleDateFormat format = new SimpleDateFormat(" HH:mm ");
        String d = format.format(time);
        return d;
    }

    /**
     * 获得本周日24点时间
     *
     * @return
     */
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    /**
     * 获得本月第一天0点时间
     *
     * @return
     */
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得本月最后一天24点时间
     *
     * @return
     */
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }


    /**
     * 时间戳转date
     *
     * @return
     */
    public static Date timestampToDate(Long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        String d = format.format(timestamp * 1000);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String converseTime(Long startTime, Long endTime) {
        if (startTime != null && startTime != 0 && endTime != null && endTime != 0) {
            Map start = converseWeek(startTime);
            Map end = converseWeek(endTime);
            if ((int) start.get("minute") == 0) {
                start.put("minute", "00");
            } else if ((int) start.get("minute") < 10) {
                start.put("minute", "0" + start.get("minute"));
            }

            if ((int) start.get("hour") == 0) {
                start.put("hour", "00");
            } else if ((int) start.get("hour") < 10) {
                start.put("hour", "0" + start.get("hour"));
            }

            if ((int) end.get("minute") == 0) {
                end.put("minute", "00");
            } else if ((int) end.get("minute") < 10) {
                end.put("minute", "0" + end.get("minute"));
            }

            if ((int) end.get("hour") == 0) {
                end.put("hour", "00");
            } else if ((int) end.get("hour") < 10) {
                end.put("hour", "0" + end.get("hour"));
            }

            if (start.get("month") == end.get("month") && start.get("day") == end.get("day")) {
                return new StringBuilder().append(start.get("month")).append("-").append(start.get("day"))
                        .append(start.get("week")).append(start.get("hour")).append(":")
                        .append(start.get("minute")).append(" ~ ").append(end.get("hour"))
                        .append(":").append(end.get("minute")).toString();
            }
            return new StringBuilder().append(start.get("month")).append("-").append(start.get("day"))
                    .append(start.get("week")).append(start.get("hour")).append(":")
                    .append(start.get("minute")).append(" ~ ").append(end.get("month")).append("-")
                    .append(end.get("day")).append(end.get("week")).append(end.get("hour"))
                    .append(":").append(end.get("minute")).toString();
        }
        return null;
    }


    public static Map converseWeek(Long time) {
        if (time != null && time != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
            int dayNum = calendar.get(Calendar.DAY_OF_MONTH);
            int monthNum = calendar.get(Calendar.MONTH);
            int hourNum = calendar.get(Calendar.HOUR_OF_DAY);
            int minuteNum = calendar.get(Calendar.MINUTE);

            String week = null;
            switch (weekNum) {
                case 1:
                    week = "（周日）";
                    break;
                case 2:
                    week = "（周一）";
                    break;
                case 3:
                    week = "（周二）";
                    break;
                case 4:
                    week = "（周三）";
                    break;
                case 5:
                    week = "（周四）";
                    break;
                case 6:
                    week = "（周五）";
                    break;
                case 7:
                    week = "（周六）";
                    break;
            }
            Map map = new HashMap();
            map.put("week", week);
            map.put("day", dayNum);
            map.put("month", monthNum + 1);
            map.put("hour", hourNum);
            map.put("minute", minuteNum);
            return map;
        }
        return null;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1500616979000L);
        int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
        int dayNum = calendar.get(Calendar.DAY_OF_MONTH);
        int monthNum = calendar.get(Calendar.MONTH);
        int hourNum = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteNum = calendar.get(Calendar.MINUTE);
        System.out.println(monthNum);
        System.out.println(dayNum);
    }

    public static String getRangeFormatDate(Long startTime, Long endTime) {
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd HH:mm");
        return format.format(startTime)+"~"+format.format(endTime);
    }
}
