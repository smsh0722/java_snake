
public class GameTask implements Runnable {
	public GameBoard myGame = null;
	int num;
	Snake snake;
	
	GameTask( GameBoard myGame, int num ){
		this.myGame = myGame;
		
		this.num = num;
		
		snake = new Snake();
		myGame.map.put( num, snake );
	}
			
	public void run() {
		while ( snake.isAlive() ) {
			myGame.game( snake );
		}
		myGame.map.remove( num );
	}
}
