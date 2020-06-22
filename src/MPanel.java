import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MPanel extends JPanel implements KeyListener, ActionListener {
//	引进图片
	ImageIcon title = new ImageIcon("image/title.jpg");
	ImageIcon food = new ImageIcon("image/food.png");
	ImageIcon body = new ImageIcon("image/body.png");
	ImageIcon up = new ImageIcon("image/up.png");
	ImageIcon right = new ImageIcon("image/right.png");
	ImageIcon down = new ImageIcon("image/down.png");
	ImageIcon left = new ImageIcon("image/left.png");
	
//	蛇的默认长度
	int len = 3;
	int score = 0;
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	String fx = "U"; //头的方向 R L U D
	boolean ifStarted = false;
	boolean ifFailed = false;
	Timer timer = new Timer(150,this);  //100毫秒的时钟
	int foodx;
	int foody;
	Random rand = new Random();
	
//	构造方法
	public MPanel() {
		initSnake(); 
		this.setFocusable(true); //可不可以获取焦点=获取键盘事件
		this.addKeyListener(this);  //让自身监听事件
		timer.start();
	}
	
//	画主界
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.white);
//		在画布上显示title
		title.paintIcon(this, g, 25, 11);
//		绘制游戏区域
		g.fillRect(25, 75, 850, 600);
//		绘制分数
		g.setColor(Color.WHITE);
		g.drawString("长度："+len, 750, 35);
		g.drawString("成绩："+score, 750, 55);
		
		if(fx == "R")
			right.paintIcon(this, g, snakex[0], snakey[0]);
		if(fx == "U")
			up.paintIcon(this, g, snakex[0], snakey[0]);
		if(fx == "L")
			left.paintIcon(this, g, snakex[0], snakey[0]);
		if(fx == "D")
			down.paintIcon(this, g, snakex[0], snakey[0]);
		
//		循环打出蛇的身体
		for(int i=1;i<len;i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		food.paintIcon(this, g, foodx, foody);
//	游戏开始提示
		if(ifStarted == false) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.drawString("Press Space to Stop", 250, 300);
		}
		if(ifFailed == true) {
			g.setColor(Color.RED);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.drawString("Game Over!", 250, 300);
		}


		
}
	
//	初始化蛇的一些数据、初始长度和坐标，
	public void initSnake() {
		len=3;
		snakex[0]=100;
		snakey[0]=100;
		snakex[1]=75;
		snakey[1]=100;
		snakex[2]=50;
		snakey[2]=100;
		foodx = 25+25*rand.nextInt(34);  //0-33取整数值
		foody = 75+25*rand.nextInt(24);
		fx = "R";
		score = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(ifFailed) {
				ifFailed = false;
				initSnake();
			}else {
				ifStarted = !ifStarted;  //取反后得重画
			}
			repaint();
		}else if(keyCode == KeyEvent.VK_LEFT) {
			fx = "L";
		}else if(keyCode == KeyEvent.VK_RIGHT) {
			fx = "R";
		}else if(keyCode == KeyEvent.VK_UP) {
			fx = "U";
		}else if(keyCode == KeyEvent.VK_DOWN) {
			fx = "D";
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(ifStarted && !ifFailed) {
			for(int i=len-1;i>0;i--) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];	
			}
			if(fx == "R") {
				snakex[0] = snakex[0]+25;
				if(snakex[0]>850)snakex[0]=25;
			}else if(fx == "L") {
				snakex[0] = snakex[0]-25;
				if(snakex[0]<25)snakex[0]=850;
			}else if(fx == "U") {
				snakey[0] = snakey[0]-25;
				if(snakey[0]<75)snakey[0]=650;
			}else if(fx == "D") {
				snakey[0] = snakey[0]+25;
				if(snakey[0]>650)snakey[0]=75;
			}
			if(snakex[0]==foodx && snakey[0]==foody) {
				len++;
				score = score + 10;
				foodx = 25+25*rand.nextInt(34);  //0-33取整数值
				foody = 75+25*rand.nextInt(24);
			}
			for(int i=1;i<len;i++) {
				if(snakex[i]==snakex[0] && snakey[i]==snakey[0]) {
					ifFailed = true;
				};
			}
			repaint();
		}
		
		timer.start();
		
	}
	



	
}
