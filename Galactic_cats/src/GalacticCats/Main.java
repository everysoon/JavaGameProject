package GalacticCats;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Main extends JFrame{
	public static final int SCREEN_WIDTH=1155;
	public static final int SCREEN_HEIGHT=688;

	Gamepanel panel=new Gamepanel();


	public Main() {
		setTitle("Galactic_cats");
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setResizable(false);//Can't change window size
		setLocationRelativeTo(null);//frame in the middle of the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setFocusable(true);
		add(panel);
	}
	public static void main (String []args) {
		new Main();
	}


}
