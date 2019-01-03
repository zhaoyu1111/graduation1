package com.zy.graduation1.common;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;


public class JsonResult<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public JsonResult(){}

    public JsonResult(SystemCode code) {
        this(code, null);
    }

    public JsonResult(SystemCode systemCode, T data) {
        this.code = systemCode.getCode();
        this.message = systemCode.getMessage();
        this.data = data;
    }

    public JsonResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
