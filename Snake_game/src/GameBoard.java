import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GameBoard extends JFrame{
	Feed feed;
	// ���콺 ��ġ
	double x, y;
	
	// ��� ��
	public int mode = 0; // 0 = none, 1 == hosting, 2 == client
	static int port = 0;
	String IP;
	boolean isExit = false;
	
	// ���� ����
	static String nickname = "defualt"; // �г���
	Snake mySnake;				// ���� ������ũ //�����Ҷ� nullPointerException ���������� �ӽ� snake�� �Ҵ��ص�
	
	// ������ũ ��
	public HashMap<String, Snake> snakes = new HashMap<>(); // �г������� ����
	
	// ���� ��
	
	// Paint ��
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image buffImg;
	Graphics buffG;
	
	// Game GUI
	public GameBoard(){
		setTitle("Snake");
		setSize( JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
		setExtendedState( JFrame.MAXIMIZED_BOTH );
		setLocationRelativeTo(null);
		setVisible(true);
		setBackground( Color.lightGray );
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		// ���� ��ġ���� ����, ������ �� �������� �������� ������ġ �ִ� ������ ������.
//		mySnake = new Snake( 100 + Math.random() * 400, Math.random() * 100 + 400);
//		snakes.put( nickname, mySnake ); // �ʿ� �߰�
		
		// â ���� �� ���α׷� ����
		this.addWindowListener( new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if ( mySnake.isAlive ) {
					mySnake.isAlive = false;
				}
				// ������ ���� ���Ḧ �˸���
				isExit = true;
				try {
				    Thread.sleep(1000);
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}//�������� ���� ���� ��� Ŭ�󿡼� �� ����� ���� ����ϱ� - 1000ms���� �� �ٿ��� �ɵ�
				
				// �ý��� ����
				System.exit(0);
		    }
		});
	}
	
	// Painting
	public void paint( Graphics g ) {
		repaint();
		
		buffImg = createImage( getWidth(), getHeight() ); // ��ȭ�� ����
		buffG = buffImg.getGraphics(); // �׷��ȿ� ��ü ���
		
		update(g);
	}
	public void update( Graphics g ) {
		buffG.clearRect( 0, 0, 700, 500 ); // ����ȭ
	
		drawSnake(g); 	// snake �׸���
		drawScore(g); 	// ������
		// drawFeeds(g); // ���� �׸���
		
		g.drawImage( buffImg, 0, 0, this ); // ȭ�鿡 ����(buffG)�� �׷��� �̹���(buffImg)�� �׸�
	}
	public void drawSnake( Graphics g ) {
		Set<String> keys = snakes.keySet();
		for ( String key : keys ) {
			Snake snake = snakes.get(key);
			if ( snake == mySnake ) buffG.setColor( Color.BLUE);
			else 					buffG.setColor( Color.red ); 
			for ( Integer i = 0; i < snake.snakeLocationList.size(); i++ ) {
				double x = snake.snakeLocationList.get(i).x;
				double y = snake.snakeLocationList.get(i).y;
				buffG.fillOval( (int)x, (int)y, 12, 12);
			}
			buffG.drawString( key, (int)snake.snakeLocationList.get(0).x , (int)snake.snakeLocationList.get(0).y);
		}
	}
	public void drawScore( Graphics g ) {
		buffG.setColor( Color.CYAN );
		buffG.setFont(new Font("TimesRoman", Font.PLAIN, 20) ); 
		if(mySnake != null) {
			buffG.drawString( "Your Length: " + mySnake.bodylen, 50, 70);
		}
		//���� ä �������� ���Ͽ����� �������Ϳ��ܶ����� ������ �޾�����, ���� �Ŀ� ������ ��������� �� ����..
		if ( mySnake == null ) {
			buffG.drawString( "Observing..", 50, 70 );
		}
	}
	public void drawFeeds( Graphics g ) {
		buffG.setColor( Color.CYAN );
		/*
		for ( �� ���� �ޱ� ){
			double x = ���� x
			double y = ���� y
			buffG.fillOval( (int)x, (int)y, 12, 12 );
		}
		*/
	}
	
	// Mouse Control
	public void Control( ) {
		// Mouse Position
		PointerInfo myPointer = MouseInfo.getPointerInfo();
		x = myPointer.getLocation().x;
		y = myPointer.getLocation().y;
		// System.out.println( "Mouse>> x: " + x + ", y: " + y ); // Debug
		
		// ���� ������ũ �̵�
//		this.mySnake.move( x, y );
		
		// ������ x, y ����
		
	}
}
