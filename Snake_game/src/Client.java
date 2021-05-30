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
	double x; double y;
	
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	
	Client( GameBoard myGame, Socket socket ){
		this.myGame = myGame;
		this.socket = socket; // = serverSocket.accept()
	}
	
	public void run(){
		try {
			BufferedInputStream bis = new BufferedInputStream (socket.getInputStream());
			in = new DataInputStream(bis);
			BufferedOutputStream bos = new BufferedOutputStream (socket.getOutputStream());
			out = new DataOutputStream(bos);
			
			//�̴ϼȶ���¡
			
			//������ ���� ��̿� �����ϰ�
			//��� ����� �г���+x+y�� ��� �ѷ���� �ұ�? ���� Ÿ������...? 
			
			do {
				//
				System.out.println("Client waiting to read: "); //debug
				String inputLine = in.readUTF();
				System.out.println("Client reads: " + inputLine); //debug
				String[] input = inputLine.split("/");
				switch(input[0]) {
				case "enter":
					//�ʱ� ������ġ ������ ����
					nickname = input[1];
					input[2] = Double.toString(100 + Math.random() * 400);
					input[3] = Double.toString(Math.random() * 100 + 400);
					inputLine = String.join("/", input); //�������� �ڵ� �˼��մϴ�..
					break;
				case "updtPos":
					break;
				case "dead":
					break;
				default:
					throw new IOException("unknown input command");
				}
				//�ٸ� ��� Ŭ�� �ش� ���� ����
				userNum = 0;
				for(Socket clnt: MainClass.connectList) {
					BufferedOutputStream tmpBos = new BufferedOutputStream (clnt.getOutputStream());
					out = new DataOutputStream(tmpBos);
					out.writeUTF(inputLine);
					out.flush();
					System.out.println("Client writes on clnt "+ Integer.toString(userNum++) + ": " + inputLine); //debug
				}
			}while ( myGame.mySnake==null || myGame.mySnake.isAlive );
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
