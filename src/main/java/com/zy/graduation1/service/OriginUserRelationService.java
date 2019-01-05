package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.OriginUserRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.graduation1.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-03
 */
public interface OriginUserRelationService extends IService<OriginUserRelation> {

    /**
     * 通过组织ID查询改组织下的所有成员
     * @param originId
     * @return
     */
    List<OriginUserRelation> queryUserInfo(Long originId);
}
