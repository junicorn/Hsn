package free.hsn.task;

import java.io.IOException;

import free.hsn.component.ChannelSession;

public class ChannelReadTask extends AbstractChannelTask {
	
	public ChannelReadTask(ChannelSession channelSession) {
		super(channelSession);
	}

	@Override
	public void run() {
		try {
			if (channelSession.readChannel() == -1) {
				channelSession.close();
			}
		} catch (IOException e) {
			channelSession.onExeception(e);
		}
		
		channelSession.onMessage();
		
		channelSession.readable();
	}
}
