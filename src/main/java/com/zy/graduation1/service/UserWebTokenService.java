package com.zy.graduation1.service;

import com.zy.graduation1.entity.UserWebToken;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-01
 */
public interface UserWebTokenService extends IService<UserWebToken> {

    /**
     * 获取用户token
     * @param studentID
     * @return
     */
    String getToken(Long studentID);
}
