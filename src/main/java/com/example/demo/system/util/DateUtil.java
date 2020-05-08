package com.example.demo.system.util;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.text.DateFormat;
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
