import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class GameBoard extends JFrame {
	JFrame myFrame;
	int port;
	String nickname;
	
	GameBoard(){
		// ������ ����
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
		
		// â����
		myFrame.setSize( 700, 500 );
		// �߾� ����
		myFrame.setLocationRelativeTo(null);
		// â���
		myFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
		// ������ visible
		myFrame.setVisible(true);
		// ������ close
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main ( String args[] ) {
		// ȭ�� ����
		GameBoard myGameBoard = new GameBoard();
		
		// new snake
		
		PointerInfo myPointer = MouseInfo.getPointerInfo();
		int x, y;
		while(true) {
			// Display snakes
			
			// Mouse Position
			myPointer = MouseInfo.getPointerInfo();
			x = myPointer.getLocation().x;
			y = myPointer.getLocation().y;
			System.out.println( "x: " + x + ", y: " + y ); // Debug
			
			// Set direction
			
			// Output direction
			
			// Input direction
			
			// Update each snake
			
		}
		
	}

	class ButtonClickListener implements ActionListener {
		public void host() {
			JOptionPane hPop =new JOptionPane();
			hPop.showMessageDialog( null, "port number" );
			
		}
		public void client() {
			JOptionPane hPop =new JOptionPane();
			hPop.showMessageDialog( null, "port number" );
		}
		
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			
			if (s.equals("host") ) {
				host();
			}
			else if ( s.equals( "client" ) ) {
				client();
			}
		}
	}
}
