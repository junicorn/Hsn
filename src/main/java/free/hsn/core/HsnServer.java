package free.hsn.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import free.hsn.adaptor.ChannelAdaptor;
import free.hsn.common.HsnProperties;

public class HsnServer {
	
	private int port;
	
	private boolean isStart;
	
	private int channelSelectorCount = HsnProperties.DEFAULT_CHANNEL_SELECTOR_COUNT;

	private int channelThreadCount = HsnProperties.DEFAULT_CHANNEL_SELECTOR_COUNT;
	
	private int bufferSize = HsnProperties.DEFAULT_BUFFER_SIZE;

	private int bufferPoolSize = HsnProperties.DEFAULT_BUFFER_POOL_SIZE;
	
	private Map<Integer, Object> socketOptions = new HashMap<Integer, Object>();
	
	private AcceptProcessor acceptProcessor;
	
	private ChannelProcessor channelProcessor;
	
	private TaskProcessor taskProcessor;

	public HsnServer(int port) {
		this.port = port;
		this.acceptProcessor = new AcceptProcessor(this);
		this.channelProcessor = new ChannelProcessor(this);
		this.taskProcessor = new TaskProcessor(this);
	}
	
	public synchronized void start() throws Exception {
		if (!isStart) {
			acceptProcessor.start();
			channelProcessor.start();
			taskProcessor.start();
		}
	}
	
	public synchronized void close() throws IOException {
		if (isStart) {
			acceptProcessor.close();
			channelProcessor.close();
			taskProcessor.close();
		}
	}
	
	public int port() {
		return port;
	}
	
	public int channelSelectorCount() {
		return channelSelectorCount;
	}

	public void setChannelSelectorCount(int channelSelectorCount) {
		this.channelSelectorCount = channelSelectorCount;
	}

	public int channelThreadCount() {
		return channelThreadCount;
	}
	
	public void setChannelThreadCount(int channelThreadCount) {
		this.channelThreadCount = channelThreadCount;
	}
	
	public void setChannelAdaptor(Class<? extends ChannelAdaptor> channelAdaptor) {
		taskProcessor.setChannelAdaptor(channelAdaptor);
	}
	
	public void setSocketOption(Integer option, Object optionValue) {
		socketOptions.put(option, optionValue);
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
	
	public ChannelProcessor channelProcessor() {
		return channelProcessor;
	}

	public TaskProcessor taskProcessor() {
		return taskProcessor;
	}
}
