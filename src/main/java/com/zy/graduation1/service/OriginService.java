package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
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

    /**
     * 分页查询组织信息
     * @param originName
     * @param address
     * @param originType
     * @return
     */
    MyPage<Origin> queryOrigin(String originName, String address, Integer originType);

}
