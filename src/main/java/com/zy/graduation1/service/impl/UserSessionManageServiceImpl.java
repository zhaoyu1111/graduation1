package com.zy.graduation1.service.impl;

import com.zy.graduation1.common.CachePrefix;
import com.zy.graduation1.dto.user.SessionDto;
import com.zy.graduation1.entity.Operator;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.entity.UserWebToken;
import com.zy.graduation1.enums.SystemTypeStatue;
import com.zy.graduation1.enums.YesOrNoEnum;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.service.OperatorService;
import com.zy.graduation1.service.UserService;
import com.zy.graduation1.service.UserSessionManageService;
import com.zy.graduation1.service.UserWebTokenService;
import com.zy.graduation1.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserSessionManageServiceImpl implements UserSessionManageService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserWebTokenService userWebTokenService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OperatorService operatorService;

    @Override
    public SessionDto webLogin(Long operatorId, String pwd) {
        Operator operator = operatorService.selectById(operatorId);
        if(null == operator) {
            throw new BizException(BizErrorCode.OPERATOR_NOT_EXIST);
        }
        if(!validatePwd(pwd, operator.getPwd())) {
            throw new BizException(BizErrorCode.PASSWORD_ERROR);
        }
        if(0 == operator.getOperatorStatus() || YesOrNoEnum.YES.getCode().equals(operator.getDeleted())) {
            throw new BizException(BizErrorCode.ACCOUNT_FROZEN);
        }
        String token = userWebTokenService.getToken(operatorId);
        if(StringUtils.isNotEmpty(token)) {
            redisTemplate.delete(CachePrefix.USER_LOGIN_WEB_TOKEN.getPrefix() + token);
        }
        token = UUID.randomUUID().toString();
        UserWebToken webToken = new UserWebToken().setStudentId(operatorId).setToken(token);
        userWebTokenService.insertOrUpdate(webToken);
        redisTemplate.opsForValue().set(CachePrefix.USER_LOGIN_WEB_TOKEN.getPrefix() + token, operatorId,
                CachePrefix.USER_LOGIN_WEB_TOKEN.getTimeout(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("test", 1, CachePrefix.USER_LOGIN_WEB_TOKEN.getTimeout(), TimeUnit.SECONDS);
        System.out.println(CachePrefix.USER_LOGIN_WEB_TOKEN.getPrefix());
        System.out.println(CachePrefix.USER_LOGIN_WEB_TOKEN.getTimeout());
        System.out.println(redisTemplate.opsForValue().get(CachePrefix.USER_LOGIN_WEB_TOKEN.getPrefix() + token));
        return new SessionDto(operatorId, token);
    }

    @Override
    public SessionDto protalLogin(Long account, String pwd, SystemTypeStatue typeStatue) {
        return null;
    }

    private Boolean validatePwd(String pwd, String savePwd) {
        return MD5Utils.encode(pwd).equals(savePwd);
    }
}
