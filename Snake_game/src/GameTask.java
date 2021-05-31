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
					// boost�� ���Ѵٸ� 17���� �۰�
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				myGame.Control();//�� snake ����
				
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
			myGame.snakes.remove( myGame.nickname ); // �ڽ��� �ؽ��ʿ��� �ڽ� ����
			//send my death position
			toServer.writeUTF( format("dead", myGame.nickname, myGame.mySnake.headPoint.getX(), myGame.mySnake.headPoint.getY()) );
			/* ������ �׾����� �г��Ӱ� �Բ� ����
			 * ��� ������ �Ѹ���
			 * �� �������� �ڽ��� �ؽ��ʿ��� �ش� ������ũ ����
			 */
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// ȣ��Ʈ�� ���ų� ip�� �߸� �Է���
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String format(String status, String nickname, double x, double y) {
		return status + "/" + nickname + "/" + Double.toString(x) + "/" + Double.toString(y);
	}
}
