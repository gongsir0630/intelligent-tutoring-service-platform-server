package com.intelligent.platform.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.platform.server.dao.IntelligentTutoringAnnouncementDAO;
import com.intelligent.platform.server.model.IntelligentTutoringAnnouncement;
import com.intelligent.platform.server.model.IntelligentTutoringUser;
import com.intelligent.platform.server.service.IntelligentTutoringAnnouncementService;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import com.intelligent.platform.server.vo.IntelligentTutoringAnnouncementVO;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-09
 */
@Service
public class IntelligentTutoringAnnouncementServiceImpl extends ServiceImpl<IntelligentTutoringAnnouncementDAO, IntelligentTutoringAnnouncement>
        implements IntelligentTutoringAnnouncementService {

    private final IntelligentTutoringUserService intelligentTutoringUserService;

    public IntelligentTutoringAnnouncementServiceImpl(IntelligentTutoringUserService intelligentTutoringUserService) {
        this.intelligentTutoringUserService = intelligentTutoringUserService;
    }

    /**
     * 查询所有公告
     *
     * @return List<IntelligentTutoringAnnouncementVO>
     */
    @Override
    public List<IntelligentTutoringAnnouncementVO> listAll() {
        List<IntelligentTutoringAnnouncement> list = list();
        return ListUtils.emptyIfNull(list).stream().map(this::buildVO)
                .sorted(Comparator.comparing(IntelligentTutoringAnnouncementVO::getDate).reversed())
                .collect(Collectors.toList());
    }

    private IntelligentTutoringAnnouncementVO buildVO(IntelligentTutoringAnnouncement announcement) {
        IntelligentTutoringAnnouncementVO.IntelligentTutoringAnnouncementVOBuilder voBuilder
                = IntelligentTutoringAnnouncementVO.builder();
        if (Objects.isNull(announcement)) {
            return voBuilder.build();
        }
        return voBuilder.id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .date(announcement.getDate())
                .createTime(announcement.getCreateTime())
                .createUserName(announcement.getCreateUserName())
                .createUserNameShow(getName(announcement.getCreateUserName()))
                .updateTime(announcement.getUpdateTime())
                .updateUserName(announcement.getUpdateUserName())
                .updateUserNameShow(getName(announcement.getUpdateUserName()))
                .build();
    }

    /**
     * 用户名 >> 姓名
     * @param username 用户名
     * @return 真实姓名
     */
    private String getName(String username) {
        IntelligentTutoringUser user = intelligentTutoringUserService.getById(username);
        if (Objects.isNull(user)) {
            return username;
        }
        return user.getName();
    }
}
