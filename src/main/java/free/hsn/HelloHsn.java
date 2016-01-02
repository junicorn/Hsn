package free.hsn;

import free.hsn.adaptor.impl.EchoChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		// 监听端口10080, 设置Backlog值为200
		HsnServer server = new HsnServer(10080, 200);
		
		// 设置用于处理连接相关操作的Selector数量(默认同CPU核心数量)
		server.setChannelSelectorCount(2);
		
		// 设置用于处理连接相关操作的线程数量(默认为ChannelSelectorCount的两倍)
		server.setChannelThreadCount(4);

		// 设置缓冲区对象池大小(默认1024)
		server.setBufferPoolSize(1024);
		// 设置缓冲区默认容量(默认1024)
		server.setBufferSize(1024);

		// 设置Channel适配器
		server.setChannelAdaptor(EchoChannelAdaptor.class);
		
		// 启动Hsn
		server.start();
	}
}
