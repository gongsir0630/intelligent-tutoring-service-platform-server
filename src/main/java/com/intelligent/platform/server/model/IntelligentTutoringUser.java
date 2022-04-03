package com.intelligent.platform.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-03
 */
@Data
@TableName("intelligent_tutoring_user")
public class IntelligentTutoringUser {

    @TableId(type = IdType.INPUT)
    private String username;

    private String password;

    private String name;

    private String avatar;

    private String introduction;

    private String role;

    private String phone;

    @TableField(exist = false)
    private List<String> roles;
}
