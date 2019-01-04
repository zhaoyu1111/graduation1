package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.UserInfoDto;
import com.zy.graduation1.entity.*;
import com.zy.graduation1.entity.Class;
import com.zy.graduation1.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserInfoManageManageServiceImpl implements UserInfoManageService {

    @Autowired
    private OriginService originService;

    @Autowired
    private OriginUserRelationService originUserRelationService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @Override
    public MyPage<UserInfoDto> queryUserInfo(Long operatorId, Integer currentPage) {
        Origin origin = originService.getOriginOperator(operatorId);
        if(null == origin) {
            return new MyPage<>();
        }
        IPage<User> userPage = originUserRelationService.queryUserInfo(operatorId, currentPage);
        List<User> users = userPage.getRecords();
        if(CollectionUtils.isEmpty(users)) {
            return new MyPage<>();
        }
        /* 批量查询班级信息*/
        List<Long> classIds = users.stream().map(User::getClassId).distinct().collect(Collectors.toList());
        List<Class> classes = classService.listClassInfo(classIds);
        Map<Long, Class> classMap = Maps.uniqueIndex(classes.iterator(), Class::getClassId);

        /* 批量查询专业信息*/
        List<Long> majorIds = users.stream().map(User::getMajorId).distinct().collect(Collectors.toList());
        List<Major> majors = majorService.listMajor(majorIds);
        Map<Long, Major> majorMap = Maps.uniqueIndex(majors.iterator(), Major::getMajorId);

        /* 批量查询学院信息*/
        List<Long> collegeIds = users.stream().map(User::getCollegeId).distinct().collect(Collectors.toList());
        List<College> colleges = collegeService.listCollege(collegeIds);
        Map<Long, College> collegeMap = Maps.uniqueIndex(colleges.iterator(), College::getCollegeId);

        List<UserInfoDto> userInfos = Lists.newArrayList();
        for (User user : users) {
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(user, userInfoDto);
            Class classs = classMap.get(user.getClassId());
            if(null != classes) {
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
        return new MyPage<>(userPage.getTotal(), userInfos);
    }
}
