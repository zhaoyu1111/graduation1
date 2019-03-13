package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.UserInfoDto;
import com.zy.graduation1.entity.*;
import com.zy.graduation1.entity.Class;
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
public class UserInfoManageManageServiceImpl implements UserInfoManageService {

    @Autowired
    private OriginService originService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuRelationService roleMenuRelationService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private OperatorRoleRelationService operatorRoleRelationService;

    @Override
    public MyPage<UserInfoDto> queryUserInfo(Long operatorId, Integer currentPage, Long classId, Long majorId, Long collegeId) {
        /*Origin origin = originService.getOriginOperator(operatorId);
        if(null == origin) {
            return new MyPage<>();
        }
        IPage<OperatorOriginRelationController> originUserRelationPage = originUserRelationService.queryUserInfo(origin.getOriginId(), currentPage);
        List<OperatorOriginRelationController> originUserRelations = originUserRelationPage.getRecords();
        if(CollectionUtils.isEmpty(originUserRelations)) {
            return new MyPage<>();
        }

        List<Long> studentIds = originUserRelations.stream().map(OperatorOriginRelationController::getStudentId).distinct().collect(Collectors.toList());
        List<User> users = userService.listUser(studentIds, classId, majorId, collegeId);
        if(CollectionUtils.isEmpty(users)) {
            return new MyPage<>();
        }
        *//* 批量查询班级信息*//*
        List<Long> classIds = users.stream().map(User::getClassId).distinct().collect(Collectors.toList());
        List<Class> classes = classService.listClassInfo(classIds);
        Map<Long, Class> classMap = Maps.uniqueIndex(classes.iterator(), Class::getClassId);

        *//* 批量查询专业信息*//*
        List<Long> majorIds = users.stream().map(User::getMajorId).distinct().collect(Collectors.toList());
        List<Major> majors = majorService.listMajor(majorIds);
        Map<Long, Major> majorMap = Maps.uniqueIndex(majors.iterator(), Major::getMajorId);

        *//* 批量查询学院信息*//*
        List<Long> collegeIds = users.stream().map(User::getCollegeId).distinct().collect(Collectors.toList());
        List<College> colleges = collegeService.listCollege(collegeIds);
        Map<Long, College> collegeMap = Maps.uniqueIndex(colleges.iterator(), College::getCollegeId);

        List<UserInfoDto> userInfos = Lists.newArrayList();
        for (User user : users) {
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(user, userInfoDto);
            Class classs = classMap.get(user.getClassId());
            if(null != classs) {
                userInfoDto.setClassName(classs.getClassName());
            }
            Major major = majorMap.get(user.getMajorId());
            if(null != major) {
                userInfoDto.setMajorName(major.getMajorName());
            }
            College college = collegeMap.get(user.getCollegeId());
            if(null != college) {
                userInfoDto.setCollegeName(college.getCollegeName());
            }
            userInfos.add(userInfoDto);
        }
        return new MyPage<>((long)userInfos.size(), userInfos);*/
        return null;
    }

    @Override
    public List<Menu> listMenu(Long operatorId) {
        OperatorRoleRelation relation = operatorRoleRelationService.getOperatorRole(operatorId);
        if(null == relation) {
            throw new BizException(BizErrorCode.OPERATOR_NOT_ROLE);
        }
        List<RoleMenuRelation> roleMenus = roleMenuRelationService.listRoleMenu(relation.getRoleId());
        if(CollectionUtils.isEmpty(roleMenus)) {
            return null;
        }
        List<Long> menuIds = roleMenus.stream().map(RoleMenuRelation::getMenuId).distinct().collect(Collectors.toList());
        List<Menu> menus = menuService.listMenu(menuIds);
        return menus;
    }
}
