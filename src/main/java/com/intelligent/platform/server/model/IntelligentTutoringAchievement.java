package com.intelligent.platform.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.intelligent.platform.server.vo.IntelligentTutoringCourseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-05-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("intelligent_tutoring_achievement")
public class IntelligentTutoringAchievement {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("course_id")
    private Long courseId;

    @TableField(exist = false)
    private IntelligentTutoringCourseVO courseVO;

    private String score0;
    private String score1;
    private String score2;
    private String score3;
    private String score4;
}
