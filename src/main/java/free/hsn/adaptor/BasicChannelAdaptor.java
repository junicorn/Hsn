package free.hsn.adaptor;

import java.io.IOException;
import java.nio.ByteBuffer;

import free.hsn.component.ChannelSession;

public class BasicChannelAdaptor implements ChannelAdaptor {

	@Override
	public void onConnected(ChannelSession channelSession) {
		System.out.println("On Connected.");
	}

	@Override
	public void onMessage(ChannelSession channelSession) {
		ByteBuffer buffer = channelSession.read();
		
		System.out.println(new String(buffer.array(), 0, buffer.position()));
		
		try {
			channelSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
