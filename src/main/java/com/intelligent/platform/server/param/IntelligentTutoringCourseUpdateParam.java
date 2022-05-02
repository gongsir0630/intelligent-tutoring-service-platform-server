package com.intelligent.platform.server.param;

import lombok.Data;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-10
 */
@Data
public class IntelligentTutoringCourseUpdateParam {

    private Long id;

    /**
     * 课余量
     */
    private Integer courseAllowance;

    /**
     * 状态
     */
    private String courseState;

    /**
     * 回复消息
     */
    private String message;
}
