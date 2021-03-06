package com.intelligent.platform.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.platform.server.model.IntelligentTutoringUser;
import com.intelligent.platform.server.vo.IntelligentTutoringUserVO;

import java.util.List;

/**
 * 所有和用户信息相关的业务逻辑接口都归 {@link IntelligentTutoringUserService} 管理, 但是不提供具体逻辑实现
 *
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-03
 */
public interface IntelligentTutoringUserService extends IService<IntelligentTutoringUser> {

    /**
     * 根据用户名查询用户登录信息
     * @param username 用户名
     * @return 用户登录信息VO
     */
    IntelligentTutoringUserVO queryByUsername(String username);

    /**
     * 查询所有用户信息
     * @return 用户信息VO
     */
    List<IntelligentTutoringUserVO> listAll();
}
