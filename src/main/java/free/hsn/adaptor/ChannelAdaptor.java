package free.hsn.adaptor;

import free.hsn.component.ChannelContext;

public interface ChannelAdaptor {

	/**
	 * 连接回调
	 */
	public void onConnected(ChannelContext channelContext);
	
	/**
	 * 数据回调
	 */
	public void onMessage(ChannelContext channelContext);
	
	/**
	 * 异常回调
	 */
	public void onExeception(ChannelContext channelContext, Throwable throwable);
	
	/**
	 * 关闭回调
	 */
	public void onClosed(ChannelContext channelContext);
	
}
