package com.zy.graduation1.service;

import com.zy.graduation1.dto.user.SessionDto;
import com.zy.graduation1.enums.SystemTypeStatue;

public interface UserSessionManageService {

    /**
     * 用户登录
     * @param account
     * @param pwd
     */
    SessionDto login(Long account, String pwd, SystemTypeStatue typeStatue);
}
