package free.hsn.core;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import free.hsn.adaptor.ChannelAdaptor;
import free.hsn.buffer.BufferPool;
import free.hsn.common.HsnProperties;
import free.hsn.common.HsnThreadFactory;

public class WorkProcessor {
	
	private HsnServer server;
	
	/**
	 * Channel Adaptor
	 */
	private Class<? extends ChannelAdaptor> channelAdaptor;
	
	private BufferPool bufferPool;
	
	private ThreadPoolExecutor workExecutor;
	
	private WorkProcessor(HsnServer server) {
		this.server = server;
		this.bufferPool = buildBufferPool();
		this.workExecutor = buildChannelExecutor();
	}
	
	static WorkProcessor newInstance(HsnServer server) {
		return new WorkProcessor(server);
	}
	
	void init() throws Exception {
		workExecutor.prestartCoreThread();
		bufferPool.prestartCorePool();
	}

	void setChannelAdaptor(Class<? extends ChannelAdaptor> channelAdaptor) {
		this.channelAdaptor = channelAdaptor;
	}
	
	ChannelAdaptor channelAdaptor() {
		ChannelAdaptor channelAdaptor = null;
		try {
			channelAdaptor = this.channelAdaptor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return channelAdaptor;
	}
	
	public ByteBuffer newBuffer() throws Exception {
		return bufferPool.borrowObject();
	}
	
	private BufferPool buildBufferPool() {
		int corePoolSize = server.getBufferPoolSize();
		int maxPoolSize = Integer.MAX_VALUE;
		int keepAliveTime = HsnProperties.DEFAULT_BUFFER_POOL_KEEPALIVE;
		int bufferSize = server.getBufferPoolSize();
		
		return new BufferPool(corePoolSize, maxPoolSize, keepAliveTime, bufferSize);
	}
	
	private ThreadPoolExecutor buildChannelExecutor() {
		int channelSelectorCount = server.channelSelectorCount();
		ThreadFactory channelHandlerFactory = HsnThreadFactory.buildChannelHandlerFactory();
		
		return (ThreadPoolExecutor) Executors.newFixedThreadPool(channelSelectorCount, channelHandlerFactory);
	}
}
