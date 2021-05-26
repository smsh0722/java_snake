import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class GameBoard extends JFrame {
	JFrame myFrame;
	
	public int mode = 0; // 0 = none, 1 == hosting, 2 == client
	
	static int port = 0;
	static String nickname = "";
	
	public HashMap<Integer, Snake> map = new HashMap<>();
	
	// Basic GUI
	GameBoard(){
		// 프레임 생성
		myFrame = new JFrame( "Snake" );
		
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mConnect = new JMenu("Connect");
		
		JMenuItem itemHost = new JMenuItem("as a host");
		JMenuItem itemClient = new JMenuItem("as a client");
		
		mConnect.add( itemHost );
		mConnect.add( itemClient );
		
		menuBar.add(mConnect);
		
		myFrame.setJMenuBar( menuBar );
		
		itemHost.addActionListener(new ButtonClickListener() );
		itemClient.addActionListener(new ButtonClickListener() );
		
		// 창열기
		myFrame.setSize( 700, 500 );
		// 중앙 정렬
		myFrame.setLocationRelativeTo(null);
		// 창모드
		myFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
		// 프레임 visible
		myFrame.setVisible(true);
		// 프레임 close
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Each Snake display itself
	public void game( Snake snake ) {
		PointerInfo myPointer = MouseInfo.getPointerInfo();
		int x, y;
		// Display snakes
			
		// Mouse Position
		myPointer = MouseInfo.getPointerInfo();
		x = myPointer.getLocation().x;
		y = myPointer.getLocation().y;
		System.out.println( "Mouse>> x: " + x + ", y: " + y ); // Debug
			
		// Set direction
			
		// Output direction
			
		// Input direction
			
		// Update each snake
	}
	
	// Menu Interaction
	class ButtonClickListener implements ActionListener {
		public void nicknamePop() {
			String s = JOptionPane.showInputDialog( "nickname" );
			if ( s != null ) nickname = nickname.concat( s );
			else mode = 0;
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
