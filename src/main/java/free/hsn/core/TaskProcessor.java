package free.hsn.core;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import free.hsn.adaptor.BasicChannelAdaptor;
import free.hsn.adaptor.ChannelAdaptor;
import free.hsn.buffer.BufferPool;
import free.hsn.common.HsnProperties;
import free.hsn.common.HsnThreadFactory;
import free.hsn.task.ChannelReadTask;
import free.hsn.task.ChannelTask;

public class TaskProcessor {
	
	private HsnServer server;
	
	private Class<? extends ChannelAdaptor> channelAdaptor = BasicChannelAdaptor.class;
	
	private BufferPool bufferPool;
	
	private ThreadPoolExecutor taskExecutor;
	
	private BlockingQueue<ChannelTask>[] channelTaskQueues;
	
	TaskProcessor(HsnServer server) {
		this.server = server;
		this.bufferPool = buildBufferPool();
		this.taskExecutor = buildChannelExecutor();
		this.channelTaskQueues = buildChannelTaskQueues();
	}
	
	void init() throws Exception {
		bufferPool.prestartCorePool();

		for (int i = 0; i < channelTaskQueues.length; i++) {
			taskExecutor.submit(new QueueTask(channelTaskQueues[i]));
		}
	}
	
	public void start() throws Exception {
		init();
	}

	void setChannelAdaptor(Class<? extends ChannelAdaptor> channelAdaptor) {
		this.channelAdaptor = channelAdaptor;
	}
	
	/**
	 * TODO 未池化多例
	 */
	public ChannelAdaptor channelAdaptor() {
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
		int channelThreadCount = server.channelThreadCount();
		ThreadFactory channelHandlerFactory = HsnThreadFactory.buildChannelHandlerFactory();
		
		return (ThreadPoolExecutor) Executors.newFixedThreadPool(channelThreadCount, channelHandlerFactory);
	}

	private BlockingQueue<ChannelTask>[] buildChannelTaskQueues() {
		@SuppressWarnings("unchecked")
		BlockingQueue<ChannelTask>[] channelTaskQueues = new LinkedBlockingQueue[server.channelThreadCount()];
		for (int i = 0; i < channelTaskQueues.length; i++) {
			channelTaskQueues[i] = new LinkedBlockingQueue<ChannelTask>();
		}
		
		return channelTaskQueues;
	}

	public int calcTaskQueueIndex() {
		return new Random().nextInt(server.channelThreadCount());
	}

	public void processor(ChannelReadTask channelReadTask) {
		channelTaskQueues[channelReadTask.taskQueueIndex()].add(channelReadTask);
	}
	
	private static class QueueTask implements Runnable {

		private boolean stop;
		
		private BlockingQueue<ChannelTask> channelTaskQueue;
	
		public QueueTask(BlockingQueue<ChannelTask> channelTaskQueue) {
			this.channelTaskQueue = channelTaskQueue;
		}
		
		@Override
		public void run() {
			while (!stop) {
				try {
					channelTaskQueue.take().run();
				} catch (InterruptedException e) {
					stop = true;
				} catch (Exception e) {
					// TODO Log
					e.printStackTrace();
					
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						// Do nothing.
					}
				}
			}
			
			// TODO Log Interrupted
		}
	}
}
