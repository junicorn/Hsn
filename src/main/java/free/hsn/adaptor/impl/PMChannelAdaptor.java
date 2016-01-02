package free.hsn.adaptor.impl;

import free.hsn.component.ChannelSession.ChannelContext;

/**
 * Http压测Adaptor
 */
public class PMChannelAdaptor extends StandardChannelAdaptor {
	
	private static final byte[] PM_HTTP_RESPONSE;
	static {
		StringBuilder buf = new StringBuilder();
		buf.append("HTTP/1.1 200 OK\r\n");
		buf.append("Connection: keep-alive\r\n");
		buf.append("Content-Length: 2\r\n");
		buf.append("\r\n");
		buf.append("ok");
		
		PM_HTTP_RESPONSE = buf.toString().getBytes();
	}
	
	@Override
	public void onMessage(ChannelContext channelContext) {
		channelContext.write(PM_HTTP_RESPONSE);
	}
}
