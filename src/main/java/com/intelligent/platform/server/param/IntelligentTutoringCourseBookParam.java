package com.intelligent.platform.server.param;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-10
 */
@Data
public class IntelligentTutoringCourseBookParam {

    /**
     * 学生用户名
     */
    private String username;

    /**
     * 老师
     */
    private String teacherUsername;

    /**
     * 分数
     */
    private String score;

    /**
     * 留言内容
     */
    private String message;

    public void checkParam() {
        if (StringUtils.isBlank(message)) {
            // 暂时设置默认消息, 确保 rootMessage 存在
            this.message = "老师您好, 我预约了您的课程";
        }
    }
}
