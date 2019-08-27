package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	//private static String SERVER_IP = "192.168.1.22";
	private static String SERVER_IP = "192.168.56.1";
	private static int SERVER_PORT = 8000;

	public static void main(String[] args) {

		Socket socket = null;
		Scanner scanner = null;

		try {
			// 1. scanner 생성(표준입력, 키보드 연결)
			scanner = new Scanner(System.in);

			// 2. socket 생성
			socket = new Socket();

			// 3. 서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("connected");

			// 4. I/O Stream 생성
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
																												
			//5. join 프로토콜
			System.out.print("닉네임 >>");
			String nickname = scanner.nextLine();
			printWriter.println("join:" + nickname);
			printWriter.flush();
			
			//6. ChatClientThread 시작
			new ChatClientThread(bufferedReader, socket).start();
					
			//7. 키보드 입력 처리
			while (true) {				
				System.out.print(">> ");
				String input = scanner.nextLine();
				
				if ("quit".equals(input)) {
					//8. quit 프로토콜 처리
					printWriter.println("quit:");
					break;
				}else {
					//9. 메시지 처리		
					//데이터 쓰기(전송)
					if("".equals(input))
						input = " ";
					printWriter.println("message:"+input);
					printWriter.flush();

				}			

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {	
				if (scanner != null)
					scanner.close();
		}

	}

	private static void log(String log) {
		System.out.println("[Client] " + log);
	}
}
