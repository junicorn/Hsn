package free.hsn;

import free.hsn.adaptor.impl.PMChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		HsnServer server = new HsnServer(10080, 200);
		
		server.setChannelSelectorCount(2);
		server.setChannelThreadCount(4);
		
		server.setChannelAdaptor(PMChannelAdaptor.class);
		server.start();
	}
}
