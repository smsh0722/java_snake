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
			
			while ( myGame.mySnake==null || myGame.mySnake.isAlive ) {
				try {
					Thread.sleep( 16 ); // 16ms >> 60fps
					// boost를 원한다면 17보다 작게
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				myGame.Control();//내 snake 조종
				
				//send my mouse position
				toServer.writeUTF( format("updtPos", myGame.nickname, myGame.x, myGame.y) );
				toServer.flush();
				/*
				Set<String> keys = myGame.snakes.keySet();
				for ( String key: keys) {
					Snake snake2 = myGame.snakes.get(key);
					snake.Collision( snake, snake2 );
				}*/
			}
			myGame.snakes.remove( myGame.nickname ); // 자신의 해쉬맵에서 자신 제거
			//send my death position
			toServer.writeUTF( format("dead", myGame.nickname, myGame.mySnake.headPoint.getX(), myGame.mySnake.headPoint.getY()) );
			/* 서버에 죽었음을 닉네임과 함께 전송
			 * 모든 유저에 뿌리기
			 * 각 유저마다 자신의 해쉬맵에서 해당 스네이크 제거
			 */
			
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
