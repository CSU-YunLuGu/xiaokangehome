package com.csuyunlugu.ehome.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @ClassName TimeCompareUtil
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/7 10:05
 * @Version 1.0
 */
@Component
public class TimeUtil {
    private static String DATE_FORMAT;
    private static String TIME_FORMAT;
    private static String TIME_ZONE;

    public TimeUtil(
            @Value("${spring.datasource.server-time-zone}") String timeZone,
            @Value("${spring.datasource.server-date-format}") String dateFormat,
            @Value("${spring.datasource.server-time-format}") String timeFormat) {
        TimeUtil.TIME_ZONE = timeZone;
        TimeUtil.DATE_FORMAT = dateFormat;
        TimeUtil.TIME_FORMAT = timeFormat;
    }

    public static Integer daysCount(Timestamp timestamp) {
        // 将 Timestamp 转换为 LocalDate
        LocalDate dateFromTimestamp = timestamp.toLocalDateTime().toLocalDate();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now(ZoneId.of(TIME_ZONE));
        // 计算给定日期和当前日期之间的天数差异
        return (int) ChronoUnit.DAYS.between(dateFromTimestamp, currentDate);
    }

    public static String formatDate(Timestamp timestamp) {
        // 格式化日期
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return DateTimeFormatter.ofPattern(DATE_FORMAT).format(localDateTime);
    }

    public static String formatTime(Timestamp timestamp) {
        // 格式化时间
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return DateTimeFormatter.ofPattern(TIME_FORMAT).format(localDateTime);
    }
}
