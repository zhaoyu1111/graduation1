package com.zy.graduation1.exception;

public enum OriginErrorCode implements CodeStatus {

    RECRUIT_NOT_EXIST("50001", "职位不存在"),
    UNIT_NOT_EXIST("50002", "公司不存在"),
    ACTIVITY_NOT_EXIST("50003", "活动不存在"),
    COLLEGE_EXIST("50004", "学院已存在，请勿重复添加"),
    COLLEGE_NOT_EXIST("50005", "学院不存在"),
    MAJOR_NOT_EXIST("50006", "专业不存在"),
    DONATION_PROJECT_NOT_EXIST("50007", "捐赠项目不存在，请刷新后重试");

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
