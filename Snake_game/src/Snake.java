import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;




public class Snake {
	
	SnakeLocationPoint headPoint;
	ArrayList<SnakeLocationPoint> snakeLocationList;
	public boolean isAlive = true;
	double previousX;
	double previousY;
	int bodylen;
	int getfeed;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
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
	public boolean CollisionFeed(Feed feed) {
		for(int i = 0; i < feed.feedLocationList.size(); i++) {
			double len = Math.pow(feed.feedLocationList.get(i).x - headPoint.x, 2) + Math.pow(feed.feedLocationList.get(i).y - headPoint.y, 2);
			if(len < 144) {
				getfeed = i;
				return true;
			}
		}
		return false;	
	}
	
	public void RemoveCollisionFeed(Feed feed) {
		for(int i = 0; i < feed.feedLocationList.size(); i++) {
			double len = Math.pow(feed.feedLocationList.get(i).x - headPoint.x, 2) + Math.pow(feed.feedLocationList.get(i).y - headPoint.y, 2);
			if(len < 144) {
				getfeed = i;
				bodylen += 2;
				feed.feedLocationList.remove(getfeed);
				if(isAlive == false) {
					feed.MakeFeedPlus(headPoint.x, headPoint.y);
				}
				return;
			}
		}
	}
	
	public void CollisionSnake(Snake snake1) {
		double nx = headPoint.x;
		double ny = headPoint.y;
		
		//다른 지렁이에 부딪힘
		for(int i = 0; i < snake1.snakeLocationList.size(); i++) {
			//1보다 작으면 겹침
			double len = Math.pow(snake1.snakeLocationList.get(i).x - nx, 2) + Math.pow(snake1.snakeLocationList.get(i).y - ny, 2);
			if(len < 144) {
				isAlive = false;
			}
		}
		
	}
	public void BoardOut() {
		SnakeLocationPoint hp = snakeLocationList.get(0);
		if(hp.x <= 0 || hp.x >= d.width) {
			isAlive = false;
		}
		else if(hp.y <= 0 || hp.y >= d.height) {
      isAlive = false;
		
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

