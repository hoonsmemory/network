package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;


public class ChatClientThread extends Thread {
	String name   = null;
	Socket socket = null;
	BufferedReader bufferedReader = null;
	
	public ChatClientThread(BufferedReader bufferedReader, Socket socket) {
		this.bufferedReader = bufferedReader;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
					
			while(true) {
				String message = bufferedReader.readLine();
				System.out.println(message);
			}
		
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

