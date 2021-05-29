import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainClass {
	String nickname = "" ;	// 나의 닉네임, 스네이크, 통신 등에서 구별할 때 사용
	String IP = ""; 		// client의 경우 host IP 필요
	int port; 				// port 번호
	int mode; 				// 1 == host, 2 == client
	
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
		/* 
		 */
		
		// Client
		/*
		 */

		// Control Thread
		// 참고) 나의 것은 직접 컨트롤
		GameTask myST = new GameTask( myGame );
		new Thread( myST ).start();
		
		// 참고) 다른 사람 것은 서버에서 x,y 받아와 해쉬맵에서 찾아내 move 시킴
	}
}
