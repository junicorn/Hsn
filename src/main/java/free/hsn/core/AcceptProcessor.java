package free.hsn.core;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import free.hsn.common.HsnProperties;
import free.hsn.common.HsnThreadFactory;
import free.hsn.component.ChannelSelector;

public class AcceptProcessor implements Closeable {

	private HsnServer server;

	private ChannelSelector channelSelector;
	
	private ServerSocketChannel serverSocketChannel;
	
	private ExecutorService acceptExecutor;
	
	AcceptProcessor(HsnServer server) {
		this.server = server;
	}
	
	private void init() throws Exception {
		channelSelector = new ChannelSelector(server);
		channelSelector.registerChannel(buildSSC(), SelectionKey.OP_ACCEPT, null);
		
		acceptExecutor = buildAcceptExecutor();
	}
	
	void start() throws Exception {
		init();
		
		acceptExecutor.submit(channelSelector);
	}
	
	private ServerSocketChannel buildSSC() throws Exception {
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		setSocketOptions();
		serverSocketChannel.socket().bind(new InetSocketAddress(server.port()), HsnProperties.BACKLOG);
		
		return serverSocketChannel;
	}
	
	private void setSocketOptions() throws Exception {
		setDefaultOptions();
		setUserOptions();
	}
	
	private void setDefaultOptions() throws SocketException {
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().setPerformancePreferences(HsnProperties.PERFORMANCE_CONNECTIONTIME, 
											   				   HsnProperties.PERFORMANCE_LATENCY, 
											   				   HsnProperties.PERFORMANCE_BANDWIDTH);
	}
	
	private void setUserOptions() throws Exception {
		SocketImpl socketImpl = getSocketImpl();
		for (Map.Entry<Integer, Object> me : server.getSocketOptions().entrySet()) {
			socketImpl.setOption(me.getKey(), me.getValue());
		}
	}
	
	private SocketImpl getSocketImpl() throws Exception {
		Method method = ServerSocket.class.getDeclaredMethod("getImpl", new Class<?>[0]);
		method.setAccessible(true);
		
		return (SocketImpl) method.invoke(serverSocketChannel.socket(), new Object[0]);
	}
	
	private ExecutorService buildAcceptExecutor() {
		return Executors.newSingleThreadExecutor(HsnThreadFactory.buildAcceptSelectorFactory());
	}

	@Override
	public void close() throws IOException {
		acceptExecutor.shutdown();
	}
}
