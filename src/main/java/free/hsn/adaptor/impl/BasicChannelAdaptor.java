package free.hsn.adaptor.impl;

import java.nio.charset.Charset;

import free.hsn.adaptor.ChannelAdaptor;
import free.hsn.component.ChannelSession.ChannelContext;

public class BasicChannelAdaptor implements ChannelAdaptor {

	@Override
	public void onConnected(ChannelContext channelContext) {
		System.out.println("On Connected.");
	}

	@Override
	public void onMessage(ChannelContext channelContext) {
		System.out.println("On Message.");
		
		channelContext.write(Charset.forName("UTF-8").decode(channelContext.read()).toString().getBytes());
		
		channelContext.close();
	}

	@Override
	public void onExeception(ChannelContext channelContext, Throwable throwable) {
		System.out.println("On Exeception.");
		
		throwable.printStackTrace();
	}

	@Override
	public void onClosed(ChannelContext channelContext) {
		System.out.println("On Close.");
	}
}
