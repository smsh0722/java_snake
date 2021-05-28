
public class GameTask implements Runnable {
	public GameBoard myGame = null;
	int num;
	Snake snake;
	
	GameTask( GameBoard myGame ){
		this.myGame = myGame;
	}
			
	public void run() {
		while ( myGame.mySnake.isAlive ) {
			try {
				Thread.sleep( 17 ); // ~= 60fps
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myGame.Control();
		}
		myGame.snakes.remove( myGame.nickname );
		// 서버에 죽었음을 전송
		// 닉네임 전송
	}
}
