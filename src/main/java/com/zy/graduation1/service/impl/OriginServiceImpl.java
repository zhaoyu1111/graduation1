package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.Origin;
import com.zy.graduation1.mapper.OriginMapper;
import com.zy.graduation1.service.OriginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class OriginServiceImpl extends ServiceImpl<OriginMapper, Origin> implements OriginService {

    @Override
    public Origin getOriginOperator(Long operatorId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("operator_id", operatorId);
        List<Origin> origins = baseMapper.selectList(query);
        return CollectionUtils.isEmpty(origins) ? null : origins.get(0);
    }
}
