package free.hsn;

import free.hsn.adaptor.impl.BasicChannelAdaptor;
import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hello Hsn.");
		
		HsnServer server = new HsnServer(10080);
		server.setChannelAdaptor(BasicChannelAdaptor.class);
		server.start();
	}
}
