package com.zy.graduation1.utils.logging;

/**
 * gws日志枚举类
 *
 * @version 
 * @author liuyi  2016年4月19日 下午11:45:05
 * 
 */
public enum LoggerEnum {

	GWS("GWS"),
	SQLTRACE("SQLTRACE"),
	ACCESSTRACE("ACCESSTRACE");

	private String logerName;

	LoggerEnum(String logerName) {
		this.logerName = logerName;
	}

	public String getLogerName() {
		return logerName;
	}
}
