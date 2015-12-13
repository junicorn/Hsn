package free.hsn.common;

public class HsnProperties {
	
	public static final int DEFAULT_CHANNEL_SELECTOR_COUNT = Runtime.getRuntime().availableProcessors();

	public static final int DEFAULT_CHANNEL_THREAD_COUNT = DEFAULT_CHANNEL_SELECTOR_COUNT * 4;
	
	public static final int DEFAULT_BUFFER_POOL_SIZE = 1024;
	
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	
	public static final int DEFAULT_BUFFER_POOL_KEEPALIVE = 60;

	public static final int BACKLOG = 50;
	
	public static final int PERFORMANCE_CONNECTIONTIME = 2;
	
	public static final int PERFORMANCE_LATENCY = 1;
	
	public static final int PERFORMANCE_BANDWIDTH = 3;
}
