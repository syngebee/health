package com.itheima.health.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期操作工具类
 */
public class DateUtils {


    /**
     * 日期转换-  String -> Date
     *
     * @param dateString 字符串时间
     * @return Date类型信息
     * @throws Exception 抛出异常
     */
    public static Date parseString2Date(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
            return parseString2Date(dateString, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("传入字符串必须：yyyy-MM-dd");
        }
    }

    /**
     * 日期转换-  String -> Date
     *
     * @param dateString 字符串时间
     * @param pattern    格式模板
     * @return Date类型信息
     * @throws Exception 抛出异常
     */
    public static Date parseString2Date(String dateString, String pattern) {
        if (dateString == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("传入字符串必须：yyyy/MM/dd");
        }
    }

    /**
     * 日期转换 Date -> String
     *
     * @param date Date类型信息
     * @return 字符串时间
     * @throws Exception 抛出异常
     */
    public static String parseDate2String(Date date) throws Exception {
        if (date == null) {
            return null;
        }
        return parseDate2String(date, "yyyy-MM-dd");
    }

    /**
     * 日期转换 Date -> String
     *
     * @param date    Date类型信息
     * @param pattern 格式模板
     * @return 字符串时间
     * @throws Exception 抛出异常
     */
    public static String parseDate2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String strDate = sdf.format(date);
            return strDate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前日期的本周一是几号
     *
     * @return 本周一的日期
     */
    public static Date getThisWeekMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取当前日期周的最后一天
     *
     * @return 当前日期周的最后一天
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 7);
        return c.getTime();
    }

    /**
     * 根据日期区间获取月份列表
     *
     * @param minDate 开始时间
     * @param maxDate 结束时间
     * @return 月份列表
     * @throws Exception
     */
    public static List<String> getMonthBetween(String minDate, String maxDate, String format) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf2.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 根据日期获取年度中的周索引
     *
     * @param date 日期
     * @return 周索引
     * @throws Exception
     */
    public static Integer getWeekOfYear(String date) throws Exception {
        Date useDate = parseString2Date(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(useDate);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 根据年份获取年中周列表
     *
     * @param year 年分
     * @return 周列表
     * @throws Exception
     */
    public static Map<Integer, String> getWeeksOfYear(String year) throws Exception {
        Date useDate = parseString2Date(year, "yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(useDate);
        //获取年中周数量
        int weeksCount = cal.getWeeksInWeekYear();
        Map<Integer, String> mapWeeks = new HashMap<>(55);
        for (int i = 0; i < weeksCount; i++) {
            cal.get(Calendar.DAY_OF_YEAR);
            mapWeeks.put(i + 1, parseDate2String(getFirstDayOfWeek(cal.get(Calendar.YEAR), i)));
        }
        return mapWeeks;
    }

    /**
     * 获取某年的第几周的开始日期
     *
     * @param year 年分
     * @param week 周索引
     * @return 开始日期
     * @throws Exception
     */
    public static Date getFirstDayOfWeek(int year, int week) throws Exception {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年的第几周的结束日期
     *
     * @param year 年份
     * @param week 周索引
     * @return 结束日期
     * @throws Exception
     */
    public static Date getLastDayOfWeek(int year, int week) throws Exception {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 获取当前时间所在周的开始日期
     *
     * @param date 当前时间
     * @return 开始时间
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 获取当前时间所在周的结束日期
     *
     * @param date 当前时间
     * @return 结束日期
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    //获得上周一的日期
    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    //获得本周一的日期
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    //获得下周一的日期
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    //获得今天日期
    public static Date getToday() {
        return new Date();
    }

    //获得本月一日的日期
    public static Date getFirstDay4CurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

//  获取指定年和月 的最后一天日期
    public static Date getLastDayOfYearAndMonth(int year, int month) {
//获取Calendar类的实例
        Calendar c = Calendar.getInstance();
//设置年份
        c.set(Calendar.YEAR, year);
//设置月份，因为月份从0开始，所以用month - 1
        c.set(Calendar.MONTH, month - 1);
//获取当前时间下，该月的最大日期的数字
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);

//将获取的最大日期数设置为Calendar实例的日期数
        c.set(Calendar.DAY_OF_MONTH, lastDay);
//获取当前月第一天c.set(Calendar.DAY_OF_MONTH, lastDay);
        Date time = c.getTime();
        String parseDate2String = parseDate2String(time, "yyyy/MM/dd");
        Date date = parseString2Date(parseDate2String, "yyyy/MM/dd");
        return date;
    }


//    获取传入日期所在月的最后一天
    public static Date getLastDayOfMonth( Date date) {

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }



//    获取传入日期所在月的第一天
    public static Date getFirstDayDateOfMonth(Date date) {

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }



    //  获取当前过去12个月  年-月
    public  static  List<String>   getLast12PerMonth (){

        //获取日历对象
        Calendar calendar = Calendar.getInstance();
//设置日历往前推12个月
        calendar.add(Calendar.MONTH,-12);

        List list = new ArrayList();
        for(int i=0;i<12;i++){

//获取每个月的时间
            Date time = calendar.getTime();
//获取每个月的月份
            String month = new SimpleDateFormat("yyyy-MM").format(time);

            String beginDate = month;

            list.add(beginDate);
            calendar.add(Calendar.MONTH,1);
        }
        return list;

    }



    //  获取当前过去12个月  年-月-日
    public  static  List<String>   getLast12FirstDatePerMonth (){

        //获取日历对象
        Calendar calendar = Calendar.getInstance();
//设置日历往前推12个月
        calendar.add(Calendar.MONTH,-12);

        List list = new ArrayList();
        for(int i=0;i<12;i++){

//获取每个月的时间
            Date time = calendar.getTime();
//获取每个月的月份
            String month = new SimpleDateFormat("yyyy-MM").format(time);

            String beginDate = month+"-1";

           list.add(beginDate);
           calendar.add(Calendar.MONTH,1);
        }
            return list;

    }


    //  获取当前过去12个月
    public  static  List<String>   getLast12EndDatePerMonth (){

        //获取日历对象
        Calendar calendar = Calendar.getInstance();
//设置日历往前推12个月
        calendar.add(Calendar.MONTH,-12);

        List list = new ArrayList();
        for(int i=0;i<12;i++){

//获取每个月的时间
            Date time = calendar.getTime();
//获取每个月的月份
            String month = new SimpleDateFormat("yyyy-MM").format(time);

            String beginDate = month+"-31";

            list.add(beginDate);
            calendar.add(Calendar.MONTH,1);
        }
        return list;

    }



    public static void main(String[] args) throws Exception {
//        try {
//            System.out.println("本周一" + parseDate2String(getThisWeekMonday()));
//            System.out.println("本月一日" + parseDate2String(getFirstDay4ThisMonth()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//         Date date = parseString2Date("2020/1/2","yyyy/MM/dd");
//           System.out.println(date);

        System.out.println(getLast12FirstDatePerMonth());
        System.out.println(getLast12EndDatePerMonth());


//        Date lastDayOfMonth = getLastDayOfMonth(new Date());
//        System.out.println(lastDayOfMonth);


//        Date firstDayDateOfMonth = getFirstDayDateOfMonth(new Date());
//        System.out.println(firstDayDateOfMonth);

//         Date f = new Date();
//        System.out.println(f.getDate());

    }
}
