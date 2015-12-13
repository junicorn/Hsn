package free.hsn.task;

import free.hsn.component.ChannelSession;

public class ChannelReadTask implements ChannelTask {
	
	private ChannelSession channelSession;

	public ChannelReadTask(ChannelSession channelSession) {
		super();
		this.channelSession = channelSession;
	}

	@Override
	public void run() {
		// TODO 终于等待你
	}

	@Override
	public int taskQueueIndex() {
		return channelSession.getTaskQueueIndex();
	}

}
