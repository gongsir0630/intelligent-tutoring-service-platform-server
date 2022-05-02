package com.intelligent.platform.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.intelligent.platform.server.dao.IntelligentTutoringCourseDAO;
import com.intelligent.platform.server.enums.IntelligentTutoringCourseStateEnum;
import com.intelligent.platform.server.enums.IntelligentTutoringUserRoleEnum;
import com.intelligent.platform.server.model.IntelligentTutoringCourse;
import com.intelligent.platform.server.model.IntelligentTutoringMessage;
import com.intelligent.platform.server.param.IntelligentTutoringCourseBookParam;
import com.intelligent.platform.server.param.IntelligentTutoringCourseUpdateParam;
import com.intelligent.platform.server.service.IntelligentTutoringCourseService;
import com.intelligent.platform.server.service.IntelligentTutoringMessageService;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import com.intelligent.platform.server.utils.IntelligentTutoringMailUtils;
import com.intelligent.platform.server.vo.IntelligentTutoringCourseVO;
import com.intelligent.platform.server.vo.IntelligentTutoringUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
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
    private final IntelligentTutoringMessageService messageService;

    public IntelligentTutoringCourseServiceImpl(IntelligentTutoringUserService userService,
                                                IntelligentTutoringMessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @Override
    public void book(IntelligentTutoringCourseBookParam param) {
        String username = param.getUsername();
        // 先记录课程预约信息
        IntelligentTutoringCourse course = IntelligentTutoringCourse.builder()
                .studentUsername(username)
                .teacherUsername(param.getTeacherUsername())
                .initialScore(param.getScore())
                .courseState(IntelligentTutoringCourseStateEnum.BOOKING.getCode())
                .build();
        save(course);
        // 生成的课程id
        Long courseId = course.getId();

        // 消息绑定到课程上
        saveMessage(param, courseId);

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

    private void saveMessage(IntelligentTutoringCourseBookParam param, Long courseId) {
        // 后台留言给老师
        IntelligentTutoringMessage message = new IntelligentTutoringMessage();
        message.setFromUser(param.getUsername());
        message.setToUser(param.getTeacherUsername());
        message.setContent(param.getMessage());
        message.setToSeeState(Boolean.FALSE);
        message.setCreateTime(System.currentTimeMillis());
        message.setCourseId(courseId);
        messageService.save(message);
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
        return ListUtils.emptyIfNull(courseList).parallelStream().map(this::buildVO)
                .sorted(Comparator.comparing(IntelligentTutoringCourseVO::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void updateAndSendMessage(IntelligentTutoringCourseUpdateParam param) {
        // 更新课程信息 & 发送邮件通知
        IntelligentTutoringCourse course = getById(param.getId());
        course.setCourseAllowance(param.getCourseAllowance());
        course.setCourseState(param.getCourseState());
        updateById(course);

        // 给学生发消息
        if (StringUtils.isNotBlank(param.getMessage())) {
            IntelligentTutoringMessage message = new IntelligentTutoringMessage();
            message.setFromUser(course.getTeacherUsername());
            message.setToUser(course.getStudentUsername());
            message.setContent(param.getMessage());
            message.setToSeeState(Boolean.FALSE);
            message.setCreateTime(System.currentTimeMillis());
            message.setCourseId(course.getId());
            messageService.save(message);
        }

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
                .messageList(messageService.queryAllMessageByCourseId(course.getId()))
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
