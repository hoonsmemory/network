package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	public static final int PORT = 8000;
	public static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			//1. socket 생성
			socket = new DatagramSocket(PORT);
			
			//1-1. 현재 서버 시간 생성
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
			String time =format.format(new Date());
			
			while(true) {
				
			//2. data 수신
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			socket.receive(receivePacket);
			

			
			//3. data 처리(확인)
			byte[] data = receivePacket.getData();
			int length = receivePacket.getLength();
			String message = new String(data,0,length,"UTF-8");

			if("".equals(message)) {
				System.out.println("[UDP Echo Server] 현재 시간은 " + time);
			}else {
				System.out.println("[UDP Echo Server] received : " + message);
			}
			

			//4. data 전송
			byte[] sendData = message.getBytes("UTF-8");			
			DatagramPacket sendMessage = new DatagramPacket(sendData, sendData.length,receivePacket.getAddress(),receivePacket.getPort());		
			socket.send(sendMessage);
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
		
	}

}
