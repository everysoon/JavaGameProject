package GalacticCats;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Gamepanel extends JPanel{
	final int SPEED=50;
	final int A_MARGIN=110;//alian,hero margin
	final int B_MARGIN=80; //Big boom,bird,Big stone margin
	final int S_MARGIN=40; //small bomb,small stone margin
	final int OTHER_MARGIN=50; //item margin
	final int STEPS=5; //speed 
	final int NEW_ATTACKER_INTERVAL= 5;
	final int OTHER_ATTACKER_INTERVAL= 5;
	final int xB=1045; //xB,yB is WIDTH - margin, HEIGHT - margin 
	final int yB=578;

	//Buffer Image,Buffer Graphics for double Buffering 
	Image buffImage;
	Graphics buffg;

	Image finishpanel =new ImageIcon("src/Image/finish.png").getImage();
	Image startBackground=new ImageIcon("src/Image/startpanel.png").getImage();
	Image gameBackground=new ImageIcon("src/Image/gameBackground_.gif").getImage();
	ImageIcon power_ing=new ImageIcon("src/Image/power_ing.gif");
	ImageIcon power_full=new ImageIcon("src/Image/power_full.gif");
	ImageIcon hero_attackImage =new ImageIcon("src/Image/hero_attacked.gif");
	ImageIcon ph_4=new ImageIcon("src/Image/ph_full.png");
	ImageIcon ph_3=new ImageIcon("src/Image/ph_2.png");
	ImageIcon ph_2=new ImageIcon("src/Image/ph_3.png");
	ImageIcon ph_1=new ImageIcon("src/Image/ph_4.png");
	ImageIcon ph_0=new ImageIcon("src/Image/ph_5.png");
	ImageIcon phBar =new ImageIcon(ph_4.getImage());
	ImageIcon profile =new ImageIcon("src/Image/profile.png");
	ImageIcon powergauge =new ImageIcon(power_ing.getImage());
	ImageIcon attack_touched =new ImageIcon("src/Image/attack_touched.gif");
	ImageIcon special_attack =new ImageIcon("src/Image/special_attack.gif");
	ImageIcon hero_invincibility =new ImageIcon("src/Image/invincibility.gif");
	ImageIcon hero_flybasic =new ImageIcon ();
	ImageIcon hero_attackbasic =new ImageIcon();
	ImageIcon hero_Attacked =new ImageIcon ();
	ImageIcon hero_specialAttack =new ImageIcon("src/Image/special.gif");

	ArrayList<Bullet> weapon=new ArrayList<Bullet>();
	ArrayList<Bullet> Attacker_weapon=new ArrayList<Bullet>();
	ArrayList<Moving> alian;
	ArrayList<Moving> item;
	ArrayList<Moving> sbb; //stone AND boom AND bird 
	ArrayList<Moving> coinList;

	JButton startButton =new JButton();
	JButton reStartButton =new JButton();
	JButton quitButton =new JButton();

	//goAnime,goScore Timer , clockListener for image Moving 
	Timer goAnime;
	Timer goScore;

	Moving hero;
	Moving green;
	Moving purple;
	Moving yellow;

	int score=0;
	int randNum;
	int coinNum=0;
	int x=0;
	int ph=4;

	int invincibleTimer =0; //hero invincibleDelayTimer int variable

	String playerName;

	boolean finished=false;
	boolean space=false;
	boolean start=false;
	boolean power=false;
	boolean attacked=false;
	boolean upPushed=false; // up,down,left,right boolean variable
	boolean downPushed=false;
	boolean leftPushed=false;
	boolean rightPushed=false;
	boolean restart =false;

	public Gamepanel() {
		playerName=JOptionPane.showInputDialog("이름을 입력해주세요 :");
		if(playerName == null ) //Dialog 'cancle' pressed quit.  
			System.exit(1);
		setup(); 
	}

	//GUI setting
	public void setup() {

		setBounds(0,0,1155,688);
		setLayout(null);

		startButton.setBounds(230,550,680,100);
		startButton.setBackground(Color.white);
		startButton.setBorderPainted(false);  //Button edge	Invisible
		startButton.setOpaque(false);	//Button Opaque
		startButton.addActionListener(new startButtonListener());

		reStartButton.setBounds(420,390,140,40);
		reStartButton.addActionListener(new reStartButtonListener());
		reStartButton.setVisible(false);

		quitButton.setBounds(600,390,140,40);
		quitButton.addActionListener(new quitButtonListener());
		quitButton.setVisible(false);

		prepareAttacker();
		this.addKeyListener(new heroKeyEvent());
		add(reStartButton);
		add(quitButton);
		add(startButton);

		//Hero Character create 
		hero=new Moving(getClass().getResource("/Image/fly_basic.gif"),
				getClass().getResource("/Image/basic.gif"),
				getClass().getResource("/Image/hero_attacked.gif"),
				getClass().getResource("/Image/basic_attack.gif"),
				0,250,A_MARGIN,5,xB,yB);

		hero_flybasic.setImage(new ImageIcon(hero.getImg()).getImage());
		hero_attackbasic.setImage(new ImageIcon(hero.getAttack_img()).getImage());
		hero_Attacked.setImage(new ImageIcon(hero.getAttacked_img()).getImage());;

		//타이머

		goScore = new Timer(800, new ClockListener());			// Listener for handling the time in seconds
		goAnime = new Timer(SPEED, new AnimeListener());	// Listener for handling the movement of the picture

	}

	// paint, screenDraw Method for Double Buffering 
	public void paint(Graphics g) {
		buffImage = createImage(1155, 688); // frame size : 1155 = WIDTH , 688 = HEIGHT ,bufferImage create 
		buffg = buffImage.getGraphics();  //bufferGraphics is bufferImage get Graphics 
		screenDraw(buffg);//Draw Image bufferGraphics 
		g.drawImage(buffImage,0,0,null);//PaintComponent draw bufferImage 
	}
	public void screenDraw(Graphics g) {

		if(!start)g.drawImage(startBackground,0,0,null); //readyScreen
		else { 
			//gameScreen draw 
			g.drawImage(gameBackground,x,0,null);
			g.drawImage(phBar.getImage(),750,60,null);
			g.drawImage(profile.getImage(),15,15,null);
			g.drawImage(powergauge.getImage(),750,10,null);	
			g.drawImage(new ImageIcon("src/Image/coin.gif").getImage(),980,65,50,50,null);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif",Font.BOLD,25));
			g.drawString("X"+coinNum,1030,100);
			g.drawString(String.valueOf(score),240,98);
			g.setFont(new Font("Serif",Font.BOLD,20));
			g.drawString(playerName,230,58);
			//draw hero Character, alian,sbb,coin,item 
			hero.draw(g,this);
			for (Moving m : alian) {
				m.draw(g,this);
			}     
			for (Moving m : sbb) {
				m.draw(g,this);
			}
			for(Moving m: coinList) {
				m.draw(g,this);
			}
			for(Moving m:item) {
				m.draw(g,this);
			}
			for(Bullet b:weapon) {
				if(power) { //if powerGauge full ,hero bullet Image change specialattack image    
					b.setImage(new ImageIcon("src/Image/special_attack.gif").getImage()); 
					b.setBulletSize(140,95);
					power=false;
				}
				b.draw(g);
				if(b.outside_Hero()) { //if bullet is outside , remove that 
					weapon.remove(b);
					break;
				}
				b.HeroMove();
			}

			for(Bullet b:Attacker_weapon) {
				b.draw(g);
				b.Attakcermove();
				if(b.outside_Attacker()) {
					Attacker_weapon.remove(b);
					break;
				}
			}
			if(finished) {
				g.drawImage(finishpanel,380,170,400,300,null);
				g.drawString(playerName,650,220);
				g.setFont(new Font("Serif",Font.BOLD,30));
				g.drawString(playerName,440,360);
				g.drawString(String.valueOf(score),660,290);
			}
		}
		this.repaint();
	}
	public class startButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			start=true;
			startButton.setVisible(false);	
			prepareAttacker();
			goScore.start();
			goAnime.start();
			focus(); //startButton clicked, ButtonListener transform foucus KeyListener 
		}

	}
	public class reStartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			goAnime.restart();
			goScore.restart();
			finished=false;
			for(Moving m : alian) {
				m.reset();
			}
			for(Moving b : coinList) {
				b.reset();
			}
			//init
			coinNum = 0;
			ph = 4;
			score = 0;
			hero.setImage(hero_flybasic.getImage());
			setPhbar(ph);
			AttackerInit();
			focus();//reStartButton clicked, ButtonListener transform foucus KeyListener 	
			restart=true;
		}

	}
	public class  quitButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(1);
		}

	}
	public void focus() {
		this.setFocusable(true);
		this.requestFocus();
	}
	public void prepareAttacker() { 

		coinList=new ArrayList<Moving>();
		item=new ArrayList<>();
		alian=new ArrayList<>();
		sbb=new ArrayList<>();

		//alian Attacker yellow,green,purple create 
		//sbb Attacker random create
		//item random create 
		//coin setting 

		green=new Moving(getClass().getResource("/Image/green_attack.gif"),
				getClass().getResource("/Image/green_attacked.gif"),
				getClass().getResource("/Image/attack4.png"),
				A_MARGIN,STEPS,xB,yB
				);
		alian.add(green);

		purple= new Moving(getClass().getResource("/Image/purple_attack.gif"),
				getClass().getResource("/Image/purple_attacked.gif"),
				getClass().getResource("/Image/attack1.png"),
				A_MARGIN,STEPS,xB,yB
				);
		alian.add(purple);

		yellow=new Moving(getClass().getResource("/Image/yellow_attack.gif"),
				getClass().getResource("/Image/yellow_attacked.gif"),
				getClass().getResource("/Image/attack3.png"),
				A_MARGIN,STEPS,xB,yB
				);
		alian.add(yellow);

		for(Moving m:alian) {
			if(m.isDie()==false) {
				Attacker_weapon.add(new Bullet(green.getBullet(),green.getX(),green.getY()+green.getMargin()/2));
				Attacker_weapon.add(new Bullet(purple.getBullet(),purple.getX(),purple.getY()+purple.getMargin()/2));
				Attacker_weapon.add(new Bullet(yellow.getBullet(),yellow.getX(),yellow.getY()+yellow.getMargin()/2));
			}
		}
		for(int i=0; i<3; i++) {
			randNum=(int)(Math.random()*2)+13;
			item.add(getRandomMoving(randNum));	
		}
		for(int i=0; i<3; i++) {
			randNum=(int)(Math.random()*9)+4;
			sbb.add(getRandomMoving(randNum));
		}
		coinInit();
	}
	public void coinInit() {

		//prepare coin setting 

		for(int i=0; i<150; i+=30) {
			coinList.add(getcoin(500+i,200));
			coinList.add(getcoin(1000+i,400+i));
			coinList.add(getcoin(1500+i,250-i));
			coinList.add(getcoin(2000-i,400+i));
			coinList.add(getcoin(2500+i,250));
			coinList.add(getcoin(3000+i,400+i));
			coinList.add(getcoin(3500+i,250-i));
			coinList.add(getcoin(4000-i,400-i));
			coinList.add(getcoin(4500+i,250));
			coinList.add(getcoin(5000+i,400+i));
			coinList.add(getcoin(5500+i,250-i));
			coinList.add(getcoin(6000+i,400+i));
			coinList.add(getcoin(6500+i,250));
			coinList.add(getcoin(7000+i,400+i));
			coinList.add(getcoin(7500+i,250));
			coinList.add(getcoin(8000+i,400));
			coinList.add(getcoin(8500+i,250));
			coinList.add(getcoin(9000+i,400+i));
			coinList.add(getcoin(9500+i,250-i));
			coinList.add(getcoin(10000+i,400-i));
			coinList.add(getcoin(10500+i,250));
			coinList.add(getcoin(11000+i,400-i));
			coinList.add(getcoin(11500+i,250));
		}

	}
	public class heroKeyEvent implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {

			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				upPushed = true;
				if(hero.invincible)
					hero.setImage(hero_invincibility.getImage());
				else
					hero.setImage(hero_flybasic.getImage());
				break;
			case KeyEvent.VK_DOWN:
				downPushed = true;
				if(hero.invincible)
					hero.setImage(hero_invincibility.getImage());
				else
					hero.setImage(hero_flybasic.getImage());
				break;
			case KeyEvent.VK_LEFT:
				leftPushed = true;
				if(hero.invincible)
					hero.setImage(hero_invincibility.getImage());
				else
					hero.setImage(hero_flybasic.getImage());
				break;
			case KeyEvent.VK_RIGHT:
				rightPushed =true;
				if(hero.invincible)
					hero.setImage(hero_invincibility.getImage());
				else
					hero.setImage(hero_flybasic.getImage());
				break;
			case KeyEvent.VK_SPACE:
				space=true;
				weapon.add(new Bullet(hero.getBullet(),hero.x,hero.y));	//if space key pressed , bullet create 
				if(power) { // when powerGauge full, if space key pressed specialAttack bullet create  
					weapon.add(new Bullet(getClass().getResource("/Image/special_attack.gif"),hero.x,hero.y));
					hero.setImage(hero_specialAttack.getImage());
				}else {
					hero.setImage(hero_flybasic.getImage());
				}
				break;	
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {

			case KeyEvent.VK_UP:
				upPushed = false;
				break;
			case KeyEvent.VK_DOWN:
				downPushed = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPushed = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightPushed =false;
				break;
			case KeyEvent.VK_SPACE:
				space =false;
				break;
			}

		}
		@Override
		public void keyTyped(KeyEvent e) {}

	}

	public void heroMove() {
		if(upPushed) 
			if (hero.y >= 0)
				hero.y -= 10;
		if(downPushed) 
			if (hero.y <=xB)
				hero.y += 10;
		if(leftPushed)
			if (hero.x >= 0)
				hero.x -= 10;
		if(rightPushed)
			if (hero.x <=yB)
				hero.x += 10;
	}

	private class ClockListener implements ActionListener {

		public void actionPerformed (ActionEvent event) {

			score++;
			if(score%30==0 && score!=0) power=true;
			if(power) {
				powergauge.setImage(power_full.getImage());
			}else {
				powergauge.setImage(power_ing.getImage());
			}

			//When time passes,New alian emergence
			if (score % NEW_ATTACKER_INTERVAL == 0) {
				randNum=(int)(Math.random()*3)+1;
				alian.add(getRandomMoving(randNum));
				for(Moving m:alian) {
					if(m.isDie()==false)
						Attacker_weapon.add(new Bullet(m.getBullet(),m.getX(),m.getY()+m.getMargin()/2));	
				}
			}
			//When time passes,New bomb,bird,stone emergence
			if (score % OTHER_ATTACKER_INTERVAL == 0) {

				randNum=(int)(Math.random()*9)+4;
				sbb.add(getRandomMoving(randNum));

				randNum=(int)(Math.random()*2)+13;
				item.add(getRandomMoving(randNum));


			}
		}

	}
	public Moving getRandomMoving(int rand) {
		Moving newAttacker = null;

		switch (rand) {
		case 1 : //case 1~3 : alian
			newAttacker = new Moving(getClass().getResource("/Image/green_attack.gif"),
					getClass().getResource("/Image/green_attacked.gif"),
					getClass().getResource("/Image/attack4.png"),
					A_MARGIN,STEPS,xB,yB
					);
			for(int i=0;i<10;i++)
				Attacker_weapon.add(new Bullet(newAttacker.getBullet(),newAttacker.getX(),newAttacker.getY()+newAttacker.getMargin()/2));
			break;
		case 2 :
			newAttacker = new Moving(getClass().getResource("/Image/purple_attack.gif"),
					getClass().getResource("/Image/purple_attacked.gif"),
					getClass().getResource("/Image/attack1.png"),
					A_MARGIN,STEPS,xB,yB
					);
			for(int i=0;i<10;i++)
				Attacker_weapon.add(new Bullet(newAttacker.getBullet(),newAttacker.getX(),newAttacker.getY()+newAttacker.getMargin()/2));
			break;
		case 3 :
			newAttacker =new Moving(getClass().getResource("/Image/yellow_attack.gif"),
					getClass().getResource("/Image/yellow_attacked.gif"),
					getClass().getResource("/Image/attack3.png"),
					A_MARGIN,STEPS,xB,yB
					);
			for(int i=0;i<10;i++)
				Attacker_weapon.add(new Bullet(newAttacker.getBullet(),newAttacker.getX(),newAttacker.getY()+newAttacker.getMargin()/2));
			break;
		case 4 :	//case 4~8 bomb
			newAttacker =  new Moving(getClass().getResource("/Image/ball1.png"),
					getClass().getResource("/Image/boom.gif"),
					S_MARGIN,xB,yB,"boom");
			newAttacker.setBoom(true);
			break;
		case 5:
			newAttacker =  new Moving(getClass().getResource("/Image/ball2.png"),
					getClass().getResource("/Image/boom.gif"),
					S_MARGIN,xB,yB,"boom");
			newAttacker.setBoom(true);
			break;
		case 6:
			newAttacker =  new Moving(getClass().getResource("/Image/ball3.png"),
					getClass().getResource("/Image/boom.gif"),
					B_MARGIN,xB,yB,"boom");
			newAttacker.setBoom(true);
			break;
		case 7:
			newAttacker =  new Moving(getClass().getResource("/Image/ball4.png"),
					getClass().getResource("/Image/boom.gif"),
					B_MARGIN,xB,yB,"boom");
			newAttacker.setBoom(true);
			break;
		case 8: //case 8~10 stone
			newAttacker =  new Moving(getClass().getResource("/Image/stone1.png"),
					getClass().getResource("/Image/stone_destroyed.gif"),
					B_MARGIN,xB,yB,"stone");
			newAttacker.setBoom(true);
			break;		
		case 9:
			newAttacker =  new Moving(getClass().getResource("/Image/stone3.png"),
					getClass().getResource("/Image/stone_destroyed.gif"),
					S_MARGIN,xB,yB,"stone");
			newAttacker.setBoom(true);
			break;
		case 10:
			newAttacker =  new Moving(getClass().getResource("/Image/stone4.png"),
					getClass().getResource("/Image/stone_destroyed.gif"),
					B_MARGIN,xB,yB,"stone");
			newAttacker.setBoom(true);
			break;

		case 11: //case 11~12 bird
			newAttacker =  new Moving(getClass().getResource("/Image/blackbird.gif"),
					getClass().getResource("/Image/blackbird_attacked.gif"),
					B_MARGIN,xB,yB,"stone");
			newAttacker.setBird(true);
			break;
		case 12:
			newAttacker =  new Moving(getClass().getResource("/Image/redbird.gif"),
					getClass().getResource("/Image/redbird_attacked.gif"),
					B_MARGIN,xB,yB,"stone");
			newAttacker.setBird(true);
			break;
		case 13:
			newAttacker= new Moving(getClass().getResource("/Image/ph.gif"),OTHER_MARGIN,"ph");
			break;
		case 14:
			newAttacker =new Moving(getClass().getResource("/Image/power.gif"),OTHER_MARGIN,"power");

		}
		return newAttacker;
	}
	public void setPhbar(int ph) {

		//phBar setting 
		if(ph==4) phBar.setImage(ph_4.getImage());
		else if(ph==3) phBar.setImage(ph_3.getImage());
		else if(ph==2)phBar.setImage(ph_2.getImage());
		else if(ph==1)phBar.setImage(ph_1.getImage()); 
		else if(ph==0) {
			//Game Finished 
			phBar.setImage(ph_0.getImage());
			goAnime.stop();
			goScore.stop();
			hero.setImage(hero_Attacked.getImage());
			reStartButton.setVisible(true);
			quitButton.setVisible(true);
			finished=true;
		}
	}
	public Moving getcoin(int x,int y) {

		//create Coin 
		Moving NewItem=null;
		NewItem=new Moving(getClass().getResource("/Image/coin.gif"),OTHER_MARGIN,x,y);
		return NewItem;

	}

	public void AttackerInit() {
		alian.clear();
		Attacker_weapon.clear();
		prepareAttacker();

	}
	//AnimeListener is that if hero and something collide, implemented a situation
	public class AnimeListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			heroMove(); //hero moving

			//background moving x
			x-=1; 
			if(x<-160)x=0;

			//When hero and coin collide , coin remove and coinNum plus
			for(Moving m :coinList) {
				if(m.collide(new Point(hero.x,hero.y))) {
					coinList.remove(m);
					coinNum++;
					break;
				}
			}
			//When hero and item collide , if item is ph, ph plus else item is power,change powergauge full ,and item remove
			for(Moving m:item) {
				if(m.collide(new Point(hero.x,hero.y))) {
					if(m.whatItem().equals("ph")) { 
						ph++;
						setPhbar(ph);
						item.remove(m);
						break;
					}
					else if(m.whatItem().equals("power")) {
						power=true;
						item.remove(m);
						break;
					}
				}
			}
			// when hero bullet and attacker collide , attacker remove
			for(Bullet b:weapon) {
				for(Moving m:alian) {
					if(b.collide(m)) {	
						m.isdie= true; // if isdie is true, do not create alian bullet
						alian.remove(m);	
						break;
					}
				}
				for(Moving m:sbb) {
					if(b.collide(m)) {
						if(m.whatAttacker() == "stone"){
							m.setImage(new ImageIcon("src/Image/stone_destroyed.gif").getImage());	
							sbb.remove(m);
							break;
						}else if(m.whatAttacker() == "boom") {
							m.setImage(new ImageIcon("src/Image/boom.gif").getImage());
							sbb.remove(m);
							break;

						}else {
							sbb.remove(m);
							break;
						}
					}
				}
			}
			//when attacker bullet and hero collide, hero ph minuous,invincible true 
			if(!hero.invincible) {
				for(Bullet b :Attacker_weapon) {
					if(b.collide(hero))
					{
						ph--;
						setPhbar(ph);
						hero.invincible = true;
						invincibleTimer =100;
					}
				}
			}

			//hero Image setting 
			if(attacked) {
				hero.setImage(hero_invincibility.getImage());
				attacked =false;
			}
			if(restart)
				hero.setImage(hero_flybasic.getImage());

			if(space) {
				hero.setImage(hero_attackbasic.getImage());
			}

			//hero invincible 
			if(invincibleTimer>0) { 
				invincibleTimer--;	
				hero.setImage(hero_invincibility.getImage());
			}
			else{	
				hero.invincible = false;
			}
			if(invincibleTimer == 1){
				hero.setImage(hero_attackbasic.getImage());
			}

			//when alian and hero collide ,hero ph minuous,invincible true 
			if(!alian.isEmpty()) {
				if(!hero.invincible) {
					for (Moving m : alian) {
						if (m.collide(new Point(hero.x, hero.y))) {
							//boomSound.play();					// 충돌의 음향
							hero.invincible = true;
							invincibleTimer =100;
							attacked=true;
							ph--;
							setPhbar(ph);
						}
					}
				}
				for (Moving m : alian) {
					if(m.outside()) {  //if alian outside , remove 
						alian.remove(m);
						break;
					}
					m.Horizontallymove();
				}
			}
			
			//when sbb and hero collide , hero ph minuous ,invincible true 
			if(!sbb.isEmpty()) {
				if(!hero.invincible) {
					for (Moving m : sbb) {
						if (m.collide(new Point(hero.x, hero.y))) {
							//boomSound.play();					// 충돌의 음향
							hero.invincible = true;
							invincibleTimer =100;
							attacked=true;
							ph--;
							setPhbar(ph);
						}
					}
				}
				//if bomb of sbb , Dianallymove 
				//else if bird of sbb , Horizontallymove 
				for (Moving m : sbb) {
					if(m.boom) {
						m.Diaonallymove();
						if(m.outside()) {
							sbb.remove(m);
							break;
						}
					}
					else if(m.bird) {
						m.Horizontallymove();
						if(m.outside()) {
							sbb.remove(m);
							break;
						}
					}	
				}
			}
			//coin if Horizontallymove 
			if(!coinList.isEmpty()) {
				for(Moving m: coinList) {
					m.Horizontallymove();
				}
			}
		}
	}
}



