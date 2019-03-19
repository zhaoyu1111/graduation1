package com.zy.graduation1.exception;

public enum BizErrorCode implements CodeStatus {

    ACCOUNT_NOT_EXIST("400001", "账号不存在"),
    ACCOUNT_FROZEN("400002", "账号被冻结，请联系管理员"),
    PASSWORD_ERROR("400002", "密码错误"),
    OPERATOR_NOT_EXIST("400004", "管理员不存在"),
    ORIGIN_EXIST("400005", "学院已存在"),
    ORIGIN_NOT_EXIST("400006", "学院不存在"),
    OPERATOR_NOT_ROLE("400007", "管理员没有分配角色，请联系学校负责人"),
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
