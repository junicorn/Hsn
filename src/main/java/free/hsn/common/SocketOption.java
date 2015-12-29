package free.hsn.common;

import java.net.SocketOptions;

public enum SocketOption {
	
	SO_TIMEOUT(SocketOptions.SO_TIMEOUT),
	
	SO_REUSEADDR(SocketOptions.SO_REUSEADDR),
	
	SO_RCVBUF(SocketOptions.SO_RCVBUF);

	private int optId;
	
	private SocketOption(int optId) {
		this.optId = optId;
	}
	
	public int getOptId() {
		return optId;
	}
}
