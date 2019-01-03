package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.OriginUserRelation;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.mapper.OriginUserRelationMapper;
import com.zy.graduation1.service.OriginUserRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-03
 */
@Service
public class OriginUserRelationServiceImpl extends ServiceImpl<OriginUserRelationMapper, OriginUserRelation> implements OriginUserRelationService {

    @Override
    public IPage<User> queryUserInfo(Long originId, Integer currentPage) {


        return null;
    }
}
