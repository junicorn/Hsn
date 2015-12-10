package free.hsn.component;

import java.nio.channels.SocketChannel;

import free.hsn.core.HsnServer;

public class ChannelSession {
	
	@SuppressWarnings("unused")
	private HsnServer server;
	
	@SuppressWarnings("unused")
	private SocketChannel socketChannel;

	private ChannelSession(HsnServer server, SocketChannel socketChannel) {
		this.server = server;
		this.socketChannel = socketChannel;
	}
	
	public static ChannelSession open(HsnServer server, SocketChannel socketChannel) {
		return new ChannelSession(server, socketChannel);
	}
}
