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
	
	private ChannelContext channelContext;
	
	private ByteBuffer readBuffer;

	private ByteBuffer writeBuffer;
	
	private boolean isRecoverReadBuffer;
	
	private boolean isRecoverWriteBuffer; 
	
	private int taskQueueIndex;
	
	private boolean needFlush;
	
	private boolean needClose;

	ChannelSession(HsnServer server, SocketChannel socketChannel) {
		this.server = server;
		this.socketChannel = socketChannel;
		this.channelContext = new ChannelContext(this);
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
	
	public void needClose(boolean needClose) {
		this.needClose = needClose;
	}
	
	private boolean needClose() {
		return needClose;
	}
	
	public void close() throws IOException {
		onClosed();
		
		recoverReadBuffer();
		recoverWriteBuffer();
		
		selectionKey.cancel();
		socketChannel.close();
	}
	
	public void checkClose() throws IOException {
		if (needClose()) {
			close();
		}
	}
	
	public int readChannel() throws IOException {
		int length = socketChannel.read(readBuffer);
		if (length == -1) {
			return length;
		} else {
			while (!readBuffer.hasRemaining()) {
				ByteBuffer newReadBuffer = ByteBuffer.allocate(readBuffer.limit() * 2);
				
				readBuffer.flip();
				newReadBuffer.put(readBuffer);
				
				newReadBuffer(newReadBuffer);
				
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
			
			newWriteBuffer(newWriteBuffer);
		}
		
		needFlush(true);
	}
	
	private void newReadBuffer(ByteBuffer newReadBuffer) {
		recoverReadBuffer();
		
		readBuffer = newReadBuffer;
	}

	private void newWriteBuffer(ByteBuffer newWriteBuffer) {
		recoverWriteBuffer();
		
		writeBuffer = newWriteBuffer;
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
	
	private void recoverReadBuffer() {
		if (!isRecoverReadBuffer) {
			server.taskProcessor().recoverBuffer(readBuffer);
		}
		
		isRecoverReadBuffer = true;
	}

	private void recoverWriteBuffer() {
		if (!isRecoverWriteBuffer) {
			server.taskProcessor().recoverBuffer(writeBuffer);
		}
		
		isRecoverWriteBuffer = true;
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
		server.taskProcessor().channelAdaptor().onConnected(channelContext);
	}
	
	public void onMessage() {
		server.taskProcessor().channelAdaptor().onMessage(channelContext);
	}
	
	public void onClosed() {
		server.taskProcessor().channelAdaptor().onClosed(channelContext);
	}
	
	public void onExeception(Throwable throwable) {
		server.taskProcessor().channelAdaptor().onExeception(channelContext, throwable);
	}
}
