package PJ;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

class PosImageIcon extends ImageIcon {
	int pX;            // ImageIcon¿« X¡¬«•
	int pY;            // ImageIcon¿« y¡¬«•
	int width;         // ImageIcon¿« ≥–¿Ã
	int height;         // ImageIcon¿« ≥Ù¿Ã
	public PosImageIcon(String img, int x, int y, int width, int height) {
		super(img);
		pX=x;
		pY=y;
		this.width = width;
		this.height = height;
	}
	public void move(int x, int y) {
		pX += x;
		pY += y;
	}
	
	public void moveTo(int x, int y) {
		pX = x;
		pY = y;
	}
	
	
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), pX, pY, width, height, null);
	}
}