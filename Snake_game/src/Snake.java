import java.util.ArrayList;



public class Snake {
	
	SnakeLocationPoint headPoint;
	ArrayList<SnakeLocationPoint> snakeLocationList;
	public boolean isAlive = true;
	double previousX;
	double previousY;
	int bodylen;
	

		Snake(double x, double y) {
			headPoint = new SnakeLocationPoint(x,y); //입력받은 위치를 시작위치로 몸길이 설정

			snakeLocationList.add(headPoint);
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+1, headPoint.y));
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+2, headPoint.y));
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+3, headPoint.y));
		}
		

		public void move(double mouseX, double mouseY, Feed feed) {
			
			double angle = Math.atan2(mouseY-headPoint.y, mouseX-headPoint.x);
			double nextX = Math.cos(Math.toRadians(angle)) * 1;//마우스 방향의 새로운 좌표
			double nextY = Math.sin(Math.toRadians(angle)) * 1;
			
			bodylen = snakeLocationList.size();
			int listlen = snakeLocationList.size() - 1;
			previousX = snakeLocationList.get(listlen).x;
			previousY = snakeLocationList.get(listlen).y;
			
			headPoint = new SnakeLocationPoint(nextX, nextY);
			snakeLocationList.add(0, headPoint);
			
			for(int i = 0; i < feed.feedLocationList.size(); i++) {
				double len = Math.pow(feed.feedLocationList.get(i).x - nextX, 2) + Math.pow(feed.feedLocationList.get(i).y - nextY, 2);
				if(len < 1) {
					bodylen += 2;
					feed.feedLocationList.remove(i);
				}
			}
			
			if(bodylen < listlen + 2) {
				snakeLocationList.remove(listlen);
			}
			
			
			

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
			//자기 몸통에 부딪히면 죽음(head인 경우는 제자리이므로 제외)
			for(int i = 1; i < snakeLocationList.size(); i++) {
				//1보다 작으면 겹침
				double len = Math.pow(snakeLocationList.get(i).x - nx, 2) + Math.pow(snakeLocationList.get(i).y - ny, 2);
				if(len < 1) {
					feed.MakeFeedPlus(nx, ny);
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
