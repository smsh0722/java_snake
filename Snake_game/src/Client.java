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
	boolean isAlive = true;//myGame.snake.isAlive는 뱀이 죽어 없어지면 참조 불가하므로 로컬변수 마련
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
					//초기 랜덤위치 생성해 전달
					nickname = input[1];
					inputLine = format("enter", nickname, 100 + Math.random() * 400, Math.random() * 100 + 400);
					
					//iterator로 myGame.snakes(호스트의 snakes)를 돌면서 닉넴/위치값 전부 enter로 socket (새유저)에 전송
					Iterator<String> keys = myGame.snakes.keySet().iterator();
			        while( keys.hasNext() ){
			            String key = keys.next();
			            Snake tmpSnk = myGame.snakes.get(key);
			            System.out.println( String.format("키 : %s, 값 : %s", key, tmpSnk) );
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
				//다른 모든 클라에 해당 내용 전송
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
			//관전은 불가능할듯 합니다.....
			if(nickname!=myGame.nickname) {
				MainClass.connectList.remove(socket);
			}//호스트는 죽어도 리무브 안함. (새 유저가 받는 snakes 해시맵은 호스트의 것을 사용하므로 호스트는 죽어도 다른 유저의 입장/죽음을 받아야 됨.)
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String format(String status, String nickname, double x, double y) {
		return status + "/" + nickname + "/" + Double.toString(x) + "/" + Double.toString(y);
	}
	// 입장하고 서버에서 랜덤 스폰 x, y 받기, 내 스네이크 생성: mySnake = new snake( x, y ), 내 맵에 추가: snakes.put( nickname, mySnake );
	// 새로운 유저 입장하면 스네이크 (x, y)와 닉네임 정보 받고, 내 맵에 추가: snakes.put( nickname, new snake(x, y) )
	
	// 나의 스네이크 마우스(x,y) 전송
	// 다른 유저들 스네이크 마우스 받기(x, y), snakes.get( nickname ).move( x, y );
	
	// 서버 유저는 먹이를 추가 시키고, 다른 유저에 뿌리기
	// 먹이 먹으면 서버에 알리기, 이후 서버에서는 맵에 먹이를 지우고 유저들에게 뿌리기, 누가 먹었는지 알리기
	
	// 자신 죽은 것 알림
	// 다른 유저 죽으면 알림 받기
}
