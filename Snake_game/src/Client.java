import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{
	int userNum;
	GameBoard myGame;
	String nickname;
	int x; int y;
	
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	
	Client( GameBoard myGame, Socket socket, int userNum ){
		this.myGame = myGame;
		this.socket = socket; // = serverSocket.accept()
		this.userNum = userNum;
	}
	
	public void run(){
		try {
			BufferedInputStream bis = new BufferedInputStream (socket.getInputStream());
			in = new DataInputStream(bis);
			BufferedOutputStream bos = new BufferedOutputStream (socket.getOutputStream());
			out = new DataOutputStream(bos);
			
			//�̴ϼȶ���¡
			nickname = in.readLine();
			x= in.readDouble();
			y= in.readDouble();
			//������ ���� ��̿� �����ϰ�
			//��� ����� �г���+x+y�� ��� �ѷ���� �ұ�? ���� Ÿ������...? 
			
			while ( myGame.mySnake.isAlive ) {
				//
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// �����ϰ� �������� ���� ���� x, y �ޱ�, �� ������ũ ����: mySnake = new snake( x, y ), �� �ʿ� �߰�: snakes.put( nickname, mySnake );
	// ���ο� ���� �����ϸ� ������ũ (x, y)�� �г��� ���� �ް�, �� �ʿ� �߰�: snakes.put( nickname, new snake(x, y) )
	
	// ���� ������ũ ���콺(x,y) ����
	// �ٸ� ������ ������ũ ���콺 �ޱ�(x, y), snakes.get( nickname ).move( x, y );
	
	// ���� ������ ���̸� �߰� ��Ű��, �ٸ� ������ �Ѹ���
	// ���� ������ ������ �˸���, ���� ���������� �ʿ� ���̸� ����� �����鿡�� �Ѹ���, ���� �Ծ����� �˸���
	
	// �ڽ� ���� �� �˸�
	// �ٸ� ���� ������ �˸� �ޱ�
}
