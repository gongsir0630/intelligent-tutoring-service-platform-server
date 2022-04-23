package com.intelligent.platform.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.intelligent.platform.server.dao.IntelligentTutoringCourseDAO;
import com.intelligent.platform.server.enums.IntelligentTutoringCourseStateEnum;
import com.intelligent.platform.server.enums.IntelligentTutoringUserRoleEnum;
import com.intelligent.platform.server.model.IntelligentTutoringCourse;
import com.intelligent.platform.server.service.IntelligentTutoringCourseService;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import com.intelligent.platform.server.utils.IntelligentTutoringMailUtils;
import com.intelligent.platform.server.vo.IntelligentTutoringCourseVO;
import com.intelligent.platform.server.vo.IntelligentTutoringUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-23
 */
@Slf4j
@Service
public class IntelligentTutoringCourseServiceImpl extends ServiceImpl<IntelligentTutoringCourseDAO, IntelligentTutoringCourse>
        implements IntelligentTutoringCourseService {

    private final IntelligentTutoringUserService userService;

    public IntelligentTutoringCourseServiceImpl(IntelligentTutoringUserService userService) {
        this.userService = userService;
    }

    @Override
    public void book(IntelligentTutoringCourse course) {
        save(course);
        // TODO: 2022/4/23 新增留言信息, 通知老师学生预约了他的课程信息
        // 邮件通知
        CompletableFuture.runAsync(() -> {
            IntelligentTutoringUserVO teacher = userService.queryByUsername(course.getTeacherUsername());
            if (Objects.isNull(teacher) || StringUtils.isBlank(teacher.getMail())) {
                log.info("老师不存在或者邮箱为空, teacher===>{}", JSON.toJSONString(teacher));
            }
            String contentHtml = String.format("<p>尊敬的<font color='red'>%s</font>老师, 您有一条新的课程预约信息, 请尽快登录平台进行处理</p>", teacher.getName());
            IntelligentTutoringMailUtils.MailContent content = IntelligentTutoringMailUtils.MailContent.builder()
                    .to(teacher.getMail())
                    .subject("课程预约通知")
                    .content(contentHtml)
                    .isHtml(true)
                    .build();
            IntelligentTutoringMailUtils.send(content);
        });
    }

    @Override
    public List<IntelligentTutoringCourseVO> queryCourseListByUsername(String username) {
        IntelligentTutoringUserVO user = userService.queryByUsername(username);
        if (Objects.isNull(user)) {
            return Collections.emptyList();
        }

        List<IntelligentTutoringCourse> courseList = Lists.newArrayList();
        IntelligentTutoringUserRoleEnum userRoleEnum = IntelligentTutoringUserRoleEnum.parseFromCode(user.getRole());
        switch (userRoleEnum) {
            case ADMIN:
                courseList = list();
                break;
            case STUDENT:
                courseList = lambdaQuery().eq(IntelligentTutoringCourse::getStudentUsername, username).list();
                break;
            case TEACHER:
                courseList = lambdaQuery().eq(IntelligentTutoringCourse::getTeacherUsername, username).list();
                break;
            default:
                break;
        }
        return ListUtils.emptyIfNull(courseList).parallelStream().map(this::buildVO).collect(Collectors.toList());
    }

    private IntelligentTutoringCourseVO buildVO(IntelligentTutoringCourse course) {
        return IntelligentTutoringCourseVO.builder()
                .id(course.getId())
                .student(userService.queryByUsername(course.getStudentUsername()))
                .teacher(userService.queryByUsername(course.getTeacherUsername()))
                .score(course.getInitialScore())
                .courseAllowance(course.getCourseAllowance())
                .courseState(course.getCourseState())
                .courseStateDesc(IntelligentTutoringCourseStateEnum.code2Desc(course.getCourseState()))
                .build();
    }

    @Override
    public boolean updateById(IntelligentTutoringCourse entity) {
        boolean update = super.updateById(entity);
        // 邮件通知
        CompletableFuture.runAsync(() -> {
            IntelligentTutoringUserVO student = userService.queryByUsername(entity.getStudentUsername());
            if (Objects.isNull(student) || StringUtils.isBlank(student.getMail())) {
                log.info("学生不存在或者邮箱为空, teacher===>{}", JSON.toJSONString(student));
            }
            String contentHtml = String.format("<p>亲爱的<font color='red'>%s</font>同学, 您有一条新的课程预约信息, 请尽快登录平台进行处理</p>", student.getName());
            IntelligentTutoringMailUtils.MailContent content = IntelligentTutoringMailUtils.MailContent.builder()
                    .to(student.getMail())
                    .subject("课程预约通知")
                    .content(contentHtml)
                    .isHtml(true)
                    .build();
            IntelligentTutoringMailUtils.send(content);
            log.info("邮箱发送成功, send to===>{}", student.getMail());
        });
        return update;
    }
}
