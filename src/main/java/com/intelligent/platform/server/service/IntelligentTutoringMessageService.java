package com.intelligent.platform.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.platform.server.model.IntelligentTutoringMessage;
import com.intelligent.platform.server.vo.IntelligentTutoringMessageVO;

import java.util.List;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-05-01
 */
public interface IntelligentTutoringMessageService extends IService<IntelligentTutoringMessage> {


    /**
     * 查询消息列表
     *
     * @param to 收信人
     * @return 所有消息, 包含已读和未读
     */
    List<IntelligentTutoringMessageVO> queryMessageListByTo(String to);

    /**
     * 查询某一门课程信息的全部会话消息, 按时间倒序排列
     * @param courseId 课程id
     * @return 所有会话消息
     */
    List<IntelligentTutoringMessageVO> queryAllMessageByCourseId(Long courseId);
}
