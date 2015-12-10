package free.hsn.component;

import java.io.IOException;
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
	public static void handlerAccpet(HsnServer server, SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		
		ChannelSession channelSession = ChannelSession.open(server, socketChannel);
		
		server.channelProcessor().registerChannelSelector(socketChannel, SelectionKey.OP_READ, channelSession);
	}
}
