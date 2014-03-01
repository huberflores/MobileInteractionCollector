package symlab.ust.hk.imagetagged;

import java.io.File;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.os.IBinder;


    
public class FaceDetectionTask extends Service {
	
    	private Bitmap myBitmap; 
    	private int width, height;
    	private FaceDetector.Face[] detectedFaces;
    	private int numberOfFaces=4;
    	private FaceDetector faceDetector;
    	private int numberOfFacesDetected;
    	
      	
    	Thread Runningthread = null;
    	
			
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
		  		      
				Runningthread = new Thread(new RunningThread());
				Runningthread.start();
				
		    return Service.START_STICKY;
		 };
		 
		 
		 private class RunningThread implements Runnable {

			 @Override
			 public void run() {
				 startDetection();
			 }
		  };
		  
		  @Override
		  public IBinder onBind(Intent intent) {
		    return null;
		  }
		
		
		public void startDetection(){
			File myFile = new File("/sdcard/1393614298995.jpg");
			
			
			BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
			//myBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.faceswapping,bitmapFatoryOptions);
			
			myBitmap = BitmapFactory.decodeFile(myFile.getAbsolutePath(), bitmapFatoryOptions);
			width=myBitmap.getWidth();
			height=myBitmap.getHeight();
			detectedFaces=new FaceDetector.Face[numberOfFaces];
			faceDetector=new FaceDetector(width,height,numberOfFaces);
			numberOfFacesDetected=faceDetector.findFaces(myBitmap, detectedFaces);
			           
			notifyStatusChanged("Completed");
		}
		


		 public void notifyStatusChanged(String status){
			 Intent intent = new Intent(ProcessTask.taskStatusText);
			 intent.putExtra("taskValue", true);
			 intent.putExtra("taskStatusValue", status);

			 sendBroadcast(intent);
		  }
	
}

	
	
	



