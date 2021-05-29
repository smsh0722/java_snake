import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
			fromServer = new DataInputStream (socket.getInputStream());
			toServer = new DataOutputStream (socket.getOutputStream());
			//send nickname to server
			
			
			while ( myGame.mySnake.isAlive ) {
				try {
					Thread.sleep( 16 ); // 16ms >> 60fps
					// boost를 원한다면 17보다 작게
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				myGame.Control();//내 스네이크 조종
				toServer.writeDouble(myGame.x);
				toServer.writeDouble(myGame.y);//내 마우스 좌표 송신
				
				
				
			}
			myGame.snakes.remove( myGame.nickname ); // 자신의 해쉬맵에서 자신 제거
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
}
