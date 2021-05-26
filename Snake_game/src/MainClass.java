import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class MainClass {
	public static void main(String[] args) {
		GameBoard myGame = new GameBoard();
		int myNum = 0;
		
		// Client
		/*
		Client myC = new Client(myGame);
		new Thread( myC ).start();
		myNum = myC.getUserNum();
		new Thread( myC ).start();
		*/
		
		// Hosting
		/* if ( myC.mode == 1 ){
		 *
		 * 		new Thread( ht ).start()
		 * }
		*/
		
		// my Snake Thread
		GameTask myST = new GameTask( myGame, myNum );
		new Thread( myST ).start();
		
		// x Snake Thread
		/*
		do {
			int userNum = 0;
			while ( myC.isEnter() ) { // 새로운 유저 입장 확인
				userNum = myC.newUser();
			}
			
			GameTask xST = new GameTask( myGame, userNum );
			new Thread( xST ).start();
			break;
		} while ( true );
		*/
	}
}
