package free.hsn;

import free.hsn.adaptor.impl.EchoChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		HsnServer server = new HsnServer(10080);
		
//		server.setChannelSelectorCount(4);
//		server.setChannelThreadCount(8);
//		server.addSocketOption(SocketOption.SO_REUSEADDR, true);
		
		server.setChannelAdaptor(EchoChannelAdaptor.class);
		server.start();
	}
}
