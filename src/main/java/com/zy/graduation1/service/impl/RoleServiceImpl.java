package com.zy.graduation1.service.impl;

import com.zy.graduation1.entity.Role;
import com.zy.graduation1.mapper.RoleMapper;
import com.zy.graduation1.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
