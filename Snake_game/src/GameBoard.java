import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	int IP;
	
	// 고유 정보
	static String nickname = "defualt"; // 닉네임
	Snake mySnake;				// 나의 스네이크
	
	// 스네이크 맵
	public HashMap<String, Snake> snakes = new HashMap<>(); // 닉네임으로 구별
	
	// 먹이 맵
	
	// Paint 용
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
		
		drawSnake(g); // snake 그리기
		// drawFeeds(g); // 먹이 그리기
		
		g.drawImage( buffImg, 0, 0, this ); // 화면에 버퍼(buffG)에 그려진 이미지(buffImg)를 그림
	}
	
	public void drawSnake( Graphics g ) {
		Set<String> keys = snakes.keySet();
		for ( String key : keys ) {
			Snake snake = snakes.get(key);
			for ( Integer i = 0; i < snake.snakeLocationList.size(); i++ ) {
				double x = snake.snakeLocationList.get(i).x;
				double y = snake.snakeLocationList.get(i).y;
				
				buffG.setColor( Color.red ); // 나중에 고유 색깔로 변경 필요
				buffG.fillOval( (int)x, (int)y, 12, 12);
			}
		}
	}
	
	// Mouse Control
	public void Control( ) {
		// Mouse Position
		PointerInfo myPointer = MouseInfo.getPointerInfo();
		myPointer = MouseInfo.getPointerInfo();
		x = myPointer.getLocation().x;
		y = myPointer.getLocation().y;
		System.out.println( "Mouse>> x: " + x + ", y: " + y ); // Debug
		
		// 나의 스네이크 이동
		this.mySnake.move( x, y );
		
		// 서버에 x, y 전송
		
	}
	
	// 호스트, 클라이언트 선택 및 서버 열기.... 서버 담당자와 논의 필요
	public void Starting() {
		JFrame f = new JFrame();
		f.setTitle("Snake");
		
		// Menu Bar/
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds( 0, 0, JFrame.MAXIMIZED_HORIZ, 100);
		
		JMenu mConnect = new JMenu("Connect");
		
		JMenuItem itemHost = new JMenuItem("as a host");
		JMenuItem itemClient = new JMenuItem("as a client");
		
		mConnect.add( itemHost );
		mConnect.add( itemClient );
		
		menuBar.add(mConnect);
		
		f.setJMenuBar( menuBar );
		
		itemHost.addActionListener(new ButtonClickListener() );
		itemClient.addActionListener(new ButtonClickListener() );
		
		
		// 프레임 크기 조정 x
		setResizable(false);
		// 창열기
		f.setSize( 300, 200 );
		// 중앙 정렬
		f.setLocationRelativeTo(null);
		// 프레임 visible
		f.setVisible(true);
		// 프레임 close
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	// Menu Interaction
	class ButtonClickListener implements ActionListener {
		public void nicknamePop() {
			String s = JOptionPane.showInputDialog( "nickname" );
			if ( s != null ) {
				nickname = nickname.concat( s );
				notifyAll();
			}
		}
		public void hostPop() {
			String portS = JOptionPane.showInputDialog("port number" );
			if ( portS != null ) {
				port = Integer.parseInt(portS);
				mode = 1;
				nicknamePop();
			}
		}
		public void clientPop() {
			String portS = JOptionPane.showInputDialog("port number" );
			if ( portS != null ) {
				port = Integer.parseInt(portS);
				mode = 2;
				nicknamePop();
			}
		}
		
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			
			if ( s.equals("as a host") ) {
				hostPop();
			}
			else if ( s.equals( "as a client" ) ) {
				clientPop();
			}
		}
	}
	
}
