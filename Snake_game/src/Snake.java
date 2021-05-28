import java.util.ArrayList;
public class Snake {
	SnakeLocationPoint headPoint;

	ArrayList<SnakeLocationPoint> snakeLocationList;
	
 
		

		Snake(double x, double y){
			headPoint = new SnakeLocationPoint(x,y);

			snakeLocationList.add(headPoint);
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+1, headPoint.y));
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+2, headPoint.y));
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+3, headPoint.y));
		}

		public boolean moveWhenClicked(double mouseX, double mouseY) {
			double angle = Math.atan2(mouseY-headPoint.y, mouseX-headPoint.x);
			int len = snakeLocationList.length();
			double nextX = 1; //���콺 ������ ���ο� ��ǥ
			double nextY = 1;
			double previousX = headPoint.x + len - 1; // ������ ���� ��ġ ����
			double previousY = headPoint.y + len - 1;
			
			
			headPoint = new SnakeLocationPoint(?, ?);
			
			
			// ���� ���콺 �����Ϳ� �´� ��ġ�� ����
			
			for(int i = 0; i < snakeLocationList.length(); i++ ) {
				
			}
			if(nextX == feedX && nextY == feedY) {
				snakeLocationList.add(new SnakeLocationPoint(headPoint.x+1, headPoint.y));
			}
			//������ġ�� ������  ���� ���� ����
			
			



			return true;
		}

		public boolean isAlive() {
			//�ٸ� �����̿� �ε����� �Ӹ����� �ε����� ��ȿ
			//�ٸ� ������ ���뿡 �ε����� �ƿ�
			//�������� �μ��� �޾Ƽ� ��?
			//���� �ε����� �ƿ�
			//�ڱ� ���뿡 �ε����� �ƿ�
			return true;
		}
		
		public class SnakeLocationPoint {
		    double x;
		    double y;

		    SnakeLocationPoint(double xx, double yy){
		        x = xx;
		        y = yy;
		    }

		    public double getX() {
		        return x;
		    }

		    public void setX(double x) {
		        this.x = x;
		    }

		    public double getY() {
		        return y;
		    }

		    public void setY(double y) {
		        this.y = y;
		    }
		}

}
