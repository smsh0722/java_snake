import java.util.ArrayList;



public class Snake {
	
	SnakeLocationPoint headPoint;
	ArrayList<SnakeLocationPoint> snakeLocationList;
	public boolean isAlive = true;
	double previousX;
	double previousY;
	
	Snake(double x, double y) {
		snakeLocationList = new ArrayList<>();
		headPoint = new SnakeLocationPoint(x,y); //입력받은 위치를 시작위치로 몸길이 설정

		snakeLocationList.add(headPoint);
		for ( int i = 0; i < 10; i ++ ) {
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+i, headPoint.y));
		}

	}
	

	public void move(double mouseX, double mouseY, double feedX, double feedY) {
		headPoint = snakeLocationList.get(0);
		
		double angle = Math.atan2(mouseY-headPoint.y, mouseX-headPoint.x);
        double nextX = headPoint.x + Math.cos(angle) * 1;//마우스 방향의 새로운 좌표
        double nextY = headPoint.y + Math.sin(angle) * 1;
        
        int len = snakeLocationList.size(); //마지막 리스트의 좌표
        
        headPoint = new SnakeLocationPoint(nextX, nextY);
        snakeLocationList.add(0, headPoint);
        snakeLocationList.remove(len);
		
		// test
		for ( int i = 0; i < len; i++ ) System.out.println( snakeLocationList.get(i).x + ", " + snakeLocationList.get(i).y );
	}
	
	public void Collision(Snake snake1, Snake snake2, double feedX, double feedY) {
		double nx = headPoint.x;
		double ny = headPoint.y;
		
		//다른 지렁이에 부딪힘
		for(int i = 0; i < snake1.snakeLocationList.size(); i++) {
			//1보다 작으면 겹침
			double len = Math.pow(snake1.snakeLocationList.get(i).x - nx, 2) + Math.pow(snake1.snakeLocationList.get(i).y - ny, 2);
			if(len < 1) {
				isAlive = false;
			}
		}
		for(int i = 0; i < snake2.snakeLocationList.size(); i++) {
			//1보다 작으면 겹침
			double len = Math.pow(snake2.snakeLocationList.get(i).x - nx, 2) + Math.pow(snake2.snakeLocationList.get(i).y - ny, 2);
			if(len < 1) {
				isAlive = false;
			}
		}
		//자기 몸통에 부딪히면 죽음(head인 경우는 제자리이므로 제외)
		for(int i = 1; i < snakeLocationList.size(); i++) {
			//1보다 작으면 겹침
			double len = Math.pow(snakeLocationList.get(i).x - nx, 2) + Math.pow(snakeLocationList.get(i).y - ny, 2);
			if(len < 1) {
				isAlive = false;
			}
		}
		//board 나가면 죽음
		if(nx < 0 || nx > 700) {
			isAlive = false;
		}
		else if(ny < 0 || ny > 500) {
			isAlive = false;
		}
		//먹이 발견 -> 몸통 추가
		if(nx == feedX && ny == feedY) {
			snakeLocationList.add(new SnakeLocationPoint(previousX, previousY));
		}
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


