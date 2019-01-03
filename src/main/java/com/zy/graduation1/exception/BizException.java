package com.zy.graduation1.exception;

public class BizException extends RuntimeException {

    private CodeStatus code;
    private String message;

    public BizException(CodeStatus errorCode) {
        super("BizException:" + errorCode.getCode() + ":" + errorCode.getMessage());
        this.code = errorCode;
        this.message = "BizException:" + errorCode.getCode() + ":" + errorCode.getMessage();
    }

    public CodeStatus getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
