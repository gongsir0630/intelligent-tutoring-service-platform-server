package com.intelligent.platform.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("intelligent_tutoring_course")
public class IntelligentTutoringCourse {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生
     */
    @TableField("student_username")
    private String studentUsername;

    /**
     * 老师
     */
    @TableField("teacher_username")
    private String teacherUsername;

    /**
     * 分数
     */
    @TableField("initial_score")
    private String initialScore;

    /**
     * 课余量
     */
    @TableField("course_allowance")
    private Integer courseAllowance;

    /**
     * 状态
     *
     * @see com.intelligent.platform.server.enums.IntelligentTutoringCourseStateEnum
     */
    @TableField("course_state")
    private String courseState;
}
