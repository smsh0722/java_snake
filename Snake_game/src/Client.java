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
			
			//이니셜라이징
			
			//서버의 별도 어레이에 저장하고
			//모든 뱀들의 닉네임+x+y를 계속 뿌려줘야 할까? 무슨 타입으로...? 
			
			do {
				//
				System.out.println("Client waiting to read: "); //debug
				String inputLine = in.readUTF();
				System.out.println("Client reads: " + inputLine); //debug
				String[] input = inputLine.split("/");
				switch(input[0]) {
				case "enter":
					//초기 랜덤위치 생성해 전달
					nickname = input[1];
					input[2] = Double.toString(100 + Math.random() * 400);
					input[3] = Double.toString(Math.random() * 100 + 400);
					inputLine = String.join("/", input); //그지같은 코드 죄송합니다..
					break;
				case "updtPos":
					break;
				case "dead":
					break;
				default:
					throw new IOException("unknown input command");
				}
				//다른 모든 클라에 해당 내용 전송
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
	
	// 입장하고 서버에서 랜덤 스폰 x, y 받기, 내 스네이크 생성: mySnake = new snake( x, y ), 내 맵에 추가: snakes.put( nickname, mySnake );
	// 새로운 유저 입장하면 스네이크 (x, y)와 닉네임 정보 받고, 내 맵에 추가: snakes.put( nickname, new snake(x, y) )
	
	// 나의 스네이크 마우스(x,y) 전송
	// 다른 유저들 스네이크 마우스 받기(x, y), snakes.get( nickname ).move( x, y );
	
	// 서버 유저는 먹이를 추가 시키고, 다른 유저에 뿌리기
	// 먹이 먹으면 서버에 알리기, 이후 서버에서는 맵에 먹이를 지우고 유저들에게 뿌리기, 누가 먹었는지 알리기
	
	// 자신 죽은 것 알림
	// 다른 유저 죽으면 알림 받기
}
