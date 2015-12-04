package com.utils;

public class Logger {

	private static org.apache.log4j.Logger infoLog = org.apache.log4j.Logger.getLogger("appInfo");
	private static org.apache.log4j.Logger debugLog = org.apache.log4j.Logger.getLogger("appDebug");
	private static org.apache.log4j.Logger errorLog = org.apache.log4j.Logger.getLogger("appError");
	
	public static void info(Object message) {
		infoLog.info(message);
	}
	
	public static void debug(Object message) {
		debugLog.debug(message);
	}
	
	public static void error(Object message) {
		errorLog.error(message);
	}
}
