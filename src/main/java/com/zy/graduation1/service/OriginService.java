package com.zy.graduation1.service;

import com.zy.graduation1.entity.Origin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-03
 */
public interface OriginService extends IService<Origin> {

    /**
     * 通过管理员ID 查出该管理员管理的组织
     * @param operatorId
     * @return
     */
    Origin getOriginOperator(Long operatorId);
}
