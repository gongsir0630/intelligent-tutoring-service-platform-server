package com.intelligent.platform.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息
 *
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("intelligent_tutoring_user")
public class IntelligentTutoringUser {

    /**
     * 用户名, 主键
     */
    @TableId(type = IdType.INPUT)
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
     * @see com.intelligent.platform.server.enums.IntelligentTutoringUserRoleEnum
     */
    private String role;

    @TableField(exist = false)
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
    private Long birthday;

    // ========== 学生信息 ==========
    /**
     * 学校
     */
    private String school;

    /**
     * 年级
     * @see com.intelligent.platform.server.enums.IntelligentTutoringGradeEnum
     */
    private String grade;

    // ========== 老师信息 ==========
    /**
     * 校区
     */
    private String campus;

    /**
     * 入职时间
     */
    @TableField("entry_time")
    private Long entryTime;

    /**
     * 教授学科
     */
    private String subject;

    /**
     * 风采展示
     */
    private String display;
}
