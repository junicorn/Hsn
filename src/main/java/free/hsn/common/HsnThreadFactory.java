package free.hsn.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class HsnThreadFactory {
	
	public static ThreadFactory buildAcceptSelectorFactory() {
		return new AcceptSelectorFactory();
	}

	public static ThreadFactory buildChannelSelectorFactory() {
		return new ChannelSelectorFactory();
	}

	public static ThreadFactory buildChannelHandlerFactory() {
		return new ChannelHandlerFactory();
	}
	
	private static class AcceptSelectorFactory implements ThreadFactory {
		
		private static final String mark = "AcceptSelector";

		@Override
		public Thread newThread(Runnable runnable) {
			Thread thread = new Thread(runnable);
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.setName(mark);
			
			return thread;
		}
	}

	private static class ChannelSelectorFactory implements ThreadFactory {
		
		private static final String mark = "ChannelSelector-";
		
		private final AtomicInteger id = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable runnable) {
			Thread thread = new Thread(runnable);
			thread.setDaemon(true);
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.setName(mark + id.incrementAndGet());
			
			return thread;
		}
	}
	
	private static class ChannelHandlerFactory implements ThreadFactory {
		
		private static final String mark = "ChannelHandler-";
		
		private final AtomicInteger id = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable runnable) {
			Thread thread = new Thread(runnable);
			thread.setDaemon(true);
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.setName(mark + id.incrementAndGet());
			
			return thread;
		}
	}
}
