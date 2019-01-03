package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.Permission;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.PermissionErrorCode;
import com.zy.graduation1.mapper.PermissionMapper;
import com.zy.graduation1.service.PermissionService;
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
 * @since 2018-12-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public Boolean addPermission(Permission permission) {
        QueryWrapper query = new QueryWrapper();
        query.eq("permission_name", permission);
        List<Permission> permissions = baseMapper.selectList(query);
        if(!CollectionUtils.isEmpty(permissions)) {
            throw new BizException(PermissionErrorCode.PERMISSION_EXIT);
        }
        return baseMapper.insert(permission) == 1;
    }

    @Override
    public Boolean deletePermission(Long permissionId) {
        Permission permission = baseMapper.selectById(permissionId);
        if(null == permission) {
            throw new BizException(PermissionErrorCode.PERMISSION_NOT_EXIST);
        }
        return baseMapper.deleteById(permissionId) == 1;
    }
}
