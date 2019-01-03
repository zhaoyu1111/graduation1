package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.dto.user.UserInfoDto;
import com.zy.graduation1.entity.Origin;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.service.OriginService;
import com.zy.graduation1.service.OriginUserRelationService;
import com.zy.graduation1.service.UserInfoManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoManageManageServiceImpl implements UserInfoManageService {

    @Autowired
    private OriginService originService;

    @Autowired
    private OriginUserRelationService originUserRelationService;

    @Override
    public IPage<UserInfoDto> queryUserInfo(Long operatorId, Integer currentPage) {
        Origin origin = originService.getOriginOperator(operatorId);
        if(null == origin) {
            return null;
        }
        IPage<User> userPage = originUserRelationService.queryUserInfo(operatorId, currentPage);
        List<User> users = userPage.getRecords();
        if(CollectionUtils.isEmpty(users)) {
            // TODO: 2019/1/3 分页
            return null;
        }
        List<Long> classIds = users.stream().map(User::getClassId).distinct().collect(Collectors.toList());
        // TODO: 2019/1/3


        List<Long> collegeIds = users.stream().map(User::getCollegeId).distinct().collect(Collectors.toList());

        return null;
    }
}
