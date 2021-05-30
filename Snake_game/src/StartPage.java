import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class StartPage {
	JFrame f;
	MainClass main;
	
	// 호스트, 클라이언트 선택 및 서버 열기.... 서버 담당자와 논의 필요
	public StartPage( MainClass mMain ) {
		main = mMain;
		
		f = new JFrame();
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
		// setResizable(false);
		// 창열기
		f.setSize( JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
		f.setExtendedState( JFrame.MAXIMIZED_BOTH );
		// 중앙 정렬
		f.setLocationRelativeTo(null);
		// 프레임 visible
		f.setVisible(true);
		// backGround
		f.setBackground( Color.lightGray );
		// 프레임 close
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	// Menu Interaction
	class ButtonClickListener implements ActionListener {
		public void nicknamePop() {
			String s = JOptionPane.showInputDialog( "nickname" );
			if ( s != null ) {
				main.nickname = main.nickname.concat( s );
				
				f.dispose(); // 현재 창 끄기

				main.gameStart(); // 게임시작
			}
		}
		public void hostPop() {
			String portS = JOptionPane.showInputDialog("port number" );
			if ( portS != null ) {
				main.port = Integer.parseInt(portS);
				main.mode = 1;
				nicknamePop();
			}
		}
		public void clientPop() {
			String portS = JOptionPane.showInputDialog("port number" );
			if ( portS != null ) {
				main.port = Integer.parseInt(portS);
				main.mode = 2;
				nicknamePop();
			}
		}
		public void ipPop() {
			String ipS = JOptionPane.showInputDialog("IP");
			if ( ipS != null ) {
				main.IP = ipS;
				clientPop();
			}
		}
		
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			
			if ( s.equals("as a host") ) {
				hostPop();
			}
			else if ( s.equals( "as a client" ) ) {
				ipPop();
			}
		}
	}
}
