package PJ;
import java.io.Serializable;

public class ChatMessage implements Serializable {
	// 메시지 타입 정의
	// 1개의 메시지 종류 필드와 3개의 String형 필드.
	// NO_ACT는 무시할 수 있는 Dummy 메시지. 디버깅용 등으로 사용하기 위해 만들어 놓음
	// (1) 클라이언트가 보내는 메시지 형식
	//	- LOGIN  : CLIENT 로그인.
	//		메시지 포맷 : LOGIN, "송신자", "", ""
	//	- LOGOUT : CLIENT 로그아웃.
	//		메시지 포맷 : LOGOUT, "송신자", "", ""
	// 	- CLIENT_MSG : 서버에게 보내는  대화 .
	// 		메시지포맷  : CLIENT_MSG, "송신자", "수신자", "내용"
	// (2) 서버가 보내는 메시지 형식
	// 	- LOGIN_FAILURE  : 로그인 실패
	//		메시지 포맷 : LOGIN_FAILURE, "", "", "로그인 실패 원인"
	// 	- SERVER_MSG : 클라이언트에게 원격으로 보내는 대화 
	//		메시지포맷  : SERVER_MSG, "송신자", "", "내용" 
	// 	- LOGIN_LIST : 현재 로그인한 사용자 리스트.
	//		메시지 포맷 : LOGIN_LIST, "", "", "/로 구분된 사용자 리스트"
	public enum MsgType {NO_ACT, LOGIN, LOGOUT, CLIENT_MSG, LOGIN_FAILURE, SERVER_MSG, LOGIN_LIST
							,MOVING,DICE_NUM,BUILD_UP,COUNTER_PART,START};
							//DICE_NUM=turn제어
							//MOVING=상대케릭터그리기
							//BUILD_UP=건물그리기/건물소유자지정/남의건물이면 돈뻇기게
	public static final String ALL = "전체";	 // 사용자 명 중 자신을 제외한 모든 로그인되어 있는
											 // 사용자를 나타내는 식별문
	private MsgType type;
	private String sender;
	private String receiver;
	private String contents;
	private Characters ch;
	private Map m;

	public ChatMessage() {
		this(MsgType.NO_ACT, "", "", "");
	}
	public ChatMessage(MsgType t, String sID, String rID, String mesg) {
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
	}
	public ChatMessage(MsgType t, String sID,Characters ch) {
		type = t;
		sender = sID;
		this.ch=ch;
	}
	public ChatMessage(MsgType t, String sID,String mesg,Map map) {
		type = t;
		sender = sID;
		contents=mesg;
		m=map;
	
	}
	
	public Map getMap() {
		return m;
	}
	public Characters getCh() {
		return ch;
	}
	public void setType (MsgType t) {
		type = t;
	}
	public MsgType getType() {
		return type;
	}

	public void setSender (String id) {
		sender = id;
	}
	public String getSender() {
		return sender;
	}
	
	public void setReceiver (String id) {
		receiver = id;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setContents (String mesg) {
		contents = mesg;
	}
	public String getContents() {
		return contents;
	}
	
	public String toString() {
		return ("메시지 종류 : " + type + "\n" +
				"송신자         : " + sender + "\n" +
				"수신자         : " + receiver + "\n" +
				"메시지 내용 : " + contents + "\n");
	}
}
