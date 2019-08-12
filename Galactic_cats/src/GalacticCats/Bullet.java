package GalacticCats;

import java.awt.Graphics;
import java.awt.Point;
import java.net.URL;

import javax.swing.ImageIcon;

public class Bullet extends ImageIcon {
	Point pos;
	int delay;
	int sizeX = 50;
	int sizeY = 50;
	int index = 0;
	public Bullet(URL img,int x,int y) {
		super(img);
		pos=new Point(x,y);
		delay = (int)(Math.random()*2);
	}
	public void Attakcermove() {
		pos.x-=3;
	}
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), pos.x, pos.y,sizeX,sizeY,null);
	}
	
	public int getX() {
		return pos.x;
	}
	public int getY() {
		return pos.y;
	}
	public void HeroMove() {
		pos.x+=5;
	}
	public void setBulletSize(int sizeX,int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	public boolean outside_Attacker() {
		if(this.pos.getX()<0)return true;
		 return false;
	}
	public boolean outside_Hero() {
		if(this.pos.getX()>1155)return true;
		return false;
	}
	public boolean collide (Moving m) {
		Point p = new Point(m.getX(), m.getY());
		if (this.pos.distance(p) <= sizeX) return true;
		return false;
	}

}

