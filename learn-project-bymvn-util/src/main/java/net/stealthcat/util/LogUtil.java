package net.stealthcat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

public class LogUtil {
	
	private LogUtil() {}
	
	public static void info(String msg) {
		Logger logger = getLogger();
		logger.info(msg);
	}
	
	public static void info(String msg, Object... arguments) {
		Logger logger = getLogger();
		logger.info(msg, arguments);
	}
 	
	public static void debug(String msg) {
		Logger logger = getLogger();
		logger.debug(msg);
	}
	
	public static void debug(String msg, Object... arguments) {
		Logger logger = getLogger();
		logger.debug(msg, arguments);
	}
	
	public static void warn(String msg) {
		Logger logger = getLogger();
		logger.warn(msg);
	}
	
	public static void warn(String msg, Object... arguments) {
		Logger logger = getLogger();
		logger.warn(msg, arguments);
	}
	
	public static void error(String msg) {
		Logger logger = getLogger();
		logger.error(msg);
	}
	
	public static void error(Throwable e) {
		Logger logger = getLogger();
		logger.error("", e);
	}
	
	public static void error(String msg, Throwable e) {
		Logger logger = getLogger();
		logger.error(msg, e);
	}
	
	public static void error(String msg, Object... arguments) {
		Logger logger = getLogger();
		logger.error(msg, arguments);
	}
	
	public static void errorAndThrow(String msg) {
		Logger logger = getLogger();
		logger.error(msg);
		throw new RuntimeException(msg);
	}
	
	public static void errorAndThrow(String msg, Object... arguments) {
		Logger logger = getLogger();
		logger.error(msg, arguments);
		throw new RuntimeException(getFormattedMessage(msg, arguments));
	}
	
	public static void errorAndThrow(String msg, Throwable e) {
		Logger logger = getLogger();
		logger.error(msg, e);
		throw new RuntimeException(msg, e);
	}
	
	public static String getFormattedMessage(String msg, Object[] arguments) {
		return MessageFormatter.arrayFormat(msg, arguments).getMessage();
	}
	
	public static Logger getLogger() {
		String className = Thread.currentThread().getStackTrace()[3].getClassName();
		return LoggerFactory.getLogger(className);
	}
	
}
