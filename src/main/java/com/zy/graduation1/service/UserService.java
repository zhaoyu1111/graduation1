package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2018-12-31
 */
public interface UserService extends IService<User> {

    Boolean validatAccount(String account);

    User getUserInfo(Long studentId);

    Boolean resetPwd(Long studentId, String oldPwd, String newPwd);

}
