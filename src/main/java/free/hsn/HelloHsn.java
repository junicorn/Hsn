package free.hsn;

import free.hsn.core.HsnServer;

public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hello Hsn.");
		
		HsnServer server = new HsnServer();
		server.start();
	}
}
