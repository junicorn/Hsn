package free.hsn.adaptor.impl;

import free.hsn.component.ChannelSession.ChannelContext;
import free.hsn.logger.Logger;

public class LogChannelAdaptor extends StandardChannelAdaptor {

	@Override
	public void onConnected(ChannelContext channelContext) {
		Logger.info("On Connected");
	}

	@Override
	public void onMessage(ChannelContext channelContext) {
		Logger.info("On Message");
		channelContext.close();
	}

	@Override
	public void onExeception(ChannelContext channelContext, Throwable throwable) {
		Logger.info("On Exeception");
	}

	@Override
	public void onClosed(ChannelContext channelContext) {
		Logger.info("On Closed");
	}
}
