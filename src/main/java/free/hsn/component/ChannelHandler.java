package free.hsn.component;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import free.hsn.core.HsnServer;

public class ChannelHandler {
	
	/**
	 * Handler accpet
	 * 
	 * Handle by current thread.
	 * Create a Session for every connection.
	 */
	public static void handlerAccpet(HsnServer server, SelectionKey key) throws Exception {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		
		ChannelSession channelSession = createSession(server, socketChannel);
		
		server.channelProcessor().registerChannelSelector(socketChannel, SelectionKey.OP_READ, channelSession);
	}
	
	private static ChannelSession createSession(HsnServer server, SocketChannel socketChannel) throws Exception {
		ChannelSession channelSession = ChannelSession.open(server, socketChannel);
		channelSession.allocateReadBuffer(server.workProcessor().newBuffer());
		
		return channelSession;
	}
}
