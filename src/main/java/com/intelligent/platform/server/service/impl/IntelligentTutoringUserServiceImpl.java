package com.intelligent.platform.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.platform.server.dao.IntelligentTutoringUserDAO;
import com.intelligent.platform.server.enums.IntelligentTutoringGradeEnum;
import com.intelligent.platform.server.enums.IntelligentTutoringSubjectEnum;
import com.intelligent.platform.server.model.IntelligentTutoringUser;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import com.intelligent.platform.server.utils.IntelligentTutoringDateUtil;
import com.intelligent.platform.server.vo.IntelligentTutoringUserVO;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 真正实现对用户信息进行管理, 是对 {@link IntelligentTutoringUserService} 所有接口的真正实现
 *
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-03
 */
@Lazy
@Service
public class IntelligentTutoringUserServiceImpl extends ServiceImpl<IntelligentTutoringUserDAO, IntelligentTutoringUser>
        implements IntelligentTutoringUserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public IntelligentTutoringUserVO queryByUsername(String username) {
        return buildUserVO(getById(username));
    }

    /**
     * 查询所有用户信息
     *
     * @return 用户信息VO
     */
    @Override
    public List<IntelligentTutoringUserVO> listAll() {
        return ListUtils.emptyIfNull(list()).parallelStream().map(this::buildUserVO).collect(Collectors.toList());
    }

    private IntelligentTutoringUserVO buildUserVO(IntelligentTutoringUser user) {
        IntelligentTutoringUserVO vo = new IntelligentTutoringUserVO();
        if (Objects.isNull(user)) {
            return vo;
        }
        vo.setUsername(user.getUsername());
        vo.setPassword(user.getPassword());
        vo.setName(user.getName());
        vo.setAvatar(user.getAvatar());
        vo.setIntroduction(user.getIntroduction());
        vo.setPhone(user.getPhone());
        vo.setMail(user.getMail());
        vo.setRole(user.getRole());
        vo.setRoles(Collections.singletonList(user.getRole()));
        vo.setBirthday(IntelligentTutoringDateUtil.longToStr(user.getBirthday()));
        // 学生信息
        vo.setSchool(user.getSchool());
        vo.setGrade(user.getGrade());
        vo.setGradeLabel(IntelligentTutoringGradeEnum.code2Desc(user.getGrade()));
        // 老师信息
        vo.setCampus(user.getCampus());
        vo.setEntryTime(IntelligentTutoringDateUtil.longToStr(user.getEntryTime()));
        vo.setSubject(user.getSubject());
        vo.setSubjectLabel(IntelligentTutoringSubjectEnum.code2Desc(user.getSubject()));
        vo.setDisplay(user.getDisplay());
        return vo;
    }
}
