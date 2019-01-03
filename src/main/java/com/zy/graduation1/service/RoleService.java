package com.zy.graduation1.service;

import com.zy.graduation1.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
public interface RoleService extends IService<Role> {

    Integer addRole(String roleName);
}
