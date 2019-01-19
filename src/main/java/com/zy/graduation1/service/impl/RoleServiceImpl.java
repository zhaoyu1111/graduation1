package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.Role;
import com.zy.graduation1.mapper.RoleMapper;
import com.zy.graduation1.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Integer addRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return baseMapper.insert(role);
    }

    @Override
    public List<Role> listRole(String roleName, Integer status) {
        QueryWrapper<Role> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(roleName)) {
            query.eq("role_name", roleName);
        }
        if(null != status) {
            query.eq("deleted", status);
        }
        return baseMapper.selectList(query);
    }

    @Override
    public void updateRole(Role role) {
        this.updateById(role);
    }

    @Override
    public List<Role> listRole(List<Long> roleIds) {
        QueryWrapper<Role> query = new QueryWrapper<>();
        query.in("role_id", roleIds);
        return baseMapper.selectList(query);
    }
}
