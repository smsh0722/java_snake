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
	
	// ���콺 ��ġ
	double x, y;
	
	// ��� ��
	public int mode = 0; // 0 = none, 1 == hosting, 2 == client
	static int port = 0;
	int IP;
	
	// ���� ����
	static String nickname = "defualt"; // �г���
	Snake mySnake;				// ���� ������ũ
	
	// ������ũ ��
	public HashMap<String, Snake> snakes = new HashMap<>(); // �г������� ����
	
	// ���� ��
	
	// Paint ��
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
		
		// ���� ��ġ���� ����, ����) ������ �� �������� �������� ������ġ �ִ� ������ ���� �ʿ�
		mySnake = new Snake( 100 + Math.random() * 400, Math.random() * 100 + 400);
		snakes.put( nickname, mySnake ); // �ʿ� �߰�
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
		
		drawSnake(g); // snake �׸���
		// drawFeeds(g); // ���� �׸���
		
		g.drawImage( buffImg, 0, 0, this ); // ȭ�鿡 ����(buffG)�� �׷��� �̹���(buffImg)�� �׸�
	}
	
	public void drawSnake( Graphics g ) {
		Set<String> keys = snakes.keySet();
		for ( String key : keys ) {
			Snake snake = snakes.get(key);
			for ( Integer i = 0; i < snake.snakeLocationList.size(); i++ ) {
				double x = snake.snakeLocationList.get(i).x;
				double y = snake.snakeLocationList.get(i).y;
				
				buffG.setColor( Color.red ); // ���߿� ���� ����� ���� �ʿ�
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
		
		// ���� ������ũ �̵�
		this.mySnake.move( x, y );
		
		// ������ x, y ����
		
	}
	
	// ȣ��Ʈ, Ŭ���̾�Ʈ ���� �� ���� ����.... ���� ����ڿ� ���� �ʿ�
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
		
		
		// ������ ũ�� ���� x
		setResizable(false);
		// â����
		f.setSize( 300, 200 );
		// �߾� ����
		f.setLocationRelativeTo(null);
		// ������ visible
		f.setVisible(true);
		// ������ close
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
