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
	int ClientNum;//이 쓰레드가 관리하는 클라이언트의 고유번호
	int clntIdx;//다른 유저한테 정보 뿌릴때 보기좋으라고 있는 변수
	
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
					break;
					
				case "feed"://클라에서 오는 feed 뜻: nickname이 먹이를 먹었으니 새로 생성해라
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
				//다른 모든 클라에 해당 내용 전송
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
			//관전은 가능할듯 합니다..?
			
			MainClass.connectList.remove(socket);
			//창을 닫기 전까지는 유저가 죽어도 리무브 안함. (유저는 죽어도 다른 유저의 데이터 받으면서 관전함)
			
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
