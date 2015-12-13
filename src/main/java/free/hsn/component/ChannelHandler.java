package free.hsn.component;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import free.hsn.core.HsnServer;
import free.hsn.task.ChannelReadTask;

public class ChannelHandler {
	
	/**
	 * Handler accpet
	 * 
	 * Handle by current thread.
	 * Create a Session for every connection.
	 */
	public static void handlerAccpet(HsnServer server, SelectionKey selectionKey) throws Exception {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		
		ChannelSession channelSession = createSession(server, socketChannel, selectionKey);
		
		server.channelProcessor().registerChannel(socketChannel, SelectionKey.OP_READ, channelSession);
	}
	
	public static void handlerRead(HsnServer server, SelectionKey selectionKey) {
		selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_READ));
		
		server.taskProcessor().processor(new ChannelReadTask((ChannelSession) selectionKey.attachment()));
	}
	
	private static ChannelSession createSession(HsnServer server, SocketChannel socketChannel, 
																  SelectionKey selectionKey) throws Exception {
		ChannelSession channelSession = new ChannelSession(server, socketChannel);
		channelSession.allocateReadBuffer(server.taskProcessor().newBuffer());
		channelSession.setTaskQueueIndex(server.taskProcessor().calcTaskQueueIndex());
		channelSession.setSelectionKey(selectionKey);
		
		return channelSession;
	}
}
