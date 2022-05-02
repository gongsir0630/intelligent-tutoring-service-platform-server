package com.intelligent.platform.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.platform.server.dao.IntelligentTutoringMessageDAO;
import com.intelligent.platform.server.model.IntelligentTutoringMessage;
import com.intelligent.platform.server.service.IntelligentTutoringMessageService;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import com.intelligent.platform.server.utils.IntelligentTutoringDateUtil;
import com.intelligent.platform.server.vo.IntelligentTutoringMessageVO;
import com.intelligent.platform.server.vo.IntelligentTutoringUserVO;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-05-01
 */
@Service
public class IntelligentTutoringMessageServiceImpl extends ServiceImpl<IntelligentTutoringMessageDAO, IntelligentTutoringMessage>
        implements IntelligentTutoringMessageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IntelligentTutoringUserService intelligentTutoringUserService;

    public IntelligentTutoringMessageServiceImpl(IntelligentTutoringUserService intelligentTutoringUserService) {
        this.intelligentTutoringUserService = intelligentTutoringUserService;
    }

    @Override
    public List<IntelligentTutoringMessage> list() {
        return super.list();
    }

    @Override
    public List<IntelligentTutoringMessageVO> queryMessageListByTo(String to) {
        return ListUtils.emptyIfNull(lambdaQuery().eq(IntelligentTutoringMessage::getToUser, to).list())
                .stream().map(this::buildVO).sorted(Comparator.comparing(IntelligentTutoringMessage::getCreateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<IntelligentTutoringMessageVO> queryAllMessageByCourseId(Long courseId) {
        return ListUtils.emptyIfNull(lambdaQuery().eq(IntelligentTutoringMessage::getCourseId, courseId).list())
                .stream().map(this::buildVO).sorted(Comparator.comparing(IntelligentTutoringMessage::getCreateTime).reversed())
                .collect(Collectors.toList());
    }

    public IntelligentTutoringMessageVO buildVO(IntelligentTutoringMessage message) {
        IntelligentTutoringMessageVO vo = new IntelligentTutoringMessageVO();
        vo.setId(message.getId());
        vo.setFromUser(message.getFromUser());
        vo.setToUser(message.getToUser());
        vo.setContent(message.getContent());
        vo.setCreateTime(message.getCreateTime());
        vo.setToSeeState(message.getToSeeState());
        vo.setCourseId(message.getCourseId());

        // 查询用户信息
        IntelligentTutoringUserVO from = intelligentTutoringUserService.queryByUsername(message.getFromUser());
        IntelligentTutoringUserVO to = intelligentTutoringUserService.queryByUsername(message.getToUser());
        vo.setFrom(Objects.nonNull(from) ? from.getName() : message.getFromUser());
        vo.setTo(Objects.nonNull(to) ? to.getName() : message.getToUser());
        // 日期
        vo.setCreateTimeStr(IntelligentTutoringDateUtil.longToStr(message.getCreateTime()));
        return vo;
    }
}
