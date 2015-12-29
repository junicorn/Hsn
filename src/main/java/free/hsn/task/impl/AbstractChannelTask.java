package free.hsn.task.impl;

import free.hsn.component.ChannelSession;
import free.hsn.task.ChannelTask;

public abstract class AbstractChannelTask implements ChannelTask {

	protected ChannelSession channelSession;

	@Override
	public ChannelSession channelSession() {
		return channelSession;
	}
	
	@Override
	public int taskQueueIndex() {
		return channelSession.taskQueueIndex();
	}

	protected AbstractChannelTask(ChannelSession channelSession) {
		super();
		this.channelSession = channelSession;
	}
}
