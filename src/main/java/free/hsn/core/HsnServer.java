package free.hsn.core;

import java.util.HashMap;
import java.util.Map;

import free.hsn.adaptor.ChannelAdaptor;
import free.hsn.common.HsnProperties;

/**
 * 建造中类
 *
 * 需提供高灵活性配置
 */
public class HsnServer {
	
	// 事件处理器 ChannelAdaptor
	
	private int port = 10080;
	
	private AcceptProcessor acceptProcessor;
	
	private ChannelProcessor channelProcessor;
	
	private TaskProcessor taskProcessor;
	
	/**
	 * Socket options
	 */
	private Map<Integer, Object> socketOptions = new HashMap<Integer, Object>();
	
	/**
	 * Count of channel selector
	 */
	private int channelSelectorCount = HsnProperties.DEFAULT_CHANNEL_SELECTOR_COUNT;

	/**
	 * Count of channel thread
	 * 
	 * TODO 构造时确定 不可修改
	 */
	private int channelThreadCount = HsnProperties.DEFAULT_CHANNEL_SELECTOR_COUNT;
	
	private int bufferSize = HsnProperties.DEFAULT_BUFFER_SIZE;

	private int bufferPoolSize = HsnProperties.DEFAULT_BUFFER_POOL_SIZE;

	public HsnServer() {
		acceptProcessor = new AcceptProcessor(this);
		channelProcessor = new ChannelProcessor(this);
		taskProcessor = new TaskProcessor(this);
	}
	
	/**
	 * start
	 */
	public void start() throws Exception {
		acceptProcessor.start();
		channelProcessor.start();
	}
	
	public ChannelProcessor channelProcessor() {
		return channelProcessor;
	}

	public TaskProcessor taskProcessor() {
		return taskProcessor;
	}
	
	public int channelSelectorCount() {
		return channelSelectorCount;
	}

	public int channelThreadCount() {
		return channelThreadCount;
	}
	
	public void setChannelAdaptor(Class<? extends ChannelAdaptor> channelAdaptor) {
		taskProcessor.setChannelAdaptor(channelAdaptor);
	}

	public int port() {
		return port;
	}

	public Map<Integer, Object> getSocketOptions() {
		return socketOptions;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getBufferPoolSize() {
		return bufferPoolSize;
	}

	public void setBufferPoolSize(int bufferPoolSize) {
		this.bufferPoolSize = bufferPoolSize;
	}
}
