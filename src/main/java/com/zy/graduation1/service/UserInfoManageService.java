package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.UserInfoDto;

import java.util.List;

public interface UserInfoManageService {

    /**
     * 查询改管理员下的用户
     * @param operator
     * @return
     */
    MyPage<UserInfoDto> queryUserInfo(Long operator, Integer currentPage);
}
