package com.intelligent.platform.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.platform.server.model.IntelligentTutoringCourse;
import com.intelligent.platform.server.param.IntelligentTutoringCourseBookParam;
import com.intelligent.platform.server.param.IntelligentTutoringCourseUpdateParam;
import com.intelligent.platform.server.vo.IntelligentTutoringCourseVO;

import java.util.List;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-23
 */
public interface IntelligentTutoringCourseService extends IService<IntelligentTutoringCourse> {

    /**
     * 学生预约
     *
     * @param param 预约信息
     */
    void book(IntelligentTutoringCourseBookParam param);

    /**
     * 查询课程信息, 不同 role 返回不同的课程列表
     * @param username 用户名
     * @return 课程信息列表
     */
    List<IntelligentTutoringCourseVO> queryCourseListByUsername(String username);

    /**
     * 更新并且发送消息
     * @param param 课程状态 & 消息信息
     */
    void updateAndSendMessage(IntelligentTutoringCourseUpdateParam param);

    /**
     * 根据 id 查询课程详情
     * @param courseId 课程id
     * @return 课程详情
     */
    IntelligentTutoringCourseVO queryById(Long courseId);
}
