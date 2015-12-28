package free.hsn.component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import free.hsn.core.HsnServer;

public class ChannelSession {
	
	private HsnServer server;
	
	private SocketChannel socketChannel;
	
	private SelectionKey selectionKey;
	
	private ByteBuffer readBuffer;

	private ByteBuffer writeBuffer;
	
	private int taskQueueIndex;
	
	private boolean needFlush;

	ChannelSession(HsnServer server, SocketChannel socketChannel) {
		this.server = server;
		this.socketChannel = socketChannel;
	}
	
	public HsnServer server() {
		return server;
	}
	
	public void allocateReadBuffer(ByteBuffer readBuffer) {
		this.readBuffer = readBuffer;
	}

	public void allocateWriteBuffer(ByteBuffer writeBuffer) {
		this.writeBuffer = writeBuffer;
	}
	
	public void taskQueueIndex(int taskQueueIndex) {
		this.taskQueueIndex = taskQueueIndex;
	}
	
	public SocketChannel socketChannel() {
		return socketChannel;
	}

	public int taskQueueIndex() {
		return taskQueueIndex;
	}

	public SelectionKey selectionKey() {
		return selectionKey;
	}

	public void selectionKey(SelectionKey selectionKey) {
		this.selectionKey = selectionKey;
	}
	
	private void needFlush(boolean needFlush) {
		this.needFlush = needFlush;
	}

	private boolean needFlush() {
		return needFlush;
	}
	
	public void flush() {
		if (needFlush()) {
			writeable();
		}
		
		needFlush(false);
	}
	
	public int readChannel() throws IOException {
		int length = socketChannel.read(readBuffer);
		if (length == -1) {
			return length;
		} else {
			while (!readBuffer.hasRemaining()) {
				ByteBuffer newBuffer = ByteBuffer.allocate(readBuffer.limit() * 2);
				
				readBuffer.flip();
				newBuffer.put(readBuffer);
				
				readBuffer = newBuffer;
				
				length += socketChannel.read(readBuffer);
			}
		}
		
		return length;
	}
	
	public int writeChannel() throws IOException {
		return socketChannel.write(writeBuffer);
	}
	
	public ByteBuffer read() {
		return readBuffer;
	}
	
	public void write(byte[] bytes) {
		if (writeBuffer.remaining() >= bytes.length) {
			writeBuffer.put(bytes);
		} else {
			ByteBuffer newWriteBuffer = ByteBuffer.allocate(writeBuffer.capacity() + bytes.length * 2);
			newWriteBuffer.put(writeBuffer);
			newWriteBuffer.put(bytes);
			
			writeBuffer = newWriteBuffer;
		}
		
		needFlush(true);
	}
	
	public void close() throws IOException {
		onClosed();
		
		selectionKey.cancel();
		socketChannel.close();
	}
	
	public void filpReadBuffer() {
		readBuffer.flip();
	}

	public void filpWriteBuffer() {
		writeBuffer.flip();
	}
	
	public void clearReadBuffer() {
		readBuffer.clear();
	}

	public void clearWriteBuffer() {
		writeBuffer.clear();
	}
	
	public void readable() {
		selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_READ);

		wakeupSelector();
	}

	public void writeable() {
		selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
		
		wakeupSelector();
	}
	
	private void wakeupSelector() {
		selectionKey.selector().wakeup();
	}
	
	public void onConnected() {
		server.taskProcessor().channelAdaptor().onConnected(this);
	}
	
	public void onMessage() {
		server.taskProcessor().channelAdaptor().onMessage(this);
	}
	
	public void onClosed() {
		server.taskProcessor().channelAdaptor().onClosed(this);
	}
	
	public void onExeception(Throwable throwable) {
		server.taskProcessor().channelAdaptor().onExeception(this, throwable);
	}
}
