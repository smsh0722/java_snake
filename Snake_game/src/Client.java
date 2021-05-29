import java.util.ArrayList;

public class Client implements Runnable{
	int userNum;
	GameBoard myGame;
	
	Client( GameBoard myGame ){
		this.myGame = myGame;
	}
	
	public void run(){
		
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
