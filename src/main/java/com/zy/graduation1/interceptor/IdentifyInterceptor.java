package com.zy.graduation1.interceptor;

import com.zy.graduation1.common.CachePrefix;
import com.zy.graduation1.common.RequestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class IdentifyInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Header");
        RequestUser.User currentUser =  identify(token);
        if(null == currentUser) {
            return true;
        }
        RequestUser.put(currentUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    private RequestUser.User identify(String token) {
        Object obj = redisTemplate.opsForValue().get(CachePrefix.USER_LOGIN_WEB_TOKEN + token);
        if(null == obj) {
            return null;
        }
        Long uid = Long.valueOf(obj.toString());
        redisTemplate.expire(CachePrefix.USER_LOGIN_WEB_TOKEN .getPrefix() + token, CachePrefix.USER_LOGIN_WEB_TOKEN.getTimeout(), TimeUnit.SECONDS);
        RequestUser.User user = new RequestUser.User();
        user.setUserId(uid);
        return user;
    }
}
