package free.hsn.task.impl;

import java.io.IOException;

import free.hsn.component.ChannelSession;

public class ChannelReadTask extends AbstractChannelTask {
	
	public ChannelReadTask(ChannelSession channelSession) {
		super(channelSession);
	}

	@Override
	public void run() {
		channelSession.clearReadBuffer();

		try {
			if (channelSession.readChannel() == -1) {
				channelSession.close();
			}
		} catch (IOException e) {
			channelSession.onExeception(e);
		}
		
		channelSession.filpReadBuffer();
		
		channelSession.onMessage();
		
		channelSession.flush();

		channelSession.readable();
	}
}
