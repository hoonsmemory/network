package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

//NSLookup : 도메인주소의 IP주소 구하거나 IP의 도메인주소를 알게하는 것.
public class NSLookup {

	public static void main(String[] args) {
		try {
               Scanner scan = new Scanner(System.in);
	           System.out.print("주소를 입력하시오 : ");
	           String domain = scan.nextLine();
	           
	           InetAddress[] inetAddresses = InetAddress.getAllByName(domain);
			
			for(InetAddress inetAddress : inetAddresses) {
				System.out.println(inetAddress.getHostName()); //호스트의 도메인명을 문자열로 반환
				System.out.println(inetAddress.getHostAddress()); //호스트의 IP 주소를 점으로 구분되는 10진수 형태로 반환
				System.out.println(inetAddress.toString());
				System.out.println("------------------------------------");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
