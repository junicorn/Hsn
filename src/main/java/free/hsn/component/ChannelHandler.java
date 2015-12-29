package free.hsn.component;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import free.hsn.core.HsnServer;
import free.hsn.logger.Logger;
import free.hsn.task.impl.ChannelReadTask;
import free.hsn.task.impl.ChannelWriteTask;

public class ChannelHandler {
	
	public static void handlerAccpet(HsnServer server, SelectionKey selectionKey) throws Exception {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		
		Logger.debug(String.format("Accept socketChannel: %s", socketChannel.getRemoteAddress()));
		
		server.channelProcessor().registerChannel(socketChannel, SelectionKey.OP_READ, openSession(server, socketChannel));
	}
	
	public static void handlerRead(HsnServer server, SelectionKey selectionKey) {
		selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_READ));
		
		ChannelSession channelSession = (ChannelSession) selectionKey.attachment();
		channelSession.selectionKey(selectionKey);
		
		server.taskProcessor().processor(new ChannelReadTask(channelSession));
	}
	
	public static void handlerWrite(HsnServer server, SelectionKey selectionKey) {
		selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_WRITE));
		
		server.taskProcessor().processor(new ChannelWriteTask((ChannelSession) selectionKey.attachment()));
	}
	
	private static ChannelSession openSession(HsnServer server, SocketChannel socketChannel) throws Exception {
		ChannelSession channelSession = createSession(server, socketChannel);
		channelSession.onConnected();
		
		return channelSession;
	}
	
	private static ChannelSession createSession(HsnServer server, SocketChannel socketChannel) throws Exception {
		ChannelSession channelSession = new ChannelSession(server, socketChannel);
		channelSession.allocateReadBuffer(server.taskProcessor().newBuffer());
		channelSession.allocateWriteBuffer(server.taskProcessor().newBuffer());
		channelSession.taskQueueIndex(server.taskProcessor().calcTaskQueueIndex());
		
		return channelSession;
	}
}
