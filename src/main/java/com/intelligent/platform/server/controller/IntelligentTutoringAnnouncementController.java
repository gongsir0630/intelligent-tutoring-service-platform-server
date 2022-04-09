package com.intelligent.platform.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.intelligent.platform.server.annotation.SysUserName;
import com.intelligent.platform.server.param.IntelligentTutoringAnnouncementParam;
import com.intelligent.platform.server.service.IntelligentTutoringAnnouncementService;
import com.intelligent.platform.server.vo.IntelligentTutoringAnnouncementVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-03
 */
@RestController
@RequestMapping("/intelligent-tutoring-service-platform/announcement")
public class IntelligentTutoringAnnouncementController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IntelligentTutoringAnnouncementService intelligentTutoringAnnouncementService;

    @Autowired
    public IntelligentTutoringAnnouncementController(IntelligentTutoringAnnouncementService intelligentTutoringAnnouncementService) {
        this.intelligentTutoringAnnouncementService = intelligentTutoringAnnouncementService;
    }

    @GetMapping("/list")
    public Map<String, Object> listAll() {
        List<IntelligentTutoringAnnouncementVO> list = intelligentTutoringAnnouncementService.listAll();
        return ImmutableMap.of(
                "code", 20000,
                "data", list
        );
    }

    @PostMapping("/save")
    public Map<String, Object> saveOrUpdate(@RequestBody IntelligentTutoringAnnouncementParam param,
                                            @SysUserName String username) {
        logger.info("saveOrUpdate, param===>{}, username===>{}", JSON.toJSONString(param), username);
        intelligentTutoringAnnouncementService.saveOrUpdate(param.buildAnnouncement(username));
        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody IntelligentTutoringAnnouncementParam param,
                                            @SysUserName String username) {
        logger.info("delete, param===>{}, username===>{}", JSON.toJSONString(param), username);
        intelligentTutoringAnnouncementService.removeById(param.getId());
        return ImmutableMap.of(
                "code", 20000,
                "data", "success"
        );
    }
}
