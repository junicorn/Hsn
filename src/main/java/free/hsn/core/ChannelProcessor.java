package free.hsn.core;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import free.hsn.common.HsnThreadFactory;
import free.hsn.component.ChannelSelector;
import free.hsn.component.ChannelSession;

public class ChannelProcessor {
	
	private HsnServer server;

	private int channelSelectorCount;
	
	private ChannelSelector[] channelSelectors;
	
	private ExecutorService channelExecutor;
	
	private ChannelProcessor(HsnServer server, int channelSelectorCount) {
		this.server = server;
		this.channelSelectorCount = channelSelectorCount;
	}
	
	static ChannelProcessor newInstance(HsnServer server, int channelSelectorCount) {
		return new ChannelProcessor(server, channelSelectorCount);
	}
	
	private void init() throws IOException {
		channelSelectors = new ChannelSelector[channelSelectorCount];
		for (int i = 0; i < channelSelectors.length; i++) {
			ChannelSelector channelSelector = ChannelSelector.newInstance(server);
			channelSelectors[i] = channelSelector;
		}
		
		channelExecutor = buildChannelExecutor();
	}
	
	void start() throws IOException {
		init();
		
		for (int i = 0; i < channelSelectors.length; i++) {
			channelExecutor.submit(channelSelectors[i]);
		}
	}
	
	public void registerChannelSelector(SelectableChannel channel, int interestOps, ChannelSession channelSession) {
		takeChannelSelector().registerChannel(channel, interestOps, channelSession);
	}
	
	private ChannelSelector takeChannelSelector() {
		return channelSelectors[new Random().nextInt(channelSelectorCount)];
	}
	
	private ExecutorService buildChannelExecutor() {
		return Executors.newFixedThreadPool(channelSelectorCount, HsnThreadFactory.buildChannelSelectorFactory());
	}
}
