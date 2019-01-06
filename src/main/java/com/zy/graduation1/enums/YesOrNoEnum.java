package com.zy.graduation1.enums;

public enum YesOrNoEnum {

    YES(0, "是"),
    NO(1, "否"),;

    private Integer code;
    private String message;

    YesOrNoEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
