import java.util.ArrayList;



public class Snake {
	
	SnakeLocationPoint headPoint;
	ArrayList<SnakeLocationPoint> snakeLocationList;
	public boolean isAlive = true;
	double previousX;
	double previousY;
	int bodylen;
	
	Snake(double x, double y) {
		snakeLocationList = new ArrayList<>();
		headPoint = new SnakeLocationPoint(x,y); //입력받은 위치를 시작위치로 몸길이 설정

		snakeLocationList.add(headPoint);
		for ( int i = 0; i < 10; i ++ ) {
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+i, headPoint.y));
		}
		bodylen = 11; // 기본 11 로 시작
	}
	
	public void move(double mouseX, double mouseY/*, Feed feed */) {
		headPoint = snakeLocationList.get(0);
		
		double angle = Math.atan2(mouseY-headPoint.y, mouseX-headPoint.x);
        double nextX = headPoint.x + Math.cos(angle) * 1.5;//마우스 방향의 새로운 좌표
        double nextY = headPoint.y + Math.sin(angle) * 1.5;
        
		int listlen = snakeLocationList.size();
		
        headPoint = new SnakeLocationPoint(nextX, nextY);
        snakeLocationList.add(0, headPoint);
        
        if ( bodylen <= listlen ) snakeLocationList.remove(listlen);
        /*
		for(int i = 0; i < feed.feedLocationList.size(); i++) {
			double len = Math.pow(feed.feedLocationList.get(i).x - nextX, 2) + Math.pow(feed.feedLocationList.get(i).y - nextY, 2);
			if(len < 1) {
				bodylen += 2;
				feed.feedLocationList.remove(i);
			}
		}
		*/
		// test
		// for ( int i = 0; i < listlen; i++ ) System.out.println( snakeLocationList.get(i).x + ", " + snakeLocationList.get(i).y );
	}
	
	public void Collision(Snake snake1, Snake snake2, Feed feed) {
		double nx = headPoint.x;
		double ny = headPoint.y;
		
		//다른 지렁이에 부딪힘
		for(int i = 0; i < snake1.snakeLocationList.size(); i++) {
			//1보다 작으면 겹침
			double len = Math.pow(snake1.snakeLocationList.get(i).x - nx, 2) + Math.pow(snake1.snakeLocationList.get(i).y - ny, 2);
			if(len < 1) {
				feed.MakeFeedPlus(nx, ny);
				isAlive = false;
				
			}
		}
		for(int i = 0; i < snake2.snakeLocationList.size(); i++) {
			//1보다 작으면 겹침
			double len = Math.pow(snake2.snakeLocationList.get(i).x - nx, 2) + Math.pow(snake2.snakeLocationList.get(i).y - ny, 2);
			if(len < 1) {
				feed.MakeFeedPlus(nx, ny);
				isAlive = false;
			}
		}
		/*
		//자기 몸통에 부딪히면 죽음(head인 경우는 제자리이므로 제외)
		for(int i = 1; i < snakeLocationList.size(); i++) {
			//1보다 작으면 겹침
			double len = Math.pow(snakeLocationList.get(i).x - nx, 2) + Math.pow(snakeLocationList.get(i).y - ny, 2);
			if(len < 1) {
				feed.MakeFeedPlus(nx, ny);
				isAlive = false;
			}
		}*/
		//board 나가면 죽음
		if(nx < 0 || nx > 700) {
			isAlive = false;
		}
		else if(ny < 0 || ny > 500) {
			isAlive = false;
		}
		//먹이 발견 -> 몸통 추가
		/*for(int i = 0; i < feed.feedLocationList.size(); i++) {
			double len = Math.pow(feed.feedLocationList.get(i).x - nx, 2) + Math.pow(feed.feedLocationList.get(i).y - ny, 2);
			if(len < 1) {
				snakeLocationList.add(new SnakeLocationPoint(previousX, previousY));
				feed.feedLocationList.remove(i);
			}
		}
		*/
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
