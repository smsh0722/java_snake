import java.util.ArrayList;

public class Client implements Runnable{
	int userNum;
	GameBoard myGame;
	ArrayList<Integer> userList;
	
	Client( GameBoard myGame ){
		this.myGame = myGame;
	}
	
	public void run() {
		userList = new ArrayList<>();
		int newUser = 0;
		
		// 1) myGame.map update
		
		// 2) 새로운 유저 입장 확인
	}
	
	public int getUserNum() {
		return userNum;
	}
	
	public boolean isEnter() {
		return false;
	}
	
	public int newUser() {
		return 0;
	}
	
}
