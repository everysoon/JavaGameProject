package GalacticCats;

import java.awt.Graphics;
import java.awt.Point;
import java.net.URL;

import javax.swing.ImageIcon;

public class Bullet extends ImageIcon {
	Point pos;
	
	public Bullet(URL img,int x,int y) {
		super(img);
		pos=new Point(x,y);
	}
	public void Attakcermove() {
		pos.x-=3;
	}
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), pos.x, pos.y,50,50,null);
	}
	public int getX() {
		return pos.x;
	}
	public void HeroMove() {
		pos.x+=5;
	}
	public boolean isattaked(Moving m) {
		Point mPoint=new Point(m.getX(),m.getY());
		if(pos.distance(mPoint) <= m.margin)
			return true;
		else 
			return false;
	}
}
