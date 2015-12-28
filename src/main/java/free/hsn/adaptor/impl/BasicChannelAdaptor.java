package free.hsn.adaptor.impl;

import java.nio.charset.Charset;

import free.hsn.adaptor.ChannelAdaptor;
import free.hsn.component.ChannelSession;

public class BasicChannelAdaptor implements ChannelAdaptor {

	@Override
	public void onConnected(ChannelSession channelSession) {
		System.out.println("On Connected.");
	}

	@Override
	public void onMessage(ChannelSession channelSession) {
		System.out.println("On Message.");
		
		channelSession.write(Charset.forName("UTF-8").decode(channelSession.read()).toString().getBytes());
	}

	@Override
	public void onExeception(ChannelSession channelSession, Throwable throwable) {
		System.out.println("On Exeception.");
		
		throwable.printStackTrace();
	}

	@Override
	public void onClosed(ChannelSession channelSession) {
		System.out.println("On Close.");
	}
}
