import java.util.ArrayList;
import java.util.Random;


public class Feed {
	ArrayList<FeedLocationPoint> feedLocationList;
	
	Feed(){
		feedLocationList = new ArrayList<>();
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

