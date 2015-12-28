package free.hsn.task.impl;

import java.io.IOException;

import free.hsn.component.ChannelSession;

public class ChannelWriteTask extends AbstractChannelTask {
	
	public ChannelWriteTask(ChannelSession channelSession) {
		super(channelSession);
	}

	@Override
	public void run() {
		channelSession.filpWriteBuffer();
		
		try {
			channelSession.writeChannel();
		} catch (IOException e) {
			channelSession.onExeception(e);
		}

		channelSession.clearWriteBuffer();
		
		// TODO
		try {
			channelSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
