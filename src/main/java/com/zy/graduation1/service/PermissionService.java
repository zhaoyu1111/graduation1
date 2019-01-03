package com.zy.graduation1.service;

import com.zy.graduation1.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 添加权限
     * @param permission
     * @return
     */
    Boolean addPermission(Permission permission);

    /**
     * 删除权限
     * @param permissionId
     * @return
     */
    Boolean deletePermission(Long permissionId);

}
