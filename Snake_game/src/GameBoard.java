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
	
	// 마우스 위치
	double x, y;
	
	// 통신 용
	public int mode = 0; // 0 = none, 1 == hosting, 2 == client
	static int port = 0;
	String IP;
	
	// 고유 정보
	static String nickname = "defualt"; // 닉네임
	Snake mySnake;				// 나의 스네이크
	
	// 스네이크 맵
	public HashMap<String, Snake> snakes = new HashMap<>(); // 닉네임으로 구별
	
	// 먹이 맵
	
	// Paint 용
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
		
		// 랜덤 위치에서 시작, 참고) 접속할 때 서버에서 랜덤으로 시작위치 주는 것으로 변경 필요
		mySnake = new Snake( 100 + Math.random() * 400, Math.random() * 100 + 400);
		snakes.put( nickname, mySnake ); // 맵에 추가
		
		// 창 닫을 시 프로그램 종료
		this.addWindowListener( new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if ( mySnake.isAlive ) 
					// 서버에 죽었음을 알리기
					
				// 시스템 종료
				System.exit(0);
		    }
		});
	}
	
	// Painting
	public void paint( Graphics g ) {
		repaint();
		
		buffImg = createImage( getWidth(), getHeight() ); // 도화지 생성
		buffG = buffImg.getGraphics(); // 그래픽용 객체 얻기
		
		update(g);
	}
	public void update( Graphics g ) {
		buffG.clearRect( 0, 0, 700, 500 ); // 백지화
	
		drawSnake(g); 	// snake 그리기
		drawScore(g); 	// 점수판
		// drawFeeds(g); // 먹이 그리기
		
		g.drawImage( buffImg, 0, 0, this ); // 화면에 버퍼(buffG)에 그려진 이미지(buffImg)를 그림
	}
	public void drawSnake( Graphics g ) {
		Set<String> keys = snakes.keySet();
		for ( String key : keys ) {
			Snake snake = snakes.get(key);
			buffG.setColor( Color.red ); // 나중에 고유 색깔로 변경 필요
			for ( Integer i = 0; i < snake.snakeLocationList.size(); i++ ) {
				double x = snake.snakeLocationList.get(i).x;
				double y = snake.snakeLocationList.get(i).y;
				buffG.fillOval( (int)x, (int)y, 12, 12);
			}
		}
	}
	public void drawScore( Graphics g ) {
		buffG.setColor( Color.CYAN );
		buffG.setFont(new Font("TimesRoman", Font.PLAIN, 20) ); 
		buffG.drawString( "Your Length: " + mySnake.bodylen, 50, 70);
	}
	
	// Mouse Control
	public void Control( ) {
		// Mouse Position
		PointerInfo myPointer = MouseInfo.getPointerInfo();
		x = myPointer.getLocation().x;
		y = myPointer.getLocation().y;
		// System.out.println( "Mouse>> x: " + x + ", y: " + y ); // Debug
		
		// 나의 스네이크 이동
		this.mySnake.move( x, y );
		
		// 서버에 x, y 전송
		
	}
}
