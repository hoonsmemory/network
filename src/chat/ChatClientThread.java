package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;


public class ChatClientThread extends Thread {
	String nickname   = null;
	Socket socket = null;
	BufferedReader bufferedReader = null;
	
	public ChatClientThread(BufferedReader bufferedReader, Socket socket, String nickname) {
		this.bufferedReader = bufferedReader;
		this.socket = socket;
		this.nickname = nickname;
	}
	
	@Override
	public void run() {
		try {
					
			while(true) {		
				String message = bufferedReader.readLine();
	
				if(message == null) {		
					break;
				}
				 
				String[] tokens = message.split(":");
				if(!nickname.equals(tokens[0])) {
					System.out.println(message);
				}
				
			}
		
		} catch (SocketException e) {
			ChatServer.log("서버에서 비정상적인 종료를 하여 종료하겠습니다.");
			System.exit(1);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}

