package com.zy.graduation1.enums;

public enum SystemTypeStatue {

    PROTAL(1, "门户网站"),
    WEB(2, "后台管理"),;

    private Integer code;
    private String message;

    SystemTypeStatue(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static SystemTypeStatue getEnum(Integer code) {
        for (SystemTypeStatue systemTypeStatue : SystemTypeStatue.values()) {
            if(systemTypeStatue.getCode().equals(code)) {
                return systemTypeStatue;
            }
        }
        return null;
    }
}
