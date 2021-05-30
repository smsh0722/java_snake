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
	String nickname = "" ;	// 나의 닉네임, 스네이크, 통신 등에서 구별할 때 사용
	String IP = ""; 		// client의 경우 host IP 필요
	int port; 				// port 번호
	int mode; 				// 1 == host, 2 == client
	static int MX_SZ = 10;
	public static Socket[] connectList = new Socket[MX_SZ];//ArrayList로 변경 예정
	
	public MainClass(){
		
	}
	
	public static void main(String[] args) {
		MainClass mMain = new MainClass();
		
		// 기본 정보 받기 위한 화면, 기본 정보를 받게되면 gameStart() 실행됨.
		StartPage SP = new StartPage(mMain);
		
	}

	public void gameStart() {
		GameBoard myGame = new GameBoard(); // 게임 화면 열기
		
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
		// 참고) 나의 것은 직접 컨트롤
		GameTask myST = new GameTask( myGame );
		new Thread( myST ).start();

		// Client
		// 다른 유저 입장 기다리기
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
		
		// 참고) 다른 사람 것은 서버에서 x,y 받아와 해쉬맵에서 찾아내 move 시킴
	}
}
