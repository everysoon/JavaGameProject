package GalacticCats;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Main extends JFrame{
	public static final int SCREEN_WIDTH=1155;
	public static final int SCREEN_HEIGHT=688;
	
	Gamepanel panel=new Gamepanel();

	KeyListener tmp2=panel.tmp;
	
	public Main() {
		setTitle("Galactic_cats");
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setResizable(false);//âũ�� �� �ٲٰ�
		setLocationRelativeTo(null);//â �����찡���!
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setFocusable(true);
		
		add(panel);
		addKeyListener(tmp2);
		
	}
	
	public static void main (String []args) {
		new Main();
	}
	
	public void setKey() {
		
	}
}
