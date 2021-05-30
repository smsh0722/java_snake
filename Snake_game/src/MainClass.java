import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.net.ServerSocket;
import java.net.Socket;

public class MainClass {
	String nickname = "" ;	// ���� �г���, ������ũ, ��� ��� ������ �� ���
	String IP = ""; 		// client�� ��� host IP �ʿ�
	int port; 				// port ��ȣ
	int mode; 				// 1 == host, 2 == client
	static int MX_SZ = 10;
	public static Socket[] connectList = new Socket[MX_SZ];//ArrayList�� ���� ����
	
	public MainClass(){
		
	}
	
	public static void main(String[] args) {
		MainClass mMain = new MainClass();
		
		// �⺻ ���� �ޱ� ���� ȭ��, �⺻ ������ �ްԵǸ� gameStart() �����.
		StartPage SP = new StartPage(mMain);
		
	}

	public void gameStart() {
		GameBoard myGame = new GameBoard(); // ���� ȭ�� ����
		
		// Debug
		System.out.println( "nickname: " + nickname );
		System.out.println( "IP: " + IP );
		System.out.println( "port: " + port );
		System.out.println( "mode: " + mode );
		
		// Hosting
		ServerSocket serverSocket = null;
		if(mode==1) {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IP = "localhost";
		}
		/* 
		 */
		
		myGame.nickname = nickname;		myGame.IP = IP;
		myGame.port = port;		myGame.mode = mode;
		/*
		 */

		// Control Thread
		// ����) ���� ���� ���� ��Ʈ��
		GameTask myST = new GameTask( myGame );
		new Thread( myST ).start();

		// Client
		// �ٸ� ���� ���� ��ٸ���
		if(mode==1) {
			int userNum = 0;
			
			while(true) {
				try {
					connectList[userNum] = serverSocket.accept();
					new Thread( new Client( myGame, connectList[userNum] ) ).start();
					System.out.println( "new User "+Integer.toString(userNum)+" connected." );
					userNum++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// ����) �ٸ� ��� ���� �������� x,y �޾ƿ� �ؽ��ʿ��� ã�Ƴ� move ��Ŵ
	}
}
