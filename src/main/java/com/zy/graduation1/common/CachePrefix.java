package com.zy.graduation1.common;

public enum CachePrefix {

    USER_LOGIN_PROTAL_TOKEN("user:login:protal:token", "门户网站用户登录token", 1L),
    USER_LOGIN_WEB_TOKEN("user:login:web:token", "管理后台用户登录token", 7200L),;

    private String prefix;
    private String desc;
    private Long timeout;

    CachePrefix(String prefix, String desc, Long timeout) {
        this.prefix = prefix;
        this.desc = desc;
        this.timeout = timeout;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDesc() {
        return desc;
    }

    public Long getTimeout() {
        return timeout;
    }
}
