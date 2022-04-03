package com.intelligent.platform.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.platform.server.dao.IntelligentTutoringUserDAO;
import com.intelligent.platform.server.model.IntelligentTutoringUser;
import com.intelligent.platform.server.service.IntelligentTutoringUserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-03
 */
@Service("intelligentTutoringUserService")
public class IntelligentTutoringUserServiceImpl extends ServiceImpl<IntelligentTutoringUserDAO, IntelligentTutoringUser>
        implements IntelligentTutoringUserService {

}
