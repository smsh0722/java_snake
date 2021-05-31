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
	int ClientNum;//�� �����尡 �����ϴ� Ŭ���̾�Ʈ�� ������ȣ
	int clntIdx;//�ٸ� �������� ���� �Ѹ��� ����������� �ִ� ����
	
	boolean isExit = false;
	GameBoard myGame;
	String nickname;
	double x; double y;
	
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	DataOutputStream tmpOut;
	
	Client( GameBoard myGame, Socket socket, int userNum ){
		this.myGame = myGame;
		this.socket = socket; // = serverSocket.accept()
		this.ClientNum = userNum;
		
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
				if(!input[0].equals("updtPos")) {System.out.println("Client"+ClientNum+" reads: " + inputLine);} //debug
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
					break;
					
				case "feed"://Ŭ�󿡼� ���� feed ��: nickname�� ���̸� �Ծ����� ���� �����ض�
					inputLine = format("feed", nickname, 100 + (int)(Math.random()*500), (int)(Math.random()*500) + 100) ;
					break;
				case "distribute":
					break;
				case "exit":
					isExit = true;
					break;
				default:
					throw new IOException("unknown input command");
				}
				//�ٸ� ��� Ŭ�� �ش� ���� ����
				clntIdx = 0;
				
				for(Socket clnt: MainClass.connectList) {
					BufferedOutputStream tmpBos = new BufferedOutputStream (clnt.getOutputStream());
					tmpOut = new DataOutputStream(tmpBos);
					tmpOut.writeUTF(inputLine);
					tmpOut.flush();
					if(!input[0].equals("updtPos")) {System.out.println("Client"+Integer.toString(ClientNum)+" writes on clnt "+ Integer.toString(clntIdx++) + ": " + inputLine);} //debug
				}
			}while ( !isExit );
			
			System.out.println("Client of exit socket Finally escaped the loop!");
			//������ �����ҵ� �մϴ�..?
			
			MainClass.connectList.remove(socket);
			//â�� �ݱ� �������� ������ �׾ ������ ����. (������ �׾ �ٸ� ������ ������ �����鼭 ������)
			
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
