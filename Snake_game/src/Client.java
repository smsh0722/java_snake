import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{
	int userNum;
	boolean isAlive = true;//myGame.snake.isAlive�� ���� �׾� �������� ���� �Ұ��ϹǷ� ���ú��� ����
	GameBoard myGame;
	String nickname;
	double x; double y;
	
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	DataOutputStream tmpOut;
	
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
			
			do {
				String inputLine = in.readUTF();
				String[] input = inputLine.split("/");
				if(!input[0].equals("updtPos")) {System.out.println("Client reads: " + inputLine);} //debug
				switch(input[0]) {
				case "enter":
					//�ʱ� ������ġ ������ ����
					nickname = input[1];
					inputLine = format("enter", nickname, 100 + Math.random() * 400, Math.random() * 100 + 400);
					
					//iterator�� myGame.snakes(ȣ��Ʈ�� snakes)�� ���鼭 �г�/��ġ�� ���� enter�� socket (������)�� ����
					Iterator<String> keys = myGame.snakes.keySet().iterator();
			        while( keys.hasNext() ){
			            String key = keys.next();
			            Snake tmpSnk = myGame.snakes.get(key);
			            System.out.println( String.format("Ű : %s, �� : %s", key, tmpSnk) );
			            out.writeUTF( format("enter", key, tmpSnk.headPoint.getX(), tmpSnk.headPoint.getY()) );
			            out.flush();
			        }
			        
					break;
				case "updtPos":
					break;
				case "dead":
					isAlive = false;
					break;
				default:
					throw new IOException("unknown input command");
				}
				//�ٸ� ��� Ŭ�� �ش� ���� ����
				userNum = 0;
				
				for(Socket clnt: MainClass.connectList) {
					BufferedOutputStream tmpBos = new BufferedOutputStream (clnt.getOutputStream());
					tmpOut = new DataOutputStream(tmpBos);
					tmpOut.writeUTF(inputLine);
					tmpOut.flush();
					if(!input[0].equals("updtPos")) {System.out.println("Client writes on clnt "+ Integer.toString(userNum++) + ": " + inputLine);} //debug
				}
//			}while ( myGame.mySnake==null || myGame.mySnake.isAlive );
			}while ( isAlive );
			
			System.out.println("Client of dead socket Finally escaped the loop!");
			//������ �Ұ����ҵ� �մϴ�.....
			if(nickname!=myGame.nickname) {
				MainClass.connectList.remove(socket);
			}//ȣ��Ʈ�� �׾ ������ ����. (�� ������ �޴� snakes �ؽø��� ȣ��Ʈ�� ���� ����ϹǷ� ȣ��Ʈ�� �׾ �ٸ� ������ ����/������ �޾ƾ� ��.)
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String format(String status, String nickname, double x, double y) {
		return status + "/" + nickname + "/" + Double.toString(x) + "/" + Double.toString(y);
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
