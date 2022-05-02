package com.intelligent.platform.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.intelligent.platform.server.annotation.SysUserName;
import com.intelligent.platform.server.model.IntelligentTutoringCourse;
import com.intelligent.platform.server.param.IntelligentTutoringCourseBookParam;
import com.intelligent.platform.server.param.IntelligentTutoringCourseUpdateParam;
import com.intelligent.platform.server.service.IntelligentTutoringCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-23
 */
@RestController
@RequestMapping("/intelligent-tutoring-service-platform/course")
public class IntelligentTutoringCourseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IntelligentTutoringCourseService intelligentTutoringCourseService;

    public IntelligentTutoringCourseController(IntelligentTutoringCourseService intelligentTutoringCourseService) {
        this.intelligentTutoringCourseService = intelligentTutoringCourseService;
    }

    @PostMapping("/book")
    public Map<String, Object> book(@RequestBody IntelligentTutoringCourseBookParam param,
                                    @SysUserName String username) {
        logger.info("学生提交预约信息, param===>{}, username===>{}", JSON.toJSONString(param), username);
        param.checkParam();
        param.setUsername(username);
        intelligentTutoringCourseService.book(param);

        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }

    @GetMapping("/list")
    public Map<String, Object> list(@SysUserName String username) {
        return ImmutableMap.of(
                "code", 20000,
                "data", intelligentTutoringCourseService.queryCourseListByUsername(username)
        );
    }

    @PostMapping("/update")
    public Map<String, Object> list(@RequestBody IntelligentTutoringCourseUpdateParam param,
                                    @SysUserName String username) {
        IntelligentTutoringCourse course = intelligentTutoringCourseService.getById(param.getId());
        if (Objects.isNull(course)) {
            return ImmutableMap.of(
                    "code", 20001,
                    "data", "id不正确"
            );
        }
        intelligentTutoringCourseService.updateAndSendMessage(param);
        return ImmutableMap.of(
                "code", 20000,
                "data", intelligentTutoringCourseService.queryCourseListByUsername(username)
        );
    }
}
