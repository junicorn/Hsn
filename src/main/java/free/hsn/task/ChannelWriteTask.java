package free.hsn.task;

import java.io.IOException;

import free.hsn.component.ChannelSession;

public class ChannelWriteTask extends AbstractChannelTask {
	
	public ChannelWriteTask(ChannelSession channelSession) {
		super(channelSession);
	}

	@Override
	public void run() {
		try {
			channelSession.writeChannel();
		} catch (IOException e) {
			channelSession.onExeception(e);
		}

		channelSession.clearWriteBuffer();
		
		try {
			channelSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
