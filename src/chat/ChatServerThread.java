package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServerThread extends Thread {
	private Socket socket = null;
	private String nickname = null;
	private List<Writer> listWriters = null;
			
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		try {
			//1. 상대편 Address, Port 얻어오기
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();// 다운케스팅..

			ChatServer.log("connected from client " + inetRemoteSocketAddress.getAddress().getHostAddress() + ":"
					+ inetRemoteSocketAddress.getPort());

			// 2. 스트림 얻기
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			
			// 3. 요청 처리
			while (true) {
				String request = bufferedReader.readLine();

				if (request == null) {
					ChatServer.log("클라이언트로 부터 연결 끊김");
					doQuit(printWriter);
					break;
				}
				
				ChatServer.log("received:" + request);

				//4. 프로토콜 분석
				String[] tokens = request.split(":");
				
				if("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);
				}
				else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
				}
				else if("quit".equals(tokens[0])){
					doQuit(printWriter);
				}else {
					ChatServer.log( "에러 : 알 수 없는 요청(" + tokens[0] + ")" );
				}
				

			}

		} catch (SocketException e) {
			ChatServer.log(this.nickname + "님이 나갔습니다.");
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


	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		
		String data = nickname + "님이 참여하였습니다.";
		
		//writer pool에 저장
		addWriter(writer);	
	
		broadcast(data);
		
		

		

	}
	
	private void doMessage(String message) {
		broadcast(nickname + ":" + message);	
	}
	
	private void doQuit(Writer writer) {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}
	
	private void broadcast(String data) {
		synchronized (listWriters) {
			
			for(Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
	
	private void addWriter(Writer writer) {
		synchronized (listWriters) { //동기화 처리
			listWriters.add(writer);
		}
	}
	
	private void removeWriter(Writer writer) {
		synchronized (listWriters) { //동기화 처리
			listWriters.remove(writer);
		}
	}


}
