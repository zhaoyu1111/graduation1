package com.zy.graduation1.service;

import com.zy.graduation1.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
public interface RoleService extends IService<Role> {

    /**
     * 添加角色
     * @param roleName
     * @return
     */
    Integer addRole(String roleName);

    /**
     * 批量查询角色
     * @param roleName
     * @param status
     * @return
     */
    List<Role> listRole(String roleName, Integer status);
}
