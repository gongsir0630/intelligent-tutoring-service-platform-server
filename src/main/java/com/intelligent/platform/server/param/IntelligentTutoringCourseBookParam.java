package com.intelligent.platform.server.param;

import lombok.Data;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-10
 */
@Data
public class IntelligentTutoringCourseBookParam {

    /**
     * 老师
     */
    private String teacherUsername;

    /**
     * 分数
     */
    private String score;
}
