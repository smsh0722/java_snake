import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FeedDistribute implements Runnable {
	DataOutputStream tmpOut;
	int clntIdx;
	FeedDistribute(){
		
	}
	int Delay = 5000;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			
			try {
				Thread.sleep( Delay );// ���ִ� Ÿ�̹�
				Delay += 1000;//�����þ
				String inputLine = "distribute/0/"+ Double.toString(100 + Math.random() * 400) +"/"+ Double.toString(Math.random() * 100 + 400);
				
				clntIdx = 0;
				for(Socket clnt: MainClass.connectList) {
					BufferedOutputStream tmpBos = new BufferedOutputStream (clnt.getOutputStream());
					tmpOut = new DataOutputStream(tmpBos);
					tmpOut.writeUTF(inputLine);
					tmpOut.flush();
				System.out.println("Master host writes on clnt "+ Integer.toString(clntIdx++) + ": " + inputLine); //debug
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
