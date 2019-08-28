package chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static int PORT = 8000;
	private static String SERVER_IP = "127.0.0.1";
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<Writer> listWriters = new ArrayList<Writer>();
		
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩
			//InetAddress hostAddress = InetAddress.getLocalHost();
			serverSocket.bind(new InetSocketAddress(SERVER_IP, PORT));
			log("binding " + SERVER_IP + ":" + PORT);
		
			// 3. 요청 대기
			while(true) {
				Socket socket = serverSocket.accept(); // Blocking
				new ChatServerThread(socket, listWriters).start();
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
		System.out.println("[Server" + Thread.currentThread().getId() +"] " + log);
	}

}
