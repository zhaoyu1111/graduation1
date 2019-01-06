package com.zy.graduation1.interceptor;

import com.alibaba.fastjson.JSON;
import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.JsonResult;
import com.zy.graduation1.common.SystemCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class SuccessAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(!(body instanceof JsonResult)) {
            JsonResult result = new JsonResult(SystemCode.SUCCESS, body);
            if(body instanceof String) {
                return JSON.toJSONString(result);
            }
            return result;
        }
        return body;
    }
}
