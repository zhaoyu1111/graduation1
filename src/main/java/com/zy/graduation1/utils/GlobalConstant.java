package com.zy.graduation1.utils;

import com.zy.graduation1.utils.logging.AccessLog;

/**
 * @author lvjunlong
 * @date 18-11-27 上午11:13
 */
public class GlobalConstant {

        public static final String SQL_TRACE = "SQLTRACE";

        public static final String ACCESS_TRACE = "ACCESSTRACE";

        public static final String GWS_LOG = "GWS";

        public static final String CON_QUOTE = "`";

        public static  Boolean SHOW_SQL=false;

        public static ThreadLocal<AccessLog> accessLog= new ThreadLocal<AccessLog>();

        public static int SID_COOKIE_MAXAGE = 60*60*24*30;


}
