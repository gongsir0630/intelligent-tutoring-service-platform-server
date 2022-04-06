package com.intelligent.platform.server.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-05
 */
public class IntelligentTutoringDateUtil {

    public static final String HX_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 时间戳转字符转
     */
    public static String longToStr(Long timestamp) {
        if (timestamp == null) {
            return StringUtils.EMPTY;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HX_DATE_FORMAT);
        return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
    }

    /**
     * 字符串转时间戳
     */
    public static Long strToLong(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HX_DATE_FORMAT);
        LocalDateTime localDateTime = LocalDate.parse(date, formatter).atStartOfDay();
        return LocalDateTime.from(localDateTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
