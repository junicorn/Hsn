package free.hsn.core;

import java.util.HashMap;
import java.util.Map;

import free.hsn.common.HsnProperties;

/**
 * 建造中类
 *
 * 需提供高灵活性配置
 */
public class HsnServer {

	// 执行从Selector读写线程池
	
	// 事件处理器 ChannelAdaptor
	
	private int port = 10080;
	
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
		acceptProcessor = AcceptProcessor.newInstance(this);
		channelProcessor = ChannelProcessor.newInstance(this, channelSelectorCount);
	}
	
	public void start() throws Exception {
		acceptProcessor.start();
		channelProcessor.start();
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
