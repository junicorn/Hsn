package free.hsn.buffer;

import java.nio.ByteBuffer;

public class ChannelBuffer {

	private ByteBuffer byteBuffer;
	
	public ChannelBuffer(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer;
	}
	
	public ByteBuffer byteBuffer() {
		return byteBuffer;
	}
}
