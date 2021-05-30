import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
			BufferedInputStream bis = new BufferedInputStream (socket.getInputStream());
			fromServer = new DataInputStream(bis);
			BufferedOutputStream bos = new BufferedOutputStream (socket.getOutputStream());
			toServer = new DataOutputStream(bos);
			
			//send nickname to server
			toServer.writeUTF( format("enter", myGame.nickname, 0, 0) );
			System.out.println("GameTask writes nickname"); //debug
			
			do {
				try {
					Thread.sleep( 16 ); // 16ms >> 60fps
					// boost�� ���Ѵٸ� 17���� �۰�
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//�������� �޾ƿ� ������ ó��
				String inputLine = fromServer.readUTF();
				System.out.println("GameTask reads: " + inputLine); //debug
				String[] input = inputLine.split("/");
				double x = Double.parseDouble(input[2]);
				double y = Double.parseDouble(input[3]);
				
				switch(input[0]) {
				case "enter":
					if (input[1].equals(myGame.nickname)) {
						myGame.mySnake = new Snake( Double.parseDouble(input[2]), Double.parseDouble(input[3]) );
						myGame.snakes.put(myGame.nickname, myGame.mySnake);
					} //my Snake
					else {
						myGame.snakes.put( myGame.nickname, new Snake(x, y) );
					} //other snake entered
					break;
					
				case "updtPos":
					myGame.snakes.get( myGame.nickname ).move( x, y );
					break;
					
				case "dead":
					//���̻���..?
					myGame.snakes.remove( input[1] );
					break;
				default:
					throw new IOException("unknown input command");
				}
				
				
				myGame.Control();//�� snake ����
				//send my mouse position
				toServer.writeUTF( format("updtPos", myGame.nickname, myGame.x, myGame.y) );
				
				
				
			}while ( myGame.mySnake.isAlive );
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
