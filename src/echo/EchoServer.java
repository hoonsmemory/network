package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoServer {
	private static int SERVER_PORT = 8000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
 
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 2. Binding : Socket에 SocketAddress(IPAddress + Port) 바인딩한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, SERVER_PORT);
			serverSocket.bind(inetSocketAddress);
			log("binding " + inetAddress.getHostAddress() + ":" + SERVER_PORT);

			
			// 3. accept : 클라이언트로부터 연결요청(Connect)을 기다린다.
			while(true) {
				Socket socket = serverSocket.accept(); // Blocking
				new EchoServerReceiveThread(socket).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 8. serverSocket 자원정리
				if (serverSocket != null && serverSocket.isClosed() == false)
					serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void log(String log) {
		System.out.println("[Echo Server" + Thread.currentThread().getId() +"] " + log);
	}

}
