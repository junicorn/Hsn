package free.hsn.task.impl;

import free.hsn.component.ChannelSession;
import free.hsn.task.ChannelTask;

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
