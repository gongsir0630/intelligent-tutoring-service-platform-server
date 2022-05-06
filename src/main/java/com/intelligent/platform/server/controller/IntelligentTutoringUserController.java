package com.intelligent.platform.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.intelligent.platform.server.enums.IntelligentTutoringGradeEnum;
import com.intelligent.platform.server.enums.IntelligentTutoringSubjectEnum;
import com.intelligent.platform.server.model.IntelligentTutoringUser;
import com.intelligent.platform.server.param.IntelligentTutoringUserParam;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import com.intelligent.platform.server.vo.IntelligentTutoringGradeVO;
import com.intelligent.platform.server.vo.IntelligentTutoringSubjectVO;
import com.intelligent.platform.server.vo.IntelligentTutoringUserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-03
 */
@RestController
@RequestMapping("/vue-admin-template/user")
public class IntelligentTutoringUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IntelligentTutoringUserService intelligentTutoringUserService;

    @Autowired
    public IntelligentTutoringUserController(IntelligentTutoringUserService intelligentTutoringUserService) {
        this.intelligentTutoringUserService = intelligentTutoringUserService;
    }

    @PostMapping("/login")
    public Map<Object, Object> login(@RequestBody IntelligentTutoringUser user) {

        logger.info("login, username===>{}, password===>{}", user.getUsername(), user.getPassword());

        // 从数据库查询用户信息
        IntelligentTutoringUser tutoringUser = intelligentTutoringUserService.getById(user.getUsername());

        if (Objects.isNull(tutoringUser)) {
            logger.info("login, 未查询到用户信息, username===>{}", user.getUsername());
            return ImmutableMap.of(
                    "code", 60204,
                    "message", "Account and password are incorrect."
            );
        }

        if (!StringUtils.equals(user.getPassword(), tutoringUser.getPassword())) {
            logger.info("login, 用户登录失败_密码错误, 用户输入的密码===>{}, 数据库密码===>{}",
                    user.getPassword(), tutoringUser.getPassword());
            return ImmutableMap.of(
                    "code", 60204,
                    "message", "Account and password are incorrect."
            );
        }

        logger.info("login, 用户登录成功, 用户信息===>{}", JSON.toJSONString(tutoringUser));

        return ImmutableMap.of(
                "code", 20000,
                "data", ImmutableMap.of("token", tutoringUser.getUsername())
        );
    }

    @GetMapping("/info")
    public Map<String, Object> getUserInfo(String token) {
        logger.info("getUserInfo, token===>{}", token);
        IntelligentTutoringUserVO user = intelligentTutoringUserService.queryByUsername(token);

        if (Objects.isNull(user)) {
            return ImmutableMap.of(
                    "code", 50008,
                    "message", "Login failed, unable to get user details."
            );
        }

        return ImmutableMap.of(
                "code", 20000,
                "data", user
        );
    }

    @PostMapping("/logout")
    public Map<String, Object> logout() {
        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody IntelligentTutoringUserParam param) {
        logger.info("register, 用户提交注册信息, param===>{}", JSON.toJSONString(param));

        IntelligentTutoringUser tutoringUser = intelligentTutoringUserService.getById(param.getUsername());
        if (Objects.nonNull(tutoringUser)) {
            return ImmutableMap.of(
                    "code", 20001,
                    "message", "账号已被注册, 试试别的账号吧~");
        }
        boolean save = intelligentTutoringUserService.save(param.buildUser());
        tutoringUser = intelligentTutoringUserService.getById(param.getUsername());
        logger.info("注册用户信息, save===>{}", save);
        return ImmutableMap.of(
                "code", 20000,
                "data", ImmutableMap.of("token", tutoringUser.getUsername())
        );
    }

    @PostMapping("/list")
    public Map<String, Object> getUserList(@RequestBody String param) {
        logger.info("getUserList, 查询用户信息列表, param===>{}", JSON.toJSONString(param));

        List<IntelligentTutoringUserVO> userList = intelligentTutoringUserService.listAll();
        return ImmutableMap.of(
                "code", 20000,
                "data", userList
        );
    }

    @GetMapping("/grade")
    public Map<String, Object> getGradeList() {
        // 流式处理
        List<IntelligentTutoringGradeVO> gradeList = Arrays.stream(IntelligentTutoringGradeEnum.values()).map(k -> IntelligentTutoringGradeVO
                .builder().value(k.getCode()).label(k.getDesc()).build()).filter(k -> StringUtils.isNotBlank(k.getLabel())).collect(Collectors.toList());
        return ImmutableMap.of(
                "code", 20000,
                "data", gradeList
        );
    }

    @GetMapping("/subject")
    public Map<String, Object> getSubjectList() {
        // 流式处理
        List<IntelligentTutoringSubjectVO> subjectList = Arrays.stream(IntelligentTutoringSubjectEnum.values()).map(k -> IntelligentTutoringSubjectVO
                .builder().value(k.getCode()).label(k.getDesc()).build()).filter(k -> StringUtils.isNotBlank(k.getLabel())).collect(Collectors.toList());
        return ImmutableMap.of(
                "code", 20000,
                "data", subjectList
        );
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody IntelligentTutoringUserParam param) {
        logger.info("update, param===>{}", JSON.toJSONString(param));
        intelligentTutoringUserService.updateById(param.buildUser());
        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteUser(@RequestBody IntelligentTutoringUserParam param) {
        logger.info("deleteUser, param===>{}", JSON.toJSONString(param));
        intelligentTutoringUserService.removeById(param.getUsername());
        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }
}
