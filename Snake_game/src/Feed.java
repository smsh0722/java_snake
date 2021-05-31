import java.util.ArrayList;
import java.util.Random;


public class Feed {
	ArrayList<FeedLocationPoint> feedLocationList;
	Random rndX = new Random(650);
	Random rndY = new Random(450);
	
	Feed(){
	
	}
	//���� ���� �����Լ�
	public void MakeFeedPoint() {
		feedLocationList.add(new FeedLocationPoint(rndX.nextDouble(), rndY.nextDouble()));
	}
	public void MakeFeedPlus(double x, double y) {
		feedLocationList.add(new FeedLocationPoint(x, y));
	}
	
	
	public class FeedLocationPoint {
	    double x;
	    double y;

	    FeedLocationPoint(double xx, double yy){
	        x = xx;
	        y = yy;
	    }
	}

}

