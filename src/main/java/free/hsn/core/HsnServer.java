package free.hsn.core;

import java.util.HashMap;
import java.util.Map;

import free.hsn.common.HsnProperties;

public class HsnServer {

	// 执行从Selector读写线程池
	
	// 事件处理器 ChannelAdaptor
	
	private int port;
	
	private AcceptProcessor acceptProcessor;
	
	private ChannelProcessor channelProcessor;
	
	/**
	 * Socket options
	 */
	private Map<Integer, Object> socketOptions = new HashMap<Integer, Object>();
	
	/**
	 * Count of channel selector
	 */
	private int channelSelectorCount = HsnProperties.DEFAULT_CHANNEL_SELECTOR_COUNT;
	
	public HsnServer() {
		init();
	}
	
	private void init() {
		acceptProcessor = AcceptProcessor.newInstance(this);
		channelProcessor = ChannelProcessor.newInstance(this, channelSelectorCount);
	}
	
	public ChannelProcessor channelProcessor() {
		return channelProcessor;
	}

	public int port() {
		return port;
	}

	public Map<Integer, Object> getSocketOptions() {
		return socketOptions;
	}
}
