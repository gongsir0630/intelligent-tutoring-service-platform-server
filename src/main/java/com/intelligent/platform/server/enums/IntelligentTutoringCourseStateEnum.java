package com.intelligent.platform.server.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-10
 */
public enum IntelligentTutoringCourseStateEnum {

    /**
     * unknown
     */
    UNKNOWN("unknown", ""),

    /**
     * 预约中
     */
    BOOKING("booking", "预约中"),

    /**
     * 预约成功
     */
    BOOK_SUCCESS("bookSuccess", "预约成功"),

    /**
     * 课程中
     */
    IN_COURSE("inCourse", "课程中"),

    /**
     * 课程结束
     */
    FINISH_COURSE("finishCourse", "课程结束")
    ;

    private final String code;
    private final String desc;

    public static String code2Desc(final String code) {
        return Arrays.stream(values())
                .filter(k -> StringUtils.equals(k.getCode(), code)).findFirst().orElse(UNKNOWN).getDesc();
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    IntelligentTutoringCourseStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
