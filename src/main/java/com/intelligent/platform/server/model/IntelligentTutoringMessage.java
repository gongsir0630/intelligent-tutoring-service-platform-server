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
 * 留言信息
 *
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-05-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("intelligent_tutoring_message")
public class IntelligentTutoringMessage {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发信人
     */
    @TableField("from_user")
    private String fromUser;

    /**
     * 收信人
     */
    @TableField("to_user")
    private String toUser;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 收信人是否已读
     */
    @TableField("to_see_state")
    private Boolean toSeeState;

    /**
     * 课程id, 用于查询一次课程预约的所有会话消息
     */
    @TableField("course_id")
    private Long courseId;
}
