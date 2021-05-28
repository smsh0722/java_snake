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
			double nextX = 1; //마우스 방향의 새로운 좌표
			double nextY = 1;
			double previousX = headPoint.x + len - 1; // 마지막 몸통 위치 저장
			double previousY = headPoint.y + len - 1;
			
			
			headPoint = new SnakeLocationPoint(?, ?);
			
			
			// 대충 마우스 포인터에 맞는 위치로 변경
			
			for(int i = 0; i < snakeLocationList.length(); i++ ) {
				
			}
			if(nextX == feedX && nextY == feedY) {
				snakeLocationList.add(new SnakeLocationPoint(headPoint.x+1, headPoint.y));
			}
			//먹이위치가 같으면  몸통 길이 증가
			
			



			return true;
		}

		public boolean isAlive() {
			//다른 지렁이와 부딪히면 머리끼리 부딪히면 무효
			//다른 지렁이 몸통에 부딪히면 아웃
			//세마리를 인수로 받아서 비교?
			//벽에 부딪히면 아웃
			//자기 몸통에 부딪혀도 아웃
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
