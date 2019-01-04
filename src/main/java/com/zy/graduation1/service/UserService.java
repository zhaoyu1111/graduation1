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

    /**
     * 验证用户是否存在
     * @param account
     * @return
     */
    Boolean validatAccount(String account);

    /**
     * 获取用户信息
     * @param studentId
     * @return
     */
    User getUserInfo(Long studentId);

    /**
     * 重置密码
     * @param studentId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    Boolean resetPwd(Long studentId, String oldPwd, String newPwd);

    /**
     * 新增或更新用户信息(当studentId为null时，表示新增)
     * @param user
     * @return
     */
    Boolean saveOrUpdateUser(User user);

    /**
     * 删除用户
     * @param studentId
     * @return
     */
    Boolean deleteUser(Long studentId);
}
