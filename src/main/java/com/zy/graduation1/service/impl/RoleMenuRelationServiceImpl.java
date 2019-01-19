package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.common.MyPage;
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

    @Override
    public void saveOrUpdateRoleMenu(Long relationId, Long roleId, Long menuId) {
        RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
        roleMenuRelation.setMenuId(menuId).setRoleId(roleId);
        if(null != relationId) {
            roleMenuRelation.setRelationId(relationId);
        }
        this.insertOrUpdate(roleMenuRelation);
    }

    @Override
    public void deleteRoleMenu(Long relationId) {
        baseMapper.deleteById(relationId);
    }

    @Override
    public IPage<RoleMenuRelation> queryRoleMenuRelation(Long roleId, Long menuId, Integer currentPage) {
        Page<RoleMenuRelation> page = new Page<>(currentPage, 10);
        QueryWrapper<RoleMenuRelation> query = new QueryWrapper<>();
        if(null != roleId) {
            query.eq("role_id", roleId);
        }
        if(null != menuId) {
            query.eq("menu_id", menuId);
        }
        return baseMapper.selectPage(page, query);
    }

    @Override
    public RoleMenuRelation getRoleMenuRelation(Long roleId, Long menuId) {
        QueryWrapper<RoleMenuRelation> query = new QueryWrapper<>();
        query.eq("role_id", roleId);
        query.eq("menu_id", menuId);
        return baseMapper.selectOne(query);
    }
}
