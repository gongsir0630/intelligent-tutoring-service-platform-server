package com.intelligent.platform.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentTutoringCourseVO {

    /**
     * id
     */
    private Long id;

    /**
     * 学生
     */
    private IntelligentTutoringUserVO student;

    /**
     * 老师
     */
    private IntelligentTutoringUserVO teacher;

    /**
     * 分数
     */
    private String score;

    /**
     * 课余量
     */
    private Integer courseAllowance;

    /**
     * 状态 code & desc
     */
    private String courseState;
    private String courseStateDesc;

    /**
     * 所有聊天记录
     */
    private List<IntelligentTutoringMessageVO> messageList;
}
