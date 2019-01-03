package com.zy.graduation1.interceptor;

import com.alibaba.fastjson.JSON;
import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.JsonResult;
import com.zy.graduation1.common.RequestUser;
import com.zy.graduation1.common.SystemCode;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod)handler;
            Anonymous anonymous = hm.getMethodAnnotation(Anonymous.class);
            if(null != anonymous && anonymous.value()) {
                return true;
            }
            if(null == RequestUser.getCurrentUser()) {
                // TODO: 2018/12/30 跳转登录界面
                JsonResult result = new JsonResult(SystemCode.NEED_LOGIN);
                response.getOutputStream().write(JSON.toJSONString(result).getBytes());
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
