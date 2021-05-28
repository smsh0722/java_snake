
public class GameTask implements Runnable {
	public GameBoard myGame = null;
	int num;
	Snake snake;
	
	GameTask( GameBoard myGame, int num ){
		this.myGame = myGame;
		
		this.num = num;
		
		// ���� ��ġ ����
		snake = new Snake( 200 + Math.random() * 200, Math.random() * 200 + 200);
		
		// ������ũ �ؽ��� �ֱ�
		myGame.snakes.put( num, snake );
	}
			
	public void run() {
		while ( snake.isAlive ) {
			try {
				Thread.sleep( 17 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myGame.game( snake );
		}
		myGame.snakes.remove( num );
	}
}
