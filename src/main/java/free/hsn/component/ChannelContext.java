package free.hsn.component;

import java.nio.ByteBuffer;

public class ChannelContext {

	private ChannelSession channelSession;
	
	ChannelContext(ChannelSession channelSession) {
		this.channelSession = channelSession;
	}
	
	public ByteBuffer read() {
		return channelSession.read();
	}
	
	public void write(byte[] bytes) {
		channelSession.write(bytes);
	}
	
	public void flush() {
		channelSession.flush();
	}
	
	public void close() {
		channelSession.needClose(true);
	}

}
