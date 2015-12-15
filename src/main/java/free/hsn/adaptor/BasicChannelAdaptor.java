package free.hsn.adaptor;

import java.nio.ByteBuffer;

import free.hsn.component.ChannelSession;

public class BasicChannelAdaptor implements ChannelAdaptor {

	@Override
	public void onConnected(ChannelSession channelSession) {
		System.out.println("On Connected.");
	}

	@Override
	public void onMessage(ChannelSession channelSession) {
		System.out.println("On Message.");
		
		ByteBuffer buffer = channelSession.read();
		
		System.out.println(new String(buffer.array(), 0, buffer.position()));
		
		channelSession.write(new String(buffer.array(), 0, buffer.position()).getBytes());
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
