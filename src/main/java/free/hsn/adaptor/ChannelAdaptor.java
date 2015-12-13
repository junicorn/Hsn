package free.hsn.adaptor;

import free.hsn.component.ChannelSession;

public interface ChannelAdaptor {

	/**
	 * 连接回调
	 */
	public void onConnected(ChannelSession channelSession);
	
	/**
	 * 数据回调
	 */
	public void onMessage(ChannelSession channelSession);
	
	/**
	 * 异常回调
	 */
	public void onExeception(ChannelSession channelSession, Throwable throwable);
	
	/**
	 * 关闭回调
	 */
	public void onClosed(ChannelSession channelSession);
	
}
