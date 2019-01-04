package com.zy.graduation1.config;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.zy.graduation1.common.JsonResult;
import com.zy.graduation1.common.SystemCode;
import com.zy.graduation1.exception.BizException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonResult exceptionHandler(Exception ex) {
        if(ex instanceof BizException) {
            Iterable iterable = Splitter.on(":").trimResults().omitEmptyStrings().split(ex.getMessage());
            List<String> item = Lists.newArrayList(iterable);
            return new JsonResult(item.get(1), item.get(2), null);
        }
        return new JsonResult(SystemCode.SERVER_ERROR);
    }
}
