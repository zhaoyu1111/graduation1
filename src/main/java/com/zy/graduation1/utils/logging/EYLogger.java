package com.zy.graduation1.utils.logging;

import com.zy.graduation1.utils.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局log类
 *
 * @version 
 * @author liuyi  2016年4月19日 下午11:08:53
 * 
 */
public final class EYLogger {

	private static ConcurrentHashMap<LoggerEnum, Logger> logMaps = new ConcurrentHashMap<>();

	/** 默认logger **/
	private static Logger GWS_LOGGER = LogManager.getLogger(LoggerEnum.GWS.getLogerName());

	/** 除此logger, 基于日志需要添加上下文 **/
	private static Logger ACCESS_LOGGER = LogManager.getLogger(LoggerEnum.ACCESSTRACE.getLogerName());

	static{
		logMaps.put(LoggerEnum.GWS, GWS_LOGGER);
		logMaps.put(LoggerEnum.ACCESSTRACE, ACCESS_LOGGER);
		logMaps.put(LoggerEnum.SQLTRACE, LogManager.getLogger(LoggerEnum.SQLTRACE.getLogerName()));
	}

	/**
	 *
	 * 全局日志logger调用, 默认为GwsLogger
	 *
	 * @author liuyi 2016年4月19日
	 * @param loggerType
	 * @return
	 */
	public static Logger getLogger(LoggerEnum loggerType){
		Logger logger= logMaps.get(loggerType);
		return (null != logger) ? logger : GWS_LOGGER;
	}

	/**
	 *
	 * 全局日志默认GWS枚举
	 *
	 * @author liuyi 2016年4月19日
	 * @return
	 */
	public static Logger getLogger(){
		return GWS_LOGGER;
	}

	/**
	 *
	 * 输出已定义日志类别的info日志
	 *
	 * @author liuyi 2016年4月20日
	 * @param loggerType
	 * @param msg
	 * @param args
	 */
	public static void info(LoggerEnum loggerType, String msg, Object... args){
		Logger logger = getLogger(loggerType);
		if (!ACCESS_LOGGER.equals(logger)) {
			msg = addImportantToLog(msg);
		}

		logger.info(msg, args);
	}

	/**
	 *
	 * 输出默认的GWS类别info日志
	 *
	 * @author liuyi 2016年4月20日
	 * @param msg
	 * @param args
	 */
	public static void info(String msg, Object... args){
		Logger logger = getLogger();
		if (!ACCESS_LOGGER.equals(logger)) {
			msg = addImportantToLog(msg);
		}
		logger.info(msg, args);
	}



	/**
	 *
	 *  输出默认的GWS类别debug日志
	 *
	 * @author liuyi 2016年4月20日
	 * @param msg
	 * @param args
	 */
	public static void debug(String msg, Object... args){
		Logger logger = getLogger();
		if (!ACCESS_LOGGER.equals(logger)) {
			msg = addImportantToLog(msg);
		}
		logger.debug(msg, args);
	}

	/**
	 *
	 *输出默认GWS日志类别的debug日志
	 *
	 * @author liuyi 2016年4月20日
	 * @param
	 * @param msg
	 * @param args
	 */
	public static void debug(LoggerEnum loggerType, String msg, Object... args) {
		Logger logger = getLogger(loggerType);
		if (!ACCESS_LOGGER.equals(logger)) {
			msg = addImportantToLog(format(msg,args));
		}
		logger.debug(msg);
	}

	/**
	 *
	 * 输出默认GWS日志类别的错误日志
	 *
	 * @author liuyi 2016年4月20日
	 * @param msg
	 * @param args
	 */
	public static void error(String msg, Object... args){
		Logger logger = getLogger();
		if (!ACCESS_LOGGER.equals(logger)) {
			msg = addImportantToLog(msg);
		}
		logger.error(msg, args);
	}

	/**
	 *
	 *  输出默认GWS日志类别的错误日志
	 *
	 * @author liuyi 2016年4月20日
	 * @param throwable
	 * @param msg
	 * @param args
	 */
	public static void error(Throwable throwable, String msg, Object... args){
		Logger logger = getLogger();
		if (!ACCESS_LOGGER.equals(logger)) {
			msg = addImportantToLog(format(msg,args));
		}
		logger.error(msg, throwable);
	}

	/**
	 *
	 * 增加重要信息到日志中
	 *
	 * @author liuyi 2016年4月20日
	 * @param msg
	 * @return
	 */
	private static String addImportantToLog(String msg) {
		AccessLog accessLog = GlobalConstant.accessLog.get();

		String action = (null != accessLog) ? accessLog.getAction() : StringUtils.EMPTY;
		String sid = (null != accessLog) ? accessLog.getSid() : StringUtils.EMPTY;
		String uid = (null != accessLog) ? accessLog.getUid() : StringUtils.EMPTY;

		StringBuilder sb = new StringBuilder();
		sb.append("action=").append(action).append(GlobalConstant.CON_QUOTE);
		sb.append("uid=").append(uid).append(GlobalConstant.CON_QUOTE);
		sb.append("sid=").append(sid).append(GlobalConstant.CON_QUOTE);
		sb.append("msg=").append(msg).append("===>");
		return sb.toString();
	}

	/**
	 *
	 * 格式化文本
	 *
	 * @author liuyi 2016年4月20日
	 * @param format
	 * @param args
	 * @return
	 */
	private static String format(String format, Object... args) {
		if (args != null && args.length > 0) {
			return String.format(format, args);
		} else {
			return format;
		}
	}


}
