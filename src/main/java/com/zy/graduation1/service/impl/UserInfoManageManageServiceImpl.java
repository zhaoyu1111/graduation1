package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.UserDto;
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

    @Autowired
    private OperatorOriginRelationService operatorOriginRelationService;

    @Override
    public MyPage<UserDto> queryUser(Long operatorId, Long majorId, Long classId,
                                         String nameOrId, Integer currentPage) {
        OperatorRoleRelation relation = operatorRoleRelationService.getOperatorRole(operatorId);
        if(null == relation) {
            throw new BizException(BizErrorCode.OPERATOR_NOT_ROLE);
        }

        IPage<User> userPage = null;
        if(relation.getRoleId() == 1) {
            userPage = userService.queryUser(null, null, majorId, classId, nameOrId, currentPage);
        }else if(relation.getRoleId() == 2) {
            List<College> colleges = collegeService.getCollege(operatorId);
            userPage = userService.queryUser(colleges, null, majorId, classId, nameOrId, currentPage);
        }else if(relation.getRoleId() == 3) {
            //TODO 校友会管理员
        } else {
            return new MyPage<>();
        }
        List<User> users = userPage.getRecords();
        if(CollectionUtils.isEmpty(users)) {
            return new MyPage<>();
        }

        List<Long> collegeId = users.stream().map(User::getCollegeId).distinct().collect(Collectors.toList());
        List<College> colleges = collegeService.listCollege(collegeId);
        Map<Long, String> collegeMap = Maps.newHashMap();
        colleges.forEach(college -> collegeMap.put(college.getCollegeId(), college.getCollegeName()));

        List<Long> majorIds = users.stream().map(User::getMajorId).distinct().collect(Collectors.toList());
        List<Major> majors = majorService.listMajorByIds(majorIds);
        Map<Long, String> majorMap = Maps.newHashMap();
        majors.forEach(major -> majorMap.put(major.getMajorId(), major.getMajorName()));

        List<UserDto> userDtos = Lists.newArrayList();
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDto.setClassName(user.getClassId().toString());
            userDto.setCollegeName(collegeMap.get(user.getCollegeId()));
            userDto.setMajorName(majorMap.get(user.getMajorId()));
            userDtos.add(userDto);
        }
        return new MyPage<>(userPage.getTotal(), userDtos);
    }

    @Override
    public UserDto getUser(Long studentId) {
        User user = userService.selectById(studentId);
        if(null == user) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        College college = collegeService.selectById(user.getCollegeId());
        if(null == college) {
            return userDto;
        }
        userDto.setCollegeName(college.getCollegeName());
        Major major = majorService.selectById(user.getMajorId());
        if(null == major) {
            return userDto;
        }
        userDto.setMajorName(major.getMajorName());
        Class cla = classService.selectById(user.getClassId());
        if(null == cla) {
            return userDto;
        }
        userDto.setClassName(cla.getClassName());
        return userDto;
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
