package free.hsn.adaptor.impl;

import free.hsn.component.ChannelSession.ChannelContext;

public class LogChannelAdaptor extends StandardChannelAdaptor {

	@Override
	public void onConnected(ChannelContext channelContext) {
		System.out.println("On Connected");
	}

	@Override
	public void onMessage(ChannelContext channelContext) {
		System.out.println("On Message");
	}

	@Override
	public void onExeception(ChannelContext channelContext, Throwable throwable) {
		System.out.println("On Exeception");
	}

	@Override
	public void onClosed(ChannelContext channelContext) {
		System.out.println("On Closed");
	}
}
