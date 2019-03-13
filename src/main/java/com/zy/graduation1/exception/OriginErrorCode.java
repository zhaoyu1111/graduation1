package com.zy.graduation1.exception;

public enum OriginErrorCode implements CodeStatus {

    RECRUIT_NOT_EXIST("50001", "职位不存在"),
    UNIT_NOT_EXIST("50002", "公司不存在"),
    ACTIVITY_NOT_EXIST("50003", "活动不存在");

    private String code;
    private String message;

    OriginErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
