package com.intelligent.platform.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.platform.server.model.IntelligentTutoringAchievement;

import java.util.List;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-05-03
 */
public interface IntelligentTutoringAchievementService extends IService<IntelligentTutoringAchievement> {

    /**
     * 查询所有教学成果
     * @param teacherUsername 教师用户名
     * @return 当前老师的所有教学成果
     */
    List<IntelligentTutoringAchievement> listAll(String teacherUsername);
}
