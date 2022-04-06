package com.intelligent.platform.server.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-05
 */
public enum IntelligentTutoringSubjectEnum {
    /**
     * 默认
     */
    UNKNOWN("unknown", ""),
    /**
     * 语文
     */
    CHINESE("chinese", "语文"),
    /**
     * 数学
     */
    MATH("math", "数据"),
    /**
     * 英语
     */
    ENGLISH("english", "英语"),
    /**
     * 物理
     */
    PHYSICAL("physical", "物理"),
    /**
     * 化学
     */
    CHEMICAL("chemical", "化学"),
    /**
     * 生物
     */
    BIOLOGICAL("biological", "生物"),
    /**
     * 政治
     */
    POLITICAL("political", "政治"),
    /**
     * 历史
     */
    HISTORY("history", "历史"),
    /**
     * 地理
     */
    GEOGRAPHIC("geographic", "地理"),
    ;

    private final String code;
    private final String desc;

    IntelligentTutoringSubjectEnum(String code, String desc) {
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
