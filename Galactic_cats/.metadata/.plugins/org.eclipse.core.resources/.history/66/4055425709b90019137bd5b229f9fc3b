package PJ;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Characters implements Serializable{
	int x,y;
	int location;
	int money;
	int gain;
	String name;
	String characterImage;
    String selectedCharacterImage;
    String gameImage;
    String proImage;
    JLabel gameName=new JLabel();
    JLabel gameMoney=new JLabel();
    JLabel gameGain=new JLabel();
    

public Characters(String characterImage, String selectedCharacterImage) {
		
		this.characterImage = characterImage;
		this.selectedCharacterImage = selectedCharacterImage;
	
	}
public Characters(String user,String gameImage,String proImage) {
	name=user;
	this.gameImage=gameImage;
	this.proImage=proImage;
	x=380;
	y=480;
	location=0;
	money=100000000;
	gain=0;
	gameName.setText(name);
	gameMoney.setText(""+money);
	gameGain.setText("gain:  "+gain);
}

public String getGameImage() {
	return gameImage;
}
public String getProImage() {
	return proImage;
}
public int getlocation() {
	return location;
}
public void setGain(int gain) {
	this.gain+=(gain);
}
public int getGain(){

	return gain;
}
public void setlocation(int location) {
	this.location=location;
}
public int getX() {
	return x;
}
public int getY()
{
	return y;
}

}
