package symlab.ust.hk.imagetagged;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.view.View;


public class FaceDetectionView extends Activity {
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(new MyFaceView(this));
	        
	 }
	    
	
	
	class MyFaceView extends View{
		
		private Bitmap myBitmap;
    	private int width, height;
    	private FaceDetector.Face[] detectedFaces;
    	private int numberOfFaces=4;
    	private FaceDetector faceDetector;
    	private int numberOfFacesDetected;
    	private float eyeDistance;
    	
		public MyFaceView(Context context) 
		{
			super(context);
			/*BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
			myBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.faceswapping,bitmapFatoryOptions);
			width=myBitmap.getWidth();
			height=myBitmap.getHeight();
			detectedFaces=new FaceDetector.Face[NUMBER_OF_FACES];
			faceDetector=new FaceDetector(width,height,NUMBER_OF_FACES);
			numberOfFacesDetected=faceDetector.findFaces(myBitmap, detectedFaces);*/
			
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
			
		}
		
		
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawBitmap(myBitmap, 0,0, null);
			Paint myPaint = new Paint();
	        myPaint.setColor(Color.GREEN);
	        myPaint.setStyle(Paint.Style.STROKE); 
	        myPaint.setStrokeWidth(3);

	        for(int count=0;count<numberOfFacesDetected;count++)
	        {
	        	Face face=detectedFaces[count];
	        	PointF midPoint=new PointF();
	        	face.getMidPoint(midPoint);
	        	
	        	eyeDistance=face.eyesDistance();
	        	canvas.drawRect(midPoint.x-eyeDistance, midPoint.y-eyeDistance, midPoint.x+eyeDistance, midPoint.y+eyeDistance, myPaint);
	        }
		}

		
	}
	
	

}
