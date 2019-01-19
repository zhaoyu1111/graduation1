package com.zy.graduation1.common;

import com.zy.graduation1.exception.CodeStatus;

public enum SystemCode implements CodeStatus {

    NEED_LOGIN("2001", "未登录"),
    SUCCESS("2000", "成功"),
    ACCOUNT_ERROR("2002", "账户异常"),
    NEED_AUTH("2003", "授权不足"),
    PARAM_ERROR("2004", "参数异常"),
    SERVER_ERROR("2005", "服务器异常"),

    MENU_NOT_EXIST("3001", "菜单不存在"),
    ROLE_NOT_EXIST("3002", "角色不存在"),
    MENU_EXIST("3003", "菜單已存在"),
    OPERATOR_ROLE_EXIST("3004", "已存在"),
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
