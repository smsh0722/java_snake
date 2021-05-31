import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accept implements Runnable {
	GameBoard myGame;
	int port;
	
	Accept(GameBoard myGame, int port){
		this.myGame = myGame;
		this.port = port;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread( new FeedDistribute() ).start();//밥주는 기계 가동시작
		
		int userNum = 0;
		
		while(true) {
			try {
				Socket connect = serverSocket.accept();
				MainClass.connectList.add(connect);
				new Thread( new Client( myGame, connect, userNum ) ).start();
				System.out.println( "new User "+Integer.toString(userNum)+" connected." );
				userNum++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
