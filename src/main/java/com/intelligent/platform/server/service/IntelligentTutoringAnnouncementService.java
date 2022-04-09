package com.intelligent.platform.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.platform.server.model.IntelligentTutoringAnnouncement;
import com.intelligent.platform.server.vo.IntelligentTutoringAnnouncementVO;

import java.util.List;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-09
 */
public interface IntelligentTutoringAnnouncementService extends IService<IntelligentTutoringAnnouncement> {

    /**
     * 查询所有公告
     * @return List<IntelligentTutoringAnnouncementVO>
     */
    List<IntelligentTutoringAnnouncementVO> listAll();
}
