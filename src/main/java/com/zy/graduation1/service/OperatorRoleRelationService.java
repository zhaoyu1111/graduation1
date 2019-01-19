package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.OperatorRoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-19
 */
public interface OperatorRoleRelationService extends IService<OperatorRoleRelation> {

    /**
     * 分页查询管理员角色联系表
     * @param operatorId
     * @param roleId
     * @return
     */
    IPage<OperatorRoleRelation> queryOperatorRole(Long operatorId, Long roleId, Integer currentPage);

    /**
     * 获取管理员角色
     * @param operatorId
     * @param roleId
     * @return
     */
    OperatorRoleRelation getOperatorRoleRelation(Long operatorId, Long roleId);
}
