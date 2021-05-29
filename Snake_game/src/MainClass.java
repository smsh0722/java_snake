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
	String nickname = "" ;	// ���� �г���, ������ũ, ��� ��� ������ �� ���
	String IP = ""; 		// client�� ��� host IP �ʿ�
	int port; 				// port ��ȣ
	int mode; 				// 1 == host, 2 == client
	
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
		/* 
		 */
		
		// Client
		/*
		 */

		// Control Thread
		// ����) ���� ���� ���� ��Ʈ��
		GameTask myST = new GameTask( myGame );
		new Thread( myST ).start();
		
		// ����) �ٸ� ��� ���� �������� x,y �޾ƿ� �ؽ��ʿ��� ã�Ƴ� move ��Ŵ
	}
}
