package com.zy.graduation1.exception;

public enum BizErrorCode implements CodeStatus {

    ACCOUNT_NOT_EXIST("400001", "账号不存在"),
    ACCOUNT_FROZEN("400002", "账号被冻结，请联系管理员"),
    PASSWORD_ERROR("400002", "密码错误"),
    OPERATOR_NOT_EXIST("400004", "管理员不存在"),
    ;

    private String code;
    private String message;

    BizErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
