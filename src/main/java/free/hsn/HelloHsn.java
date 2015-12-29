package free.hsn;

import free.hsn.adaptor.impl.EchoChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hello Hsn.");
		
		HsnServer server = new HsnServer(10080);
		server.setBufferPoolSize(4096);
		server.setChannelSelectorCount(2);
		server.setChannelThreadCount(4096);
		server.setChannelAdaptor(EchoChannelAdaptor.class);
		server.start();
	}
}
