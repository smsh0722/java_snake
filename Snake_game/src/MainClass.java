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
		// ����) ���� ���� ���� ��Ʈ��
		GameTask myST = new GameTask( myGame );
		new Thread( myST ).start();
		
		// ����) �ٸ� ��� ���� �������� x,y �޾ƿ� �ؽ��ʿ��� ã�Ƴ� move ��Ŵ
		
	}
}
