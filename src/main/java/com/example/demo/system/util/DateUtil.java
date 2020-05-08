package com.example.demo.system.util;

import com.exception.CustomException;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 日期类
 */
@Log4j2
public class DateUtil {

    /**
     * 标准日期时间格式，精确到秒
     */
    public final static String	NORM_DATETIME_PATTERN	= "yyyy-MM-dd HH:mm:ss";
    public static final String MSPATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String CURRENTDATE = "yyyyMMdd";
    /**
     * 描述: 获取去年的第一天时间的开始时间
     */
    public static String getPreviousYearFirst () {
        int year = LocalDateTime.now().getYear() - 1;
        return year + "-01-01 00:00:00";
    }

    /**
     * 描述: 获得上年最后一天的日期结束时间
     */
    public static String getPreviousYearEnd () {
        int years_value = LocalDateTime.now().getYear() - 1;
        return years_value + "-12-31 23:59:59";
    }

    /**
     * 描述: 获得本年第一天的开始日期
     */
    public static String getCurrentYearFirst () {
        int years = LocalDateTime.now().getYear();
        return years + "-01-01 00:00:00";
    }

    /**
     * 描述: 获得本年第一天的日期结束日期
     */
    public static String getCurrentYearEnd () {
        int currYear = LocalDateTime.now().getYear();
        return currYear + "-12-31 23:59:59";
    }

    /**
     * 描述: 获取上个月的开始时间
     */
    public static String getPreviousMonthDayBegin () {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startTime = format.format(calendar.getTime()) + " 00:00:00";
        return startTime;
    }

    /**
     * 描述: 获取上个月的结束时间
     */
    public static String getPreviousMonthDayEnd () {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String str = sf.format(calendar.getTime()) + " 23:59:59";
        return str;
    }


    /**
     * 描述: 获取上个周的开始时间
     */
    public static LocalDateTime getBeginLastWeek () {
        LocalDateTime standard = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).toLocalDate().atStartOfDay();
        return standard.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).toLocalDate().atStartOfDay();
    }

    /**
     * 描述: 获取上周的结束时间
     */
    public static LocalDateTime getEndLastWeek () {
        LocalDateTime standard = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).toLocalDate().atStartOfDay();
        return standard.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)).toLocalDate().atStartOfDay();
    }

    /**
     * 描述: 获取某个日期的开始时间
     */
    public static Timestamp getDayStartTime (Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 描述: 获取某个日期的结束时间
     */
    public static Timestamp getDayEndTime (Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上个月时间范围
     */
    public static TimeLimit getLastMonthTimeRange () {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.MONTH, -1);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.MONTH, -1);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        LocalDateTime startTime = LocalDateTime.ofInstant(startCalendar.getTime().toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(endCalendar.getTime().toInstant(), ZoneId.systemDefault());
        return new TimeLimit(startTime, endTime);
    }

    /**
     * 获取某年某月的时间范围
     */
    public static TimeLimit getMonthTimeRange (int year, int month) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, year);
        startCalendar.set(Calendar.MONTH, month - 1);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, year);
        endCalendar.set(Calendar.MONTH, month - 1);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        LocalDateTime startTime = LocalDateTime.ofInstant(startCalendar.getTime().toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(endCalendar.getTime().toInstant(), ZoneId.systemDefault());
        return new TimeLimit(startTime, endTime);
    }

    /**
     * 描述: 获取上个季度的时间（开始和结束时间）
     */
    public static TimeLimit getLastStartAndTimeQuarter () {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, (startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, (endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        LocalDateTime startTime = LocalDateTime.ofInstant(startCalendar.getTime().toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(endCalendar.getTime().toInstant(), ZoneId.systemDefault());
        return new TimeLimit(startTime, endTime);
    }

    /**
     * 获取某年某月的季度范围
     */
    public static TimeLimit getQuarterTimeRange (int year, int quarter) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, year);
        startCalendar.set(Calendar.MONTH, 3 * (quarter - 1));
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.YEAR, year);
        endCalendar.set(Calendar.MONTH, 3 * (quarter - 1) + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        LocalDateTime startTime = LocalDateTime.ofInstant(startCalendar.getTime().toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(endCalendar.getTime().toInstant(), ZoneId.systemDefault());
        return new TimeLimit(startTime, endTime);
    }
    private static void setMinTime (Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    private static void setMaxTime (Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }

    /**
     * 描述: 计算两个时间相差
     */
    public static String getDifferTime (String startTime, String endTime) throws CustomException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        double diff = 0.00;
        if (startTime != null && endTime != null) {
            try {
                Date date1 = df.parse(startTime);
                Date date2 = df.parse(endTime);
                // 转化成小时
                diff = DateUtil.intervalSeconds(date1,date2)/3600.00;
                diff = Double.valueOf(String.format("%.2f", diff < 0 ? 0 : diff));
            }catch (ParseException e){
                throw new CustomException("日期格式不对，应为yyyy-MM-dd HH:mm:ss");
            }
        }
        return String.valueOf(diff);
    }

    /**
     * 每秒的毫秒数
     */
    public static final int MILLIS_PER_SECOND = 1000;
    /**
     *
     * 描述:获取日期间隔分钟数(同一分钟间隔为0)
     * @param startTime 起始时间
     * @param endTime   结束时间
     */
    public static int intervalSeconds(Date startTime, Date endTime) throws CustomException {
        try {
            if (startTime==null||endTime==null) {
                return -1;
            }
            int unit = 1;
            if (startTime.after(endTime)) {
                Date temp = startTime;
                startTime = endTime;
                endTime = temp;
                unit = -1;
            }
            double interval = (endTime.getTime() - startTime.getTime()) / (MILLIS_PER_SECOND);
            return (int) Math.floor(interval) * unit;
        } catch (Exception e) {
            throw new CustomException("--获取日期间隔分钟数失败!");
        }
    }

    /**
     * @description 获取毫秒
     */
    public static String getMsTime(@NotBlank Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(MSPATTERN);
        String dateString = sdf.format(date);
        return dateString;
    }

    /**
     * @description 转换字符至date
     */
    public static LocalDateTime formatLocalDateTime(String dateTime){
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(DateUtil.MSPATTERN);
            LocalDateTime ldt = LocalDateTime.parse(dateTime, df);

            return ldt;
        } catch (Exception e) {
            log.error("日期时间格式转换错误：", e);
            return null;
        }
    }

    /**
     * @description 获取当前时间的年月日
     */
    public static String formatDate(){
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(DateUtil.CURRENTDATE);
            String code = LocalDateTime.now().format(df);
            return code;
        }catch (Exception e){
            log.error("日期时间格式转换错误：",e);
            return null;
        }
    }

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static LocalDateTime parseDateTime(String dateString) throws Exception {
        try {
            if (StringUtil.isEmpty(dateString)) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            throw new Exception(String.format("Parse [%s] with format [%s] error!", dateString, NORM_DATETIME_PATTERN), e);
        }
    }
    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param date 标准形式的时间字符串
     * @return 字符对象
     */
    public static String formatDate(Date date) throws Exception {
        try {
            if (null == date) {
                return null;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(NORM_DATETIME_PATTERN);
            return formatter.format(date);
        } catch (Exception e) {
            throw new Exception(String.format("Parse [%s] with format [%s] error!", date, NORM_DATETIME_PATTERN), e);
        }
    }

    /**
     * 解析时间
     *
     * @return
     */
    public static Date parseTime(String dateTimeStr, String pattern) {

        if (StringUtils.isEmpty(dateTimeStr)) {
            return null;
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(dateTimeStr);
        } catch (Exception e) {
            return null;
        }

    }
}
