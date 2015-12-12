package free.hsn.component;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import free.hsn.core.HsnServer;

public class ChannelSelector implements Runnable {
	
	private HsnServer server;

	private Selector selector;
	
	private final Queue<RegisterChannel> registerChannels = new LinkedBlockingQueue<RegisterChannel>();
	
	private ChannelSelector(HsnServer server) throws IOException {
		this.server = server;
		this.selector = Selector.open();
	}
	
	public static ChannelSelector newInstance(HsnServer server) throws IOException {
		return new ChannelSelector(server);
	}
	
	public void registerChannel(SelectableChannel channel, int interestOps, ChannelSession channelSession) {
		registerChannels.offer(new RegisterChannel(channel, interestOps, channelSession));
		selector.wakeup();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				selector.select();
			} catch (IOException e) {
				continue;
			}
			
			processRegisterChannels();
			
			Set<SelectionKey> keys = selector.selectedKeys();
			for (SelectionKey key : keys) {
				try{ 
					if (key.isAcceptable()) {
						ChannelHandler.handlerAccpet(server, key);
					} else if (key.isReadable()) {
						// TODO
//						ChannelHandler.handlerRead(key);
					} else if (key.isWritable()) {
//						ChannelHandler.handlerWrite(key);
					}
				} catch(Throwable throwable){ 
//					ChannelHandler.handlerExeception(key, throwable);
				}
			}
			
			// TODO when channel is closeed(By client), if the selector will catch it? 
			
			keys.clear();
		}
	}
	
	private void processRegisterChannels() {
		RegisterChannel registerChannel = null;
		while ((registerChannel = registerChannels.poll()) != null) {
			try {
				registerChannel.channel.register(selector, registerChannel.interestOps, registerChannel.channelSession);
			} catch (ClosedChannelException e) {
				continue;
			}
		}
	}

	private static class RegisterChannel {
		
		private SelectableChannel channel;
		
		private int interestOps;
		
		private ChannelSession channelSession;
		
		public RegisterChannel(SelectableChannel channel, int interestOps, ChannelSession channelSession) {
			this.channel = channel;
			this.interestOps = interestOps;
			this.channelSession = channelSession;
		}
	}
}
