import java.util.ArrayList;

import javax.swing.JFrame;



public class Snake {
	
	SnakeLocationPoint headPoint;
	ArrayList<SnakeLocationPoint> snakeLocationList;
	public boolean isAlive = true;
	double previousX;
	double previousY;
	int bodylen;
	
	Snake(double x, double y) {
		snakeLocationList = new ArrayList<>();
		headPoint = new SnakeLocationPoint(x,y); //�Է¹��� ��ġ�� ������ġ�� ������ ����

		snakeLocationList.add(headPoint);
		for ( int i = 0; i < 10; i ++ ) {
			snakeLocationList.add(new SnakeLocationPoint(headPoint.x+i, headPoint.y));
		}
		bodylen = 11; // �⺻ 11 �� ����
	}
	
	public void move(double mouseX, double mouseY/*, Feed feed */) {
		headPoint = snakeLocationList.get(0);
		
		double angle = Math.atan2(mouseY-headPoint.y, mouseX-headPoint.x);
        double nextX = headPoint.x + Math.cos(angle) * 1.5;//���콺 ������ ���ο� ��ǥ
        double nextY = headPoint.y + Math.sin(angle) * 1.5;
        
		int listlen = snakeLocationList.size();
		
        headPoint = new SnakeLocationPoint(nextX, nextY);
        snakeLocationList.add(0, headPoint);
        
        if ( bodylen <= listlen ) snakeLocationList.remove(listlen);
	}
	
	public void Collision(Snake snake1) {
		SnakeLocationPoint hp = snakeLocationList.get(0);
		double nx = hp.x;
		double ny = hp.y;
		
		//�ٸ� �����̿� �ε���
		for(int i = 0; i < snake1.snakeLocationList.size(); i++) {
			//1���� ������ ��ħ
			double len = Math.pow(snake1.snakeLocationList.get(i).x - nx, 2) + Math.pow(snake1.snakeLocationList.get(i).y - ny, 2);
			if(len < 144) {
				isAlive = false;
			}
		}
		
	}
	public void BoardOut() {
		if(headPoint.x <= 0 || headPoint.x >= JFrame.MAXIMIZED_HORIZ) {
			isAlive = false;
		}
		else if(headPoint.y <= 0 || headPoint.y >= JFrame.MAXIMIZED_VERT) {
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

