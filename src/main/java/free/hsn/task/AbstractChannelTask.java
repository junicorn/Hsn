package free.hsn.task;

import free.hsn.component.ChannelSession;

public abstract class AbstractChannelTask implements ChannelTask {

	protected ChannelSession channelSession;

	protected AbstractChannelTask(ChannelSession channelSession) {
		super();
		this.channelSession = channelSession;
	}
	
	@Override
	public int taskQueueIndex() {
		return channelSession.taskQueueIndex();
	}
}
