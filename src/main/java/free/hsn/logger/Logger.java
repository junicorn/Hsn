package free.hsn.logger;

import free.hsn.common.HsnProperties;

public class Logger {

	private static final org.slf4j.Logger HSN_LOGGER = org.slf4j.LoggerFactory.getLogger(HsnProperties.HSN);
	
	public static void info(String message) {
		HSN_LOGGER.info(message);
	}
	
	public static void info(String message, Object ...args) {
		HSN_LOGGER.info(message, args);
	}

	public static void info(String message, Throwable throwable) {
		HSN_LOGGER.info(message, throwable);
	}
	
	public static void debug(String message) {
		HSN_LOGGER.debug(message);
	}
	
	public static void debug(String message, Object ...args) {
		HSN_LOGGER.debug(message, args);
	}
	
	public static void debug(String message, Throwable throwable) {
		HSN_LOGGER.debug(message, throwable);
	}
	
	public static void warn(String message) {
		HSN_LOGGER.warn(message);
	}

	public static void warn(String message, Throwable throwable) {
		HSN_LOGGER.warn(message, throwable);
	}
	
	public static void error(String message) {
		HSN_LOGGER.error(message);
	}

	public static void error(String message, Throwable throwable) {
		HSN_LOGGER.error(message, throwable);
	}
}
