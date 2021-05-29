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
					// boost�� ���Ѵٸ� 17���� �۰�
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				myGame.Control();//�� ������ũ ����
				toServer.writeDouble(myGame.x);
				toServer.writeDouble(myGame.y);//�� ���콺 ��ǥ �۽�
				
				
				
			}
			myGame.snakes.remove( myGame.nickname ); // �ڽ��� �ؽ��ʿ��� �ڽ� ����
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
}
