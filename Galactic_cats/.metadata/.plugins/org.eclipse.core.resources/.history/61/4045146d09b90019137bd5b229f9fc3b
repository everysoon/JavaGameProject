package PJ;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	// 접속한 클라이언트의 사용자 이름과 출력 스트림을 해쉬 테이블에 보관
	// 나중에 특정 사용자에게 메시지를 보낼때 사용. 현재 접속해 있는 사용자의 전체 리스트를 구할때도 사용
	HashMap<String, ObjectOutputStream> clientOutputStreams =
			new HashMap<String, ObjectOutputStream>();

	public static void main (String[] args) {
		new ChatServer().go();
	}

	private void go () {
		try {
			ServerSocket serverSock = new ServerSocket(8810);	// 채팅을 위한 소켓 포트 5000 사용

			while(true) {
				Socket clientSocket = serverSock.accept();		// 새로운 클라이언트 접속 대기

				// 클라이언트를 위한 입출력 스트림 및 스레드 생성
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();									
				System.out.println("S : 클라이언트 연결 됨");		// 상태를 보기위한 출력 메시지
			}
		} catch(Exception ex) {
			System.out.println("S : 클라이언트  연결 중 이상발생");	// 상태를 보기위한 출력  메시지
			ex.printStackTrace();
		}
	}

	// Client 와 1:1 대응하는 메시지 수신 스레드
	private class ClientHandler implements Runnable {
		Socket sock;					// 클라이언트 연결용 소켓
		ObjectInputStream reader;		// 클라이언트로 부터 수신하기 위한 스트림
		ObjectOutputStream writer;		// 클라이언트로 송신하기 위한 스트림

		// 구성자. 클라이언트와의 소켓에서 읽기와 쓰기 스트림 만들어 냄
		// 스트림을 만들때 InputStream을 먼저 만들면 Hang함. 그래서 OutputStream먼저 만들었음.
		// 이것은 클라이언트에서 InpitStreams을 먼저 만들기 때문임 안그러면 데드락
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				writer = new ObjectOutputStream(clientSocket.getOutputStream());
				reader = new ObjectInputStream(clientSocket.getInputStream());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		// 클라이언트에서 받은 메시지에 따라 상응하는 작업을 수행
		public void run() {
			ChatMessage message;
			ChatMessage.MsgType type;
			try {
				while (true) {
					// 읽은 메시지의 종류에 따라 각각 할일이 정해져 있음
					message = (ChatMessage) reader.readObject();	  // 클라이언트의 전송 메시지 받음
					type = message.getType();
					if (type == ChatMessage.MsgType.LOGIN) {		  // 클라이언트 로그인 요청
						handleLogin(message.getSender(),writer);	  // 클아이언트 이름과 그에게 메시지를
						// 보낼 스트림을 등록
					}
					else if (type == ChatMessage.MsgType.LOGOUT) {	  // 클라이언트 로그아웃 요청
						handleLogout(message.getSender());			  // 등록된 이름 및 이와 연결된 스트림 삭제
						writer.close(); reader.close(); sock.close(); // 이 클라이언트와 관련된 스트림들 닫기
						return;										  // 스레드 종료
					}
					else if (type == ChatMessage.MsgType.CLIENT_MSG) {
						handleMessage(message.getSender(), message.getReceiver(), message.getContents());
					}
					else if (type == ChatMessage.MsgType.NO_ACT) {
						//  무시해도 되는 메시지
						continue;
					}
					else if(type == ChatMessage.MsgType.DICE_NUM) {
						broadcastMessage(message);
					}
					else if(type == ChatMessage.MsgType.START) {
						broadcastMessage(message);
					}
					else if(type == ChatMessage.MsgType.MOVING) {
						broadcastMessage(message);
					}
					else if(type == ChatMessage.MsgType.COUNTER_PART) {
						broadcastMessage(message);
					}
					else if(type == ChatMessage.MsgType.BUILD_UP) {
						broadcastMessage(message);
					}
					else {
						// 정체가 확인되지 않는 이상한 메시지?
						throw new Exception("S : 클라이언트에서 알수 없는 메시지 도착했음");
					}
				}
			} catch(Exception ex) {
				System.out.println("S : 클라이언트 접속 종료");				// 연결된 클라이언트 종료되면 예외발생
				// 이를 이용해 스레드 종료시킴
			}
		} // close run
	} // close inner class

	// 사용자 이름과 클라이언트로의 출력 스트림과 연관지어 해쉬 테이블에 넣어줌.
	// 이미 동일한 이름의 사용자가 있다면, 현재의 로그인은 실패 한것으로 클라이언트에게 알림
	// 그리고 새로운 접속자 리스트를 모든 접속자에게 보내줌
	// 해쉬 테이블의 접근에서는 경쟁조건 생기면 곤란 (not Thread-Safe. Synchronized로 상호배제 함.
	private synchronized void handleLogin(String user, ObjectOutputStream writer) {
		try {
			// 이미 동일한 이름의 사용자가 있다면, 현재의 로그인은 실패 한것으로 클라이언트에게 알림
			if (clientOutputStreams.containsKey(user)) {
				writer.writeObject(
						new ChatMessage(ChatMessage.MsgType.LOGIN_FAILURE, "", "", "사용자 이미 있음"));
				return;
			}
		} catch (Exception ex) {
			System.out.println("S : 서버에서 송신 중 이상 발생");
			ex.printStackTrace();
		}
		// 해쉬테이블에 사용자-전송스트림 페어를 추가하고 새로운 로그인 리스트를 모두에게 알림
		clientOutputStreams.put(user, writer);
		// 새로운 로그인 리스트를 전체에게 보내 줌
		broadcastMessage(new ChatMessage(ChatMessage.MsgType.LOGIN_LIST, "", "", makeClientList()));
	}  // close handleLogin

	// 주어진 사용자를 해쉬테이블에서 제거 (출력 스트림도 제거)
	// 그리고 업데이트된 접속자 리스트를 모든 접속자에게 보내줌
	private synchronized void handleLogout(String user) {
		clientOutputStreams.remove(user);
		// 새로운 로그인 리스트를 전체에게 보내 줌
		broadcastMessage(new ChatMessage(ChatMessage.MsgType.LOGIN_LIST, "", "", makeClientList()));
	}  // close handleLogout

	// 클라이언트가 대화 상대방에게 보내는 메시지. 그 상대 혹은 "전체"에게 보내 주어야 함
	private synchronized void handleMessage(String sender, String receiver, String contents) {
		// 여기서 모두에게 보내는 경우를 처리해야 함
		if (receiver.equals(ChatMessage.ALL)) {			// "전체"에게 보내는 메시지이면
			broadcastMessage(new ChatMessage(ChatMessage.MsgType.SERVER_MSG, sender, "", contents));
			return;
		}
		// 특정 상대에게 보내는 경우라면
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new ChatMessage(ChatMessage.MsgType.SERVER_MSG, sender, "", contents));
		} catch (Exception ex) {
			System.out.println("S : 서버에서 송신 중 이상 발생");
			ex.printStackTrace();
		}
	}  // close handleIncomingMessage

	// 해쉬맵에 있는 모든 접속자들에게 주어진 메시지를 보내는 메소드.
	// 반드시 synchronized 된 메소드에서만 호출하기로 함
	private void broadcastMessage(ChatMessage message) {
		Set<String> s = clientOutputStreams.keySet();	// 먼저 등록된 사용자들을 추출하고 하나하나에 메시지 보냄
		// 그러기 위해서 먼저 사용자 리스트만 추출
		Iterator<String> it = s.iterator();
		String user;
		while(it.hasNext()) {
			user = it.next();
			try {
				ObjectOutputStream writer = clientOutputStreams.get(user);	// 대상 사용자와의 스트림 추출
				writer.writeObject(message);									// 그 스트림에 출력
				writer.flush();
			} catch(Exception ex) {
				System.out.println("S : 서버에서 송신 중 이상 발생");
				ex.printStackTrace();
			}
		} // end while	   
	}	// end broadcastMessage

	private String makeClientList() {
		Set<String> s = clientOutputStreams.keySet();	// 먼저 등록된 사용자들을 추출
		Iterator<String> it = s.iterator();
		String userList = "";
		while(it.hasNext()) {
			userList += it.next() + "/";					// 스트링 리스트에 추가하고 구분자 명시
		} // end while
		return userList;									 
	}	// makeClientList
}
