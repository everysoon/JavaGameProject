package PJ;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import PJ.KaKaOMable;

public class Main extends JFrame {
	public static final int SCREEN_WIDTH=900;
	public static final int SCREEN_HEIGHT=636;
	KaKaOMable km;
	
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private Socket sock;
	
	public static void main(String []args) {
		new Main();
	}
	
	public Main() {
		setupNetworking();
		km=new KaKaOMable(this.writer,this.reader);
		setTitle("FriendsMable");
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setResizable(false);//창크기 못 바꾸게
		setLocationRelativeTo(null);//창 윈도우가운데에!
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(km);
	}
	
	public void setupNetworking() {
	
		try {
			
			sock=new Socket("114.71.208.11",8810);//IP번호와 포트번호
			reader=new ObjectInputStream(sock.getInputStream());
			writer=new ObjectOutputStream(sock.getOutputStream());
			System.out.println("SetupNetWorking완료!");
		
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"서버 접속에 실패하였습니다.");
			ex.printStackTrace();
			dispose(); //클라이언트 강제종료
		}
	}
}
