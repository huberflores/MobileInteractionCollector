package symlab.ust.hk.imagetagged;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.Utilities.Commons;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;


    
public class FaceDetectionTask extends Service  {
	
    	private Bitmap myBitmap; 
    	private int width, height;
    	private FaceDetector.Face[] detectedFaces;
    	private int numberOfFaces=4;
    	private FaceDetector faceDetector;
    	private int numberOfFacesDetected;
    	private String selectedImage;
    	
    	private Logger Log = Logger.getLogger(ImageTagged.class.getName());
    	    	
    	Thread Runningthread = null;
    	
    	private List<String> listOfImages = null;
    	private String root="/";
    
    	
			
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			
			Runningthread = new Thread(new RunningThread());
			Runningthread.start();
	
		    return Service.START_STICKY;
		    
		 };
		 
		 
		 private class RunningThread implements Runnable {

			 @Override
			 public void run() {
				 experimentalDelay(10);
				 Commons.computationalTimeStart = System.currentTimeMillis();
				 startDetection(Commons.appPicturesPath);
				 Commons.computationalTimeEnd = System.currentTimeMillis();
			 }
		  };
		  
		  @Override
		  public IBinder onBind(Intent intent) {
		    return null;
		  }
		
		
		public void startDetection(String appPicturesPath){
			
			getDir(appPicturesPath);
			Random randomGenerator = new Random();
			int pos = randomGenerator.nextInt(listOfImages.size());
		
			selectedImage = listOfImages.get(pos);
			File myFile = new File(selectedImage);
			
			
			BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
			
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
			 intent.putExtra("selectedImage", selectedImage); 
						 
			 sendBroadcast(intent);
			
		  }
		 
		 
		 
		 private void getDir(String dirPath)
		 {
			 
			 listOfImages = new ArrayList<String>();

			 File f = new File(dirPath);
			 File[] files = f.listFiles();

			 for(int i=0; i < files.length; i++)
			 {
				 File file = files[i];
				 String filename = file.getName();
				 String ext = filename.substring(filename.lastIndexOf('.')+1, filename.length());

				  if(ext.equals("JPG")||ext.equals("jpg")||ext.equals("PNG")||ext.equals("png"))
				  {
					listOfImages.add(file.getPath());  
				  }
				   
			 }

		  }
		 
		 @SuppressLint("NewApi")
		private void experimentalDelay(int time){

			 try {
				Random generator = new Random();
				Commons.addedDelay = generator.nextInt(time);
				TimeUnit.SECONDS.sleep(Commons.addedDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		 }
	
}

	
	
	



