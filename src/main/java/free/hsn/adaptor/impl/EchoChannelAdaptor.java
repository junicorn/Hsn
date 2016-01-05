package free.hsn.adaptor.impl;

import java.nio.charset.Charset;

import free.hsn.component.ChannelSession.ChannelContext;

public class EchoChannelAdaptor extends StandardChannelAdaptor {

	@Override
	public void onMessage(ChannelContext channelContext) {
		channelContext.write(Charset.forName("UTF-8").decode(channelContext.read()).toString().getBytes());
		channelContext.close();
	}
}
