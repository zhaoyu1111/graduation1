package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.OriginUserRelation;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.mapper.OriginUserRelationMapper;
import com.zy.graduation1.service.OriginUserRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public IPage<OriginUserRelation> queryUserInfo(Long originId, Integer currentPage) {
        QueryWrapper query = new QueryWrapper();
        query.eq("origin_id", originId);
        Page<OriginUserRelation> page = new Page<>(currentPage, 20);
        return baseMapper.selectPage(page, query);
    }
}
