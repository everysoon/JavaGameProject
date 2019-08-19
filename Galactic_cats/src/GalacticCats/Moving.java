package GalacticCats;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.ImageIcon;

public class Moving extends ImageIcon {
	public int x;				// ����� ��ġ ��ǥ
	public int y;				// ����� ��ġ ��ǥ
	private int initX, initY; 	// �ʱ���� x, y��ǥ
	protected int xDirection;
	protected int yDirection;
	protected int xBoundary; //���ǥ��
	protected int yBoundary; //���ǥ�� 
	protected int steps;
	protected int margin;		// �� ����� ������ ���ԵǴ� ������ ��Ÿ���� ����
	URL img;
	URL attack_img;
	URL attacked_img;
	URL bullet;
	boolean bird=false;
	boolean boom=false;
	String item;
	String what;
	boolean invincible =false;
	boolean isdie =false; 

	public Moving(URL imgURL,URL attack_img,URL attacked_img,URL bullet,int x, int y, int margin, int steps, int xBoundary, int yBoundary) {
		// imgPath : �׸� ������ ��θ�
		// x, y : �̹����� ���� ��ġ ��ǥ
		// margin : �� �̹����� ������ ��Ÿ���� ���� (�� �����ȿ� ������ �浹 �� ������ �Ǵ� �ϱ� ����) ->�̹��� ũ�� 
		// steps : �̹����� �����϶� �̵��ϴ� ��ǥ ����
		// xBoundary, yBoundary : �׸��� �̵��� �� �ִ� ��ǥ�� �ִ밪
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
	//stone,ball,�� �̹��� �׸��� : (attack_img)���� �ֵ� 
	public Moving(URL imgURL,URL attacked,int margin,int xBoundary,int yBoundary,String what) {
		super(imgURL);
		this.attacked_img=attacked;
		this.what = what;
		this.margin=margin;
		this.xBoundary=xBoundary;
		this.yBoundary=yBoundary;
		this.xDirection=1;
		this.yDirection=1;
		this.x= (int) (Math.random() * xBoundary/2)+xBoundary/2-400;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.initX=x;
		this.initY=y;
		this.steps=5;
		img=imgURL;

	}

	//ph����,�Ŀ�����,
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
	//���� 
	public Moving(URL imgURL, int margin,int x,int y) {
		this(imgURL,margin,"coin");
		this.x=x;
		this.y=y;
		img=imgURL;

	}
	// ���� ��ġ�� ������ ����Ʈ�� �ִ� ������ 
	//���� (attack�̹����ִ¾ֵ�)
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
		x= (int) (Math.random() * xBoundary/2)+xBoundary/2;
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
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setMargin(int margin) {
		this.margin = margin;
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
	// �ϳ��� ���� �� ���� �浹�Ͽ����� (����� margin �Ÿ��ȿ� �ִ���)�� �Ǵ��ϴ� �Լ�
	public boolean collide (Point p2) {
		Point p = new Point(this.x, this.y);
		if (p.distance(p2) <= margin) return true;
		return false;
	}
	public boolean isDie() {
		return isdie;
	}
	public void reset() {
		x = initX; y= initY;
	}

	// �ش� ����� g�� ������ִ� �޼ҵ�
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

	//�� 
	public void Diaonallymove() {
		int rand =(int)(Math.random()*2);
		switch(rand) {
		case 0:
			if (xDirection > 0 && x >= xBoundary) {
				//xDirection = -1;
			}
			if (xDirection < 0 && x <= 0) {
				//xDirection = 1;
			}
			x += (xDirection * steps);

			if (yDirection > 0 && y >= yBoundary) {
				yDirection = -1;
			}
			if (yDirection < 0 && y <= 0) {
				yDirection = 1;
			}
			y += (yDirection * steps);
			break;
		case 1 :
			if (xDirection > 0 && x >= xBoundary) {
				//xDirection = -1;
			}
			if (xDirection < 0 && x <= 0) {
				//xDirection = 1;
			}
			x -= (xDirection * steps);

			if (yDirection > 0 && y >= yBoundary) {
				yDirection = -1;
			}
			if (yDirection < 0 && y <= 0) {
				yDirection = 1;
			}
			y -= (yDirection * steps);
			break;
		}
	}
}
