package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.common.SystemCode;
import com.zy.graduation1.dto.user.OperatorRoleDto;
import com.zy.graduation1.dto.user.RoleMenuRelationDto;
import com.zy.graduation1.entity.*;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SystemManageServerImpl implements SystemManageService {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuRelationService roleMenuRelationService;

    @Autowired
    private OperatorRoleRelationService operatorRoleRelationService;

    @Override
    public MyPage<OperatorRoleRelation> queryOperatorRole(String operatorName, String roleName) {
        return null;
    }

    @Override
    public void saveOrUpdateRoleMenu(Long relationId, Long roleId, Long menuId) {
        Role role = roleService.selectById(roleId);
        if(null == role) {
            throw new BizException(SystemCode.ROLE_NOT_EXIST);
        }
        Menu menu = menuService.selectById(menuId);
        if(null == menu) {
            throw new BizException(SystemCode.MENU_NOT_EXIST);
        }
        RoleMenuRelation roleMenuRelation = roleMenuRelationService.getRoleMenuRelation(roleId, menuId);
        if(null != roleMenuRelation) {
            throw new BizException(SystemCode.MENU_EXIST);
        }
        List<RoleMenuRelation> relations = Lists.newArrayList();
        RoleMenuRelation roleMenu = new RoleMenuRelation();
        roleMenu.setMenuId(menuId).setRoleId(roleId);
        if(null != relationId) {
            roleMenu.setRelationId(relationId);
        }
        relations.add(roleMenu);

        /* 查詢是否存在父目錄，如果不存在則加入*/
        Menu parentMenu = menuService.selectById(menuId);
        RoleMenuRelation parentRoleMenu = roleMenuRelationService.getRoleMenuRelation(roleId, parentMenu.getParentId());
        if(null == parentRoleMenu && parentMenu.getParentId() != 0) {
            RoleMenuRelation roleMenuRelation1 = new RoleMenuRelation();
            roleMenuRelation1.setRoleId(roleId).setMenuId(parentRoleMenu.getMenuId());
            relations.add(roleMenuRelation1);
        }
        roleMenuRelationService.insertOrUpdateBatch(relations);
    }

    @Override
    public MyPage<RoleMenuRelationDto> queryRoleMenuRelation(Long roleId, Long menuId, Integer currentPage) {
        IPage<RoleMenuRelation> relationPage = roleMenuRelationService.queryRoleMenuRelation(roleId, menuId, currentPage);
        List<RoleMenuRelation> relations = relationPage.getRecords();
        if(CollectionUtils.isEmpty(relations)) {
            return new MyPage<>();
        }
        List<Long> roleIds = relations.stream().map(RoleMenuRelation::getRoleId).distinct().collect(Collectors.toList());
        List<Long> menuIds = relations.stream().map(RoleMenuRelation::getMenuId).distinct().collect(Collectors.toList());

        List<Role> roles = roleService.listRole(roleIds);
        if(CollectionUtils.isEmpty(roles)) {
            return new MyPage<>();
        }
        Map<Long, String> roleNameMap = Maps.newHashMap();
        roles.forEach(role -> {
            roleNameMap.put(role.getRoleId(), role.getRoleName());
        });

        List<Menu> menus = menuService.listMenu(menuIds);
        if(CollectionUtils.isEmpty(menus)) {
            return new MyPage<>();
        }
        Map<Long, String> menuNameMap = Maps.newHashMap();
        menus.forEach(menu -> {
            menuNameMap.put(menu.getMenuId(), menu.getTitle());
        });

        List<RoleMenuRelationDto> relationDtos = Lists.newArrayList();
        for (RoleMenuRelation relation : relations) {
            RoleMenuRelationDto roleMenuRelationDto = new RoleMenuRelationDto();
            BeanUtils.copyProperties(relation, roleMenuRelationDto);
            roleMenuRelationDto.setMenuName(menuNameMap.get(relation.getMenuId()));
            roleMenuRelationDto.setRoleName(roleNameMap.get(relation.getRoleId()));
            relationDtos.add(roleMenuRelationDto);
        }
        return new MyPage<>(relationPage.getTotal(), relationDtos);
    }

    @Override
    public MyPage<OperatorRoleDto> queryOperatorRoleRelation(Long roleId, String operatorName, Integer currentPage) {
        Operator operator = operatorService.getOperatorInfo(operatorName);
        Long operatorId = null;
        if(null != operator) {
            operatorId = operator.getOperatorId();
        }
        IPage<OperatorRoleRelation> relationPage = operatorRoleRelationService.queryOperatorRole(operatorId, roleId, currentPage);
        List<OperatorRoleRelation> relations = relationPage.getRecords();
        if(CollectionUtils.isEmpty(relations)) {
            return new MyPage<>();
        }

        List<Long> roleIds = relations.stream().map(OperatorRoleRelation::getRoleId).distinct().collect(Collectors.toList());
        List<Long> operatorIds = relations.stream().map(OperatorRoleRelation::getOperatorId).distinct().collect(Collectors.toList());

        List<Role> roles = roleService.listRole(roleIds);
        Map<Long, String> roleNameMap = Maps.newHashMap();
        roles.forEach(role -> {
            roleNameMap.put(role.getRoleId(), role.getRoleName());
        });

        List<Operator> operators = operatorService.getOperatorInfo(operatorIds);
        Map<Long, String> operatorNameMap = Maps.newHashMap();
        operators.forEach(operator1 -> {
            operatorNameMap.put(operator1.getOperatorId(), operator1.getOperatorName());
        });

        List<OperatorRoleDto> operatorRoleDtos = Lists.newArrayList();
        for (OperatorRoleRelation relation : relations) {
            OperatorRoleDto operatorRoleDto = new OperatorRoleDto();
            BeanUtils.copyProperties(relation, operatorRoleDto);
            operatorRoleDto.setOperatorName(operatorNameMap.get(relation.getOperatorId()));
            operatorRoleDto.setRoleName(operatorNameMap.get(relation.getRoleId()));
            operatorRoleDtos.add(operatorRoleDto);
        }
        return new MyPage<>(relationPage.getTotal(), operatorRoleDtos);
    }

    @Override
    public void saveOrUpdateOperatorRole(Long relationId, String operatorName, Long roleId) {
        Long operatorId = null;
        Operator operator = operatorService.getOperatorInfo(operatorName);
        if(null != operator) {
            operatorId = operator.getOperatorId();
        }else {
            throw new BizException(BizErrorCode.OPERATOR_NOT_EXIST);
        }

        OperatorRoleRelation operatorRoleRelation = new OperatorRoleRelation();
        operatorRoleRelation.setOperatorId(operatorId).setRoleId(roleId);
        if(null != relationId) {
            operatorRoleRelation.setRelationId(relationId);
        }
        if(null == relationId) {
            OperatorRoleRelation relation = operatorRoleRelationService.getOperatorRoleRelation(operatorId, roleId);
            if(null != relation) {
                throw new BizException(SystemCode.OPERATOR_ROLE_EXIST);
            }
        }
        operatorRoleRelationService.insertOrUpdate(operatorRoleRelation);
    }
}
