package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.RoleMenuRelation;
import com.zy.graduation1.mapper.RoleMenuRelationMapper;
import com.zy.graduation1.service.RoleMenuRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-12
 */
@Service
public class RoleMenuRelationServiceImpl extends ServiceImpl<RoleMenuRelationMapper, RoleMenuRelation> implements RoleMenuRelationService {

    @Override
    public List<RoleMenuRelation> listRoleMenu(Long roleId) {
        QueryWrapper<RoleMenuRelation> query = new QueryWrapper<>();
        query.eq("role_id", roleId);
        return baseMapper.selectList(query);
    }
}
