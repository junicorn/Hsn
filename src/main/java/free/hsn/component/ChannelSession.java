package free.hsn.component;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import free.hsn.core.HsnServer;

public class ChannelSession {
	
	@SuppressWarnings("unused")
	private HsnServer server;
	
	@SuppressWarnings("unused")
	private SocketChannel socketChannel;
	
	private SelectionKey selectionKey;
	
	@SuppressWarnings("unused")
	private ByteBuffer readBuffer;
	
	private int taskQueueIndex;

	ChannelSession(HsnServer server, SocketChannel socketChannel) {
		this.server = server;
		this.socketChannel = socketChannel;
	}
	
	public void allocateReadBuffer(ByteBuffer readBuffer) {
		this.readBuffer = readBuffer;
	}
	
	public void setTaskQueueIndex(int taskQueueIndex) {
		this.taskQueueIndex = taskQueueIndex;
	}
	
	public int getTaskQueueIndex() {
		return taskQueueIndex;
	}

	public SelectionKey getSelectionKey() {
		return selectionKey;
	}

	public void setSelectionKey(SelectionKey selectionKey) {
		this.selectionKey = selectionKey;
	}
}
