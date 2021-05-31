import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Communicate implements Runnable {
	GameBoard myGame;
	DataOutputStream toServer;
	DataInputStream fromServer;
	Socket socket;
	
	Communicate( GameBoard myGame, Socket socket ){
		this.myGame = myGame;
		this.socket = socket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			BufferedInputStream bis = new BufferedInputStream (socket.getInputStream());
			fromServer = new DataInputStream(bis);
			BufferedOutputStream bos = new BufferedOutputStream (socket.getOutputStream());
			toServer = new DataOutputStream(bos);
			
			while(!myGame.isExit) {
				//�������� �޾ƿ� ������ ó��
				String inputLine = fromServer.readUTF();
				String[] input = inputLine.split("/");
				if(!input[0].equals("updtPos")) {System.out.println("GameTask(Communicate) reads: " + inputLine +"\n="+input[0]+"=");} //debug
				
				double x = Double.parseDouble(input[2]);
				double y = Double.parseDouble(input[3]);
				
				switch(input[0]) {
				case "enter":
					if (input[1].equals(myGame.nickname)) {
						System.out.println("mySnake initialized.");
						myGame.mySnake = new Snake( x, y );
						myGame.snakes.put(myGame.nickname, myGame.mySnake);
					} //my Snake
					else {
						myGame.snakes.put( input[1], new Snake(x, y) );
						System.out.println("Snake "+input[1]+" added on the board.");
					} //other snake entered
					break;
					
				case "updtPos":
					Snake tmpSnk = myGame.snakes.get( input[1] );
					if(tmpSnk!=null) {
						tmpSnk.move( x, y );
					}
					break;
					
				case "dead":
					myGame.snakes.remove( input[1] );
					System.out.println(input[1]+" Died.");
					//nickname�� ������ġ�� ���̻���
					myGame.feed.MakeFeedPlus(x, y);
					break;
					
				case "feed"://�������� ���� ���� ��ġ�� ���� ����
					//input[1]�� ��������. nickname = input[1];
					myGame.snakes.get(input[1]).RemoveCollisionFeed(myGame.feed);
					/*
					 * 		���� ���� ������ �ٸ� ���� ��� ���� �޽����� ���޹���.
					 * 		���̸����� = myGame.snakes.get(nickname); 	//�ش� �г����� �� ����
					 * 		���̸�����.RemoveCollidedFeed(feed);		//�� ���� �浹�� ���̸� �ٽ� ã�Ƽ� ���̴� ���ְ� ���� ���� �ø�.
					 * 
					 * */
					myGame.feed.MakeFeedPlus(x, y);
					break;
				case "distribute":
					myGame.feed.MakeFeedPlus(x, y);
					break;
					
				case "exit":
					break;
				default:
					throw new IOException("unknown input command");
				}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
