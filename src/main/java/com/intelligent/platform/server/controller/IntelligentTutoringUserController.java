package com.intelligent.platform.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.intelligent.platform.server.model.IntelligentTutoringUser;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

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
        IntelligentTutoringUser user = intelligentTutoringUserService.getById(token);

        if (Objects.isNull(user)) {
            return ImmutableMap.of(
                    "code", 50008,
                    "message", "Login failed, unable to get user details."
            );
        }

        // 将 role >> roles : admin >> ['admin']
        user.setRoles(Collections.singletonList(user.getRole()));

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
    public Map<String, Object> register(@RequestBody IntelligentTutoringUser user) {
        logger.info("register, 用户提交注册信息, user===>{}", JSON.toJSONString(user));

        IntelligentTutoringUser tutoringUser = intelligentTutoringUserService.getById(user.getUsername());
        if (Objects.isNull(tutoringUser)) {
            boolean save = intelligentTutoringUserService.save(user);
            tutoringUser = intelligentTutoringUserService.getById(user.getUsername());
            logger.info("注册用户信息, save===>{}", save);
        }
        return ImmutableMap.of(
                "code", 20000,
                "data", ImmutableMap.of("token", tutoringUser.getUsername())
        );
    }

}
