package free.hsn;

import free.hsn.adaptor.impl.PMChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		HsnServer server = new HsnServer(10080, 200);
		
//		server.setBufferPoolSize(4096);
//		server.setChannelSelectorCount(1);
//		server.setChannelThreadCount(1);
		
		server.setChannelAdaptor(PMChannelAdaptor.class);
		server.start();
	}
}
