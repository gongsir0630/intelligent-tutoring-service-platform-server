package com.intelligent.platform.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.intelligent.platform.server.annotation.SysUserName;
import com.intelligent.platform.server.model.IntelligentTutoringMessage;
import com.intelligent.platform.server.service.IntelligentTutoringMessageService;
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
@RequestMapping("/intelligent-tutoring-service-platform/message")
public class IntelligentTutoringMessageController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IntelligentTutoringMessageService intelligentTutoringMessageService;

    public IntelligentTutoringMessageController(IntelligentTutoringMessageService intelligentTutoringMessageService) {
        this.intelligentTutoringMessageService = intelligentTutoringMessageService;
    }

    @GetMapping("/list")
    public Map<String, Object> list(@SysUserName String to) {
        return ImmutableMap.of(
                "code", 20000,
                "data", intelligentTutoringMessageService.queryMessageListByTo(to)
        );
    }

    @PostMapping("/read")
    public Map<String, Object> markRead(@RequestBody JSONObject param) {
        logger.info("markRead, param===>{}", JSON.toJSONString(param));
        long id = param.getLongValue("id");
        IntelligentTutoringMessage message = intelligentTutoringMessageService.getById(id);
        if (Objects.isNull(message)) {
            return ImmutableMap.of(
                    "code", 20001,
                    "data", "message is not exist!"
            );
        }
        // 标记已读
        message.setToSeeState(Boolean.TRUE);
        // 更新
        intelligentTutoringMessageService.updateById(message);
        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }

    @PostMapping("/replay")
    public Map<String, Object> replayMessage(@RequestBody JSONObject param) {
        logger.info("replayMessage, param===>{}", JSON.toJSONString(param));
        long messageId = param.getLongValue("id");
        String messageStr = param.getString("message");
        IntelligentTutoringMessage parent = intelligentTutoringMessageService.getById(messageId);
        if (Objects.isNull(parent)) {
            return ImmutableMap.of(
                    "code", 20001,
                    "data", "message is not exist!"
            );
        }
        IntelligentTutoringMessage message = new IntelligentTutoringMessage();
        // 身份调转
        message.setFromUser(parent.getToUser());
        message.setToUser(parent.getFromUser());
        message.setContent(messageStr);
        message.setToSeeState(Boolean.FALSE);
        message.setCreateTime(System.currentTimeMillis());
        message.setCourseId(parent.getCourseId());
        intelligentTutoringMessageService.save(message);
        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }
}
