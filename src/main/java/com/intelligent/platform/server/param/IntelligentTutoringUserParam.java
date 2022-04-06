package com.intelligent.platform.server.param;

import com.intelligent.platform.server.model.IntelligentTutoringUser;
import com.intelligent.platform.server.utils.IntelligentTutoringDateUtil;
import lombok.Data;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-05
 */
@Data
public class IntelligentTutoringUserParam {
    // 用户基本信息

    private String username;
    private String password;
    private String name;
    private String avatar;
    private String introduction;
    private String role;
    private String phone;
    private String birthday;

    // 学生信息

    private String school;
    private String grade;

    // 老师信息

    private String campus;
    private String entryTime;
    private String subject;
    private String display;

    public IntelligentTutoringUser buildUser() {
        return IntelligentTutoringUser.builder()
                .username(this.username)
                .avatar(this.avatar)
                .birthday(IntelligentTutoringDateUtil.strToLong(this.birthday))
                .introduction(this.introduction)
                .name(this.name)
                .password(this.password)
                .phone(this.phone)
                .role(this.role)
                // 学生
                .school(this.school)
                .grade(this.grade)
                // 老师
                .subject(this.subject)
                .campus(this.campus)
                // 日期转时间戳
                .entryTime(IntelligentTutoringDateUtil.strToLong(this.entryTime))
                .display(this.display)
                .build();
    }
}
