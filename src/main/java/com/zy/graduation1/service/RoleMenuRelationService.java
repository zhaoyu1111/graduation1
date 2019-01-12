package com.zy.graduation1.service;

import com.zy.graduation1.entity.RoleMenuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-12
 */
public interface RoleMenuRelationService extends IService<RoleMenuRelation> {

    /**
     * 通过角色ID获取菜单列表
     * @param roleId
     * @return
     */
    List<RoleMenuRelation> listRoleMenu(Long roleId);
}
