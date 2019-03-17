package com.zy.graduation1.config;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.zy.graduation1.common.JsonResult;
import com.zy.graduation1.common.SystemCode;
import com.zy.graduation1.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonResult exceptionHandler(Exception ex) {
        if(ex instanceof BizException) {
            Iterable iterable = Splitter.on(":").trimResults().omitEmptyStrings().split(ex.getMessage());
            List<String> item = Lists.newArrayList(iterable);
            ex.printStackTrace();
            return new JsonResult(item.get(1), item.get(2), null);
        }else if(ex instanceof ConstraintViolationException) {
            List<String> messages = Arrays.asList(ex.getMessage().split(","));
            List<String> data = messages.stream().map(s -> {
                int seperator = s.indexOf(":");
                return s.substring(-1 == seperator ? 0 : seperator + 2);
            }).collect(Collectors.toList());
            ex.printStackTrace();
            return new JsonResult(SystemCode.PARAM_ERROR.getCode(), StringUtils.join(data.get(0)), null);
        }
        ex.printStackTrace();
        return new JsonResult(SystemCode.SERVER_ERROR);
    }
}
