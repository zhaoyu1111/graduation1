package com.zy.graduation1.exception;

public enum PermissionErrorCode implements CodeStatus {

    PERMISSION_EXIT("500001", "权限存在"),
    PERMISSION_NOT_EXIST("500002", "权限不存在"),;

    private String code;
    private String message;

    PermissionErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
