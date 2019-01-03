package com.zy.graduation1.service.impl;

import com.zy.graduation1.entity.UserWebToken;
import com.zy.graduation1.mapper.UserWebTokenMapper;
import com.zy.graduation1.service.UserWebTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-01
 */
@Service
public class UserWebTokenServiceImpl extends ServiceImpl<UserWebTokenMapper, UserWebToken> implements UserWebTokenService {

    @Override
    public String getToken(Long studentId) {
        UserWebToken userWebToken = baseMapper.selectById(studentId);
        if(userWebToken != null) {
            return userWebToken.getToken();
        }
        return null;
    }
}
