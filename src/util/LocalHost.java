package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName    = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			byte[] ipAddresses = inetAddress.getAddress();
			
			for(byte ipAddress : ipAddresses) {
				System.out.print(ipAddress & 0x000000ff);
				System.out.print(".");
			}
			System.out.println(hostName);
			System.out.println(hostAddress);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
