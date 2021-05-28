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
		GameBoard myGame = new GameBoard();
		int myNum = 0;
		
		// Hosting
		/* if ( myC.mode == 1 ){
		 *
		 * 		new Thread( ht ).start()
		 * }
		*/
		
		// Client
		/*
		Client myC = new Client(myGame);
		new Thread( myC ).start();
		myNum = myC.getUserNum();
		new Thread( myC ).start();
		*/
		
		// my Snake Thread
		GameTask myST = new GameTask( myGame );
		new Thread( myST ).start();
	}
}
