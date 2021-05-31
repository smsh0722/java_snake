import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

public class GameTask implements Runnable {
	public GameBoard myGame = null;
	int num;
	Snake snake;
	
	DataOutputStream toServer;
	DataInputStream fromServer;
	Socket socket;
	
	GameTask( GameBoard myGame ){
		this.myGame = myGame;
	}
			
	public void run() {
		try {
			socket = new Socket(myGame.IP, myGame.port);
			
			BufferedInputStream bis = new BufferedInputStream (socket.getInputStream());
			fromServer = new DataInputStream(bis);
			BufferedOutputStream bos = new BufferedOutputStream (socket.getOutputStream());
			toServer = new DataOutputStream(bos);
			
			//send nickname to server
			toServer.writeUTF( format("enter", myGame.nickname, 0, 0) );
			toServer.flush();
			System.out.println("GameTask writes nickname"); //debug
			new Thread( new Communicate( myGame, socket ) ).start();//communicate with server and adapt to myGame
			
			while(myGame.mySnake==null) {
				try {
					Thread.sleep( 16 ); // 16ms >> 60fps
					// boost를 원한다면 17보다 작게
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			while ( !myGame.isExit ) {
//			while ( myGame.mySnake==null || myGame.mySnake.isAlive ) {
				try {
					Thread.sleep( 16 ); // 16ms >> 60fps
					// boost를 원한다면 17보다 작게
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//살아있는지 테스트
				if(!myGame.mySnake.isAlive) {
					//send my death position
					toServer.writeUTF( format("dead", myGame.nickname, myGame.mySnake.headPoint.getX(), myGame.mySnake.headPoint.getY()) );
					toServer.flush();
					System.out.println("GameTask sent dying message to server.");
					/* 서버에 죽었음을 닉네임과 함께 전송
					 * 모든 유저에 뿌리기
					 * 각 유저마다 자신의 해쉬맵에서 해당 스네이크 제거
					 */
					while ( !myGame.isExit ) {
						try {
							Thread.sleep( 16 );
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}//관전모드
					break;
				}
				
				myGame.Control();//내 snake 조종
				
				//send my mouse position
				toServer.writeUTF( format("updtPos", myGame.nickname, myGame.x, myGame.y) );
				toServer.flush();
				
				// 충돌 테스트
				Set<String> keys = myGame.snakes.keySet();
				for ( String key: keys) {
					Snake snakeX = myGame.snakes.get(key);
					if ( snakeX != myGame.mySnake ) myGame.mySnake.CollisionSnake( snakeX );
				}
				// 경계 테스트
				// myGame.mySnake.BoardOut();
				// 먹이 충돌 테스트
				/*	if(mySnake.CollisionFeed){
				 * 		//내 Snake가 먹이 먹었다고 서버에 전달
				 * 		toServer.writeUTF( format("feed", myGame.nickname, myGame.x, myGame.y) );
				 *		toServer.flush();
				 *		//그럼 서버에서 먹은사람과 새로운 먹이 위치를 보내줄거임.
				 *		//그다음 일들은 Communication에서 처리
				 * 	}
				 */
				
			}
			//서버에 겜종 알림
			toServer.writeUTF( format("exit", myGame.nickname, 0, 0) );
			toServer.flush();
			System.out.println("exiting...");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 호스트가 없거나 ip를 잘못 입력함
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String format(String status, String nickname, double x, double y) {
		return status + "/" + nickname + "/" + Double.toString(x) + "/" + Double.toString(y);
	}
}
