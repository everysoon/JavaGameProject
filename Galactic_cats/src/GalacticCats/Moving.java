package GalacticCats;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.ImageIcon;

public class Moving extends ImageIcon {
	public int x,y;				// Image Position coordinates x,y
	private int initX, initY; 	// Initial start x, y coordinates
	protected int xDirection; //Direction x,y
	protected int yDirection;
	protected int xBoundary; // Boundary x,y
	protected int yBoundary; 
	protected int steps;
	protected int margin;	// Indicates an area that contains an area of ​​shape
	URL img; //character Image
	URL attack_img; //chracter attack motion Image
	URL attacked_img; //character attacked motion Image
	URL bullet; //character bullet Image 
	String item; // To indicate the power,potion among the items, String variable
	String what;// To indicate the bomb,bird,stone among the otherAttacker, String variable

	boolean invincible =false;
	boolean isdie =false; 
	boolean bird=false;
	boolean boom=false;

	public Moving(URL imgURL,URL attack_img,URL attacked_img,URL bullet,int x, int y, int margin, int steps, int xBoundary, int yBoundary) {
		// imgPath : 그림 파일의 경로명
		// x, y : 이미지의 시작 위치 좌표
		// margin : 이 이미지의 영역을 나타내는 범위 (이 영역안에 있으면 충돌 한 것으로 판단 하기 위함) ->이미지 크기 
		// steps : 이미지가 움직일때 이동하는 좌표 단위 (speed)
		// xBoundary, yBoundary : 그림이 이동할 수 있는 좌표의 최대값
		super (imgURL);
		this.attack_img=attack_img;
		this.attacked_img=attacked_img;
		this.bullet=bullet;
		this.x = x;
		this.y = y;	
		this.initX = x;
		this.initY = y;
		this.margin = margin;
		this.xDirection = 1;
		this.yDirection = 1;
		this.steps = steps;
		this.xBoundary = xBoundary;
		this.yBoundary = yBoundary;
		img=imgURL;

	}
	//stone,ball,새 이미지 그리기 : (attack_img)없는 애들 
	public Moving(URL imgURL,URL attacked,int margin,int xBoundary,int yBoundary,String what) {
		super(imgURL);
		this.attacked_img=attacked;
		this.what = what;
		this.margin=margin;
		this.xBoundary=xBoundary;
		this.yBoundary=yBoundary;
		this.xDirection=1;
		this.yDirection=1;
		this.x =((int)(Math.random()*xBoundary)+xBoundary);
		this.y=((int)(Math.random()*yBoundary)+100);
		this.initX=x;
		this.initY=y;
		this.steps=5;
		img=imgURL;
	}

	//ph포션,파워포션,
	public Moving(URL imgURL,int margin,String item) {
		super(imgURL);
		this.initX=x;
		this.initY=y;
		this.item=item;
		this.steps=5;
		this.xDirection = 1;
		this.yDirection = 1;
		this.xBoundary=1155-margin;
		this.yBoundary=688-margin;
		this.x= (int) (Math.random() * xBoundary/2)+xBoundary/2-200;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.margin=margin;
		img=imgURL;

	}
	//코인 
	public Moving(URL imgURL, int margin,int x,int y) {
		this(imgURL,margin,"coin");
		this.x=x;
		this.y=y;
		img=imgURL;

	}
	//적들 (attack이미지있는애들)
	public Moving(URL imgURL,URL attacked_img,URL bullet,int margin, int steps, int xBoundary, int yBoundary) {
		super(imgURL);
		this.attack_img=attacked_img;
		this.margin=margin;
		this.bullet=bullet;
		this.steps=steps;
		this.xDirection=1;
		this.yDirection=1;
		this.xBoundary=xBoundary;
		this.yBoundary=yBoundary;
		this.x= (int) (Math.random() * xBoundary/2)+xBoundary/2;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.initX=x;
		this.initY=y;
		img=imgURL;

	}

	public boolean outside() {
		if(this.x<0)
			return true;
		return false;
	}
	public String whatAttacker() {
		return what;
	}
	public String whatItem() {
		return item;
	}
	public void setBoom(boolean boom) {
		this.boom=boom;
	}
	public void setBird(boolean bird) {
		this.bird=bird;
	}
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getMargin() {
		return margin;
	}
	public URL getAttack_img() {
		return attack_img;
	}
	public URL getAttacked_img() {
		return attacked_img;
	}
	public URL getImg() {
		return img;
	}
	public URL getBullet() {
		return bullet;
	}
	public boolean isDie() {
		return isdie;
	}
	public void reset() {
		x = initX; y= initY;
	}

	// 하나의 점이 이 모양과 충돌하였는지 (모양의 margin 거리안에 있는지)를 판단하는 함수
	public boolean collide (Point p2) {
		Point p = new Point(this.x, this.y);
		if (p.distance(p2) <= margin) return true;
		return false;
	}
	// 해당 모양을 g에 출력해주는 메소드
	public void draw(Graphics g, ImageObserver io) {
		((Graphics2D)g).drawImage(this.getImage(), x, y, margin, margin, io);
	}

	public void Horizontallymove() {
		if (xDirection > 0 && x >= xBoundary) {
			xDirection = -1;
			y += (yDirection * steps * 5);
		}
		if (xDirection < 0 && x <= 0) {
			xDirection = 1;
			y += (yDirection * steps * 5);
		}
		x -= (xDirection * steps);

		if (yDirection > 0 && y >= yBoundary) {
			yDirection = -1;
		}
		if (yDirection < 0 && y <= 0) {
			yDirection = 1;
		}
	}

	//돌 
	public void Diaonallymove() {

		x -= (xDirection * steps);

		if (yDirection > 0 && y >= yBoundary) {
			yDirection = -1;
		}
		if (yDirection < 0 && y <= 0) {
			yDirection = 1;
		}
		y += (yDirection * steps);


	}
}

