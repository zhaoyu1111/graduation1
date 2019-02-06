package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.OperatorRoleRelation;
import com.zy.graduation1.mapper.OperatorRoleRelationMapper;
import com.zy.graduation1.service.OperatorRoleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-19
 */
@Service
public class OperatorRoleRelationServiceImpl extends ServiceImpl<OperatorRoleRelationMapper, OperatorRoleRelation> implements OperatorRoleRelationService {

    @Override
    public IPage<OperatorRoleRelation> queryOperatorRole(Long operatorId, Long roleId, Integer currentPage) {
        Page<OperatorRoleRelation> page = new Page<>(currentPage, 10);
        QueryWrapper<OperatorRoleRelation> query = new QueryWrapper<>();
        if(null != operatorId) {
            query.eq("operator_id", operatorId);
        }
        if(null != roleId) {
            query.eq("role_id", roleId);
        }
        return baseMapper.selectPage(page, query);
    }

    @Override
    public OperatorRoleRelation getOperatorRoleRelation(Long operatorId, Long roleId) {
        QueryWrapper<OperatorRoleRelation> query = new QueryWrapper<>();
        query.eq("operator_id", operatorId);
        query.eq("role_id", roleId);
        return baseMapper.selectOne(query);
    }

    @Override
    public void saveOrUpdateOperatorRole(Long operatorId, Long roleId) {
        OperatorRoleRelation operator = this.getOperatorRole(operatorId);
        if(null != operator) {
            operator.setRoleId(roleId);
            baseMapper.updateById(operator);
            return ;
        }else {
            OperatorRoleRelation operatorRoleRelation = new OperatorRoleRelation();
            operatorRoleRelation.setOperatorId(operatorId).setRoleId(roleId);
            baseMapper.insert(operatorRoleRelation);
        }
    }

    @Override
    public OperatorRoleRelation getOperatorRole(Long operatorId) {
        QueryWrapper<OperatorRoleRelation> query = new QueryWrapper<>();
        query.eq("operator_id", operatorId);
        return baseMapper.selectOne(query);
    }

    @Override
    public void deleteOperatorRole(Long operatorId) {
        OperatorRoleRelation relation = this.getOperatorRole(operatorId);
        if(null == relation) {
            return ;
        }
        baseMapper.deleteById(relation.getRelationId());
    }
}
