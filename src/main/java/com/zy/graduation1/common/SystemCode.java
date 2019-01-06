package com.zy.graduation1.common;

public enum SystemCode {

    NEED_LOGIN("2001", "未登录"),
    SUCCESS("2000", "成功"),
    ACCOUNT_ERROR("2002", "账户异常"),
    NEED_AUTH("2003", "授权不足"),
    PARAM_ERROR("2004", "参数异常"),
    SERVER_ERROR("2005", "服务器异常"),
    ;

    private String code;
    private String message;

    SystemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
