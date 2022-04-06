package com.intelligent.platform.server.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-05
 */
public enum IntelligentTutoringUserRoleEnum {
    /**
     * 管理员
     */
    ADMIN("admin", "管理员"),
    /**
     * 学生
     */
    STUDENT("student", "学生"),
    /**
     * 老师
     */
    TEACHER("teacher", "老师")
    ;

    private final String code;
    private final String desc;

    IntelligentTutoringUserRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static IntelligentTutoringUserRoleEnum parseFromCode(final String code) {
        return Arrays.stream(values())
                .filter(k -> StringUtils.equals(k.getCode(), code)).findFirst().orElse(null);
    }
}
