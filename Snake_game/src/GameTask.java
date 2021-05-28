
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
		myGame.snakes.remove( myGame.nickname ); // 자신의 해쉬맵에서 자신 제거
		/* 서버에 죽었음을 닉네임과 함께 전송
		 * 모든 유저에 뿌리기
		 * 각 유저마다 자신의 해쉬맵에서 해당 스네이크 제거
		 */
	}
}
