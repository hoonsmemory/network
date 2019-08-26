package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class TCPClient {
	private static String SERVER_IP = "192.168.1.22";
	private static int SERVER_PORT = 5000;

	public static void main(String[] args) {

		Socket socket = null;
        Scanner scan = null;
        
		try {
			// 1. socket 생성
			socket = new Socket();

			//1-1. socket buffer size 확인
			int receiveBufferSize =  socket.getReceiveBufferSize();
			int sendBufferSIze = socket.getSendBufferSize();
			
			System.out.println(receiveBufferSize + " : " + sendBufferSIze);
			
			//1-2. socket buffer size 변경
			socket.setReceiveBufferSize(1024 * 10);
			socket.setSendBufferSize(1024 * 10);
			
			//1-3. socket buffer size 재확인
			receiveBufferSize =  socket.getReceiveBufferSize();
			sendBufferSIze = socket.getSendBufferSize();
			
			//1-4.  SO_NODELAY(Nagle Algorithm off)
			socket.setTcpNoDelay(true);
			
			//1-5. SO_TIMEOUT
			socket.setSoTimeout(1000);
			
			System.out.println(receiveBufferSize + " : " + sendBufferSIze);
		
			
			// 2. 서버연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(inetSocketAddress);
			System.out.println("[TCPClient] connected");

			// 3.IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			//while (true) {
				// 4. 쓰기
				String data = " Hello World\n";
				os.write(data.getBytes("UTF-8"));

				// 5. 데이터 읽기
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer); // 크기가 항상 같지 않으므로 체크
				if (readByteCount == -1) {
					System.out.println("[TCPClient] closed by client");
					return;
				}

				// encording(InputStreamReader 대체)
				data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("[TCPClient] received :" + data);
			//}
		} catch (SocketTimeoutException e) {
			System.out.println("[TCPClient] time out");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
