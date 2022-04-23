package com.intelligent.platform.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentTutoringUserVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 自我介绍
     */
    private String introduction;

    /**
     * 角色信息
     */
    private String role;
    private List<String> roles;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 生日
     */
    private String birthday;

    // 学生信息

    /**
     * 学校
     */
    private String school;

    /**
     * 年级
     */
    private String grade;
    private String gradeLabel;

    // 老师信息

    /**
     * 校区
     */
    private String campus;

    /**
     * 入职时间
     */
    private String entryTime;

    /**
     * 教授学科
     */
    private String subject;
    private String subjectLabel;

    /**
     * 风采展示
     */
    private String display;
}
