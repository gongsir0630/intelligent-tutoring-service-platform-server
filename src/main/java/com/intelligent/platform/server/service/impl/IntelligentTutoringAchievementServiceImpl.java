package com.intelligent.platform.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.platform.server.dao.IntelligentTutoringAchievementDAO;
import com.intelligent.platform.server.model.IntelligentTutoringAchievement;
import com.intelligent.platform.server.service.IntelligentTutoringAchievementService;
import com.intelligent.platform.server.service.IntelligentTutoringCourseService;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import com.intelligent.platform.server.vo.IntelligentTutoringCourseVO;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-05-03
 */
@Service
public class IntelligentTutoringAchievementServiceImpl extends ServiceImpl<IntelligentTutoringAchievementDAO, IntelligentTutoringAchievement>
        implements IntelligentTutoringAchievementService {

    private final IntelligentTutoringUserService userService;
    private final IntelligentTutoringCourseService courseService;

    public IntelligentTutoringAchievementServiceImpl(IntelligentTutoringUserService userService, IntelligentTutoringCourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    /**
     * 查询所有教学成果
     *
     * @param teacherUsername 教师用户名
     * @return 当前老师的所有教学成果
     */
    @Override
    public List<IntelligentTutoringAchievement> listAll(String teacherUsername) {
        // 查询该老师的所有课程列表
        List<Long> courseIdList = courseService.queryCourseListByUsername(teacherUsername).stream()
                .map(IntelligentTutoringCourseVO::getId).collect(Collectors.toList());
        List<IntelligentTutoringAchievement> achievements = ListUtils.emptyIfNull(lambdaQuery().in(IntelligentTutoringAchievement::getCourseId,
                courseIdList).orderByDesc(IntelligentTutoringAchievement::getId).list());
        achievements.forEach(this::generateVoFields);
        return achievements;
    }

    private void generateVoFields(IntelligentTutoringAchievement achievement) {
        IntelligentTutoringCourseVO courseVO = courseService.queryById(achievement.getCourseId());
        achievement.setCourseVO(courseVO);
    }
}
