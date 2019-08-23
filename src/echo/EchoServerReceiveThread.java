package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {

	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// 상대편 Address, Port 얻어오기
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();// 다운케스팅..

		EchoServer.log("connected from client " + inetRemoteSocketAddress.getAddress().getHostAddress() + ":"
				+ inetRemoteSocketAddress.getPort());

		try {
			// 4. I/O Stream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {

				// 5. 데이터 읽기(수신)
				String data = br.readLine();
				
				if (data == null) {
					EchoServer.log("closed by client");
					break;
				}
				EchoServer.log("received : " + data);

				// 6. 데이터 쓰기(송신)
				pw.println(data);
			}
			
		} catch (SocketException e) {
			EchoServer.log("abnormal closed by client");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7. data socket 자원정리
			if (socket != null && socket.isClosed() == false)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}
