package free.hsn.test;

import free.hsn.adaptor.impl.EchoChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		// 监听端口10080
		HsnServer server = new HsnServer(10080);
		// 设置Channel适配器
		server.setChannelAdaptor(EchoChannelAdaptor.class);
		// 启动Hsn
		server.start();
	}
}