package com.zy.graduation1.service.impl;

import com.zy.graduation1.common.CachePrefix;
import com.zy.graduation1.dto.user.SessionDto;
import com.zy.graduation1.entity.Operator;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.entity.UserWebToken;
import com.zy.graduation1.enums.SystemTypeStatue;
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
    public SessionDto login(Long account, String pwd, SystemTypeStatue typeStatue) {
        User user = userService.getUserInfo(account);
        if(null == user) {
            throw new BizException(BizErrorCode.ACCOUNT_NOT_EXIST);
        }
        if(1 == user.getStatus()) {
            throw new BizException(BizErrorCode.ACCOUNT_FROZEN);
        }
        if(!(user.getPwd().equals(MD5Utils.encode(pwd)))) {
            throw new BizException(BizErrorCode.PASSWORD_ERROR);
        }
        return login(account, typeStatue);
    }

    private SessionDto login(Long account, SystemTypeStatue typeStatue) {
        String token = "";
        switch (typeStatue) {
            case WEB:
                Operator operator = operatorService.selectById(account);
                if(null == operator) {
                    throw new BizException(BizErrorCode.OPERATOR_NOT_EXIST);
                }

                if(1 == operator.getDeleted()) {
                    throw new BizException(BizErrorCode.ACCOUNT_FROZEN);
                }
                token = userWebTokenService.getToken(account);
                if(StringUtils.isNotEmpty(token)) {
                    redisTemplate.delete(CachePrefix.USER_LOGIN_WEB_TOKEN.getPrefix() + token);
                }
                token = UUID.randomUUID().toString();
                UserWebToken webToken = new UserWebToken().setStudentId(account).setToken(token);
                userWebTokenService.insertOrUpdate(webToken);
                redisTemplate.opsForValue().set(CachePrefix.USER_LOGIN_WEB_TOKEN.getPrefix() + token, account,
                        CachePrefix.USER_LOGIN_WEB_TOKEN.getTimeout(), TimeUnit.SECONDS);
                break;
            case PROTAL:
                break;
            default:
                break;
        }
        return new SessionDto(account, token);
    }
}
