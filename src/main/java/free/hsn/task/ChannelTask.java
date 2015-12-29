package free.hsn.task;

import free.hsn.component.ChannelSession;

public interface ChannelTask {
	
	public void run();
	
	public int taskQueueIndex();
	
	public ChannelSession channelSession();
}
