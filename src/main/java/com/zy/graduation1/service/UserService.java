package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.UserDto;
import com.zy.graduation1.entity.College;
import com.zy.graduation1.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 批量查询用户信息
     * @param studentIds
     * @return
     */
    List<User> listUser(List<Long> studentIds, Long classId, Long majorId, Long collegeId);

    /**
     * 分页查询用户信息
     * @param majorId
     * @param classId
     * @param nameOrId
     * @return
     */
    IPage<User> queryUser(List<College> colleges, String address, Long majorId, Long classId,
                          String nameOrId, Integer currentPage);

}
