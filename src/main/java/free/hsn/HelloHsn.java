package free.hsn;

import free.hsn.adaptor.impl.EchoChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hello Hsn.");
		
		HsnServer server = new HsnServer(10080);
		server.setChannelSelectorCount(16);
		server.setBufferPoolSize(4096);
		server.setChannelThreadCount(2048);
		server.setChannelAdaptor(EchoChannelAdaptor.class);
		server.start();
	}
}
