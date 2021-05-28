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
	public static void main(String[] args) {
		
		// Hosting
		/* 
		 */
		
		// Client
		/*
		 */
		
		GameBoard myGame = new GameBoard();

		// Control Thread
		// 참고) 나의 것은 직접 컨트롤
		GameTask myST = new GameTask( myGame );
		new Thread( myST ).start();
		
		// 참고) 다른 사람 것은 서버에서 x,y 받아와 해쉬맵에서 찾아내 move 시킴
		
	}
}
