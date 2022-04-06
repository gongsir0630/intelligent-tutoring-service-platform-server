package com.intelligent.platform.server.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-05
 */
public enum IntelligentTutoringGradeEnum {
    /**
     * 默认
     */
    UNKNOWN("unknown", ""),
    /**
     * 一年级
     */
    FIRST_GRADE("firstGrade", "一年级"),
    /**
     * 二年级
     */
    SECOND_GRADE("secondGrade", "二年级"),
    /**
     * 三年级
     */
    THIRD_GRADE("thirdGrade", "三年级"),
    /**
     * 四年级
     */
    FOURTH_GRADE("fourthGrade", "四年级"),
    /**
     * 五年级
     */
    FIFTH_GRADE("fifthGrade", "五年级"),
    /**
     * 六年级
     */
    SIXTH_GRADE("sixthGrade", "六年级"),
    /**
     * 七年级
     */
    SEVENTH_GRADE("seventhGrade", "七年级"),
    /**
     * 八年级
     */
    EIGHTH_GRADE("eighthGrade", "八年级"),
    /**
     * 九年级
     */
    NINTH_GRADE("ninthGrade", "九年级"),
    /**
     * 高一
     */
    SENIOR_ONE("seniorOne", "高一"),
    /**
     * 高二
     */
    SECOND_YEAR("secondYear", "高二"),
    /**
     * 高三
     */
    SENIOR_YEAR("seniorYear", "高三")
    ;

    private final String code;
    private final String desc;

    IntelligentTutoringGradeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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
}
