import java.util.ArrayList;

public class Client implements Runnable{
	int userNum;
	GameBoard myGame;
	
	Client( GameBoard myGame ){
		this.myGame = myGame;
	}
	
	public void run(){
		
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
