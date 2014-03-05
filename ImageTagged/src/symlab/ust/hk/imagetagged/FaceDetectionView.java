package symlab.ust.hk.imagetagged;

import java.io.File;
import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.DatabaseManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class FaceDetectionView extends Activity implements android.view.View.OnClickListener, GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener{
	
	/** Called when the activity is first created. */
	
	static Logger Log = Logger.getLogger(FaceDetectionView.class.getName());

    private static String selectedImage;
    
    private Uri dbUri;
	private DatabaseManager dManager;
	
	private double press = 0f;
	private double release = 0f;
	
	private GestureDetectorCompat mDetector;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(R.layout.activity_picture_viewer);
        
        Bundle extras = getIntent().getExtras();
        selectedImage = extras.getString("selectedImage");
        
        dbUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
    	    	.getParcelable(MyTaskContentProvider.CONTENT_ITEM_TYPE);

        
        MyView imageView = (MyView) findViewById(R.id.image_view);

        
        Button btn_finish = (Button) findViewById(R.id.finish_task);
        btn_finish.setOnClickListener(this);
        btn_finish.setOnTouchListener(btnTouchFinish);
        
        dManager = new DatabaseManager(this);
    	dManager.setDbUri(dbUri);
    	

    	mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        
        
        
    }
    
    
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {   
		
			case R.id.finish_task: 
			
				 dManager.saveData("Button - FinishTask", "Press/Release event", press, release);
				 
				 dManager.saveData(Commons.computationalTimeStart, Commons.computationalTimeEnd, Commons.addedDelay);
				 
		         Intent listOfTasks= new Intent(getApplicationContext(), TasksActivity.class);
				 listOfTasks.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				 startActivity(listOfTasks);
				 finish();
				 Commons.activateQoE = true;
		   
				 
				break;	
				
			
		}		
		
	}
    
    private View.OnTouchListener btnTouchFinish = new View.OnTouchListener() {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
	         int action = event.getAction();
	         if (action == MotionEvent.ACTION_DOWN)
	       	   press = System.currentTimeMillis();
	         else if (action == MotionEvent.ACTION_UP)
	           release = System.currentTimeMillis();
	         return false;   
	      }
	 };
 	
    
    public static class MyView extends ImageView
    {
    	private Bitmap myBitmap;
    	private int width, height;
    	private FaceDetector.Face[] detectedFaces;
    	private int NUMBER_OF_FACES=4;
    	private FaceDetector faceDetector;
    	private int NUMBER_OF_FACE_DETECTED;
    	private float eyeDistance;
    	
    	public MyView(Context context) 
    	{
    		super(context);
    		
    	}
    	
    	public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }
    	
    	@Override
    	protected void onDraw(Canvas canvas) 
    	{
    		//Log.info("Here we are");
			File myFile = new File(selectedImage); 
			BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
			
			myBitmap = BitmapFactory.decodeFile(myFile.getAbsolutePath(), bitmapFatoryOptions);
 
    		width=myBitmap.getWidth();
    		height=myBitmap.getHeight();
    		detectedFaces=new FaceDetector.Face[NUMBER_OF_FACES];
    		faceDetector=new FaceDetector(width,height,NUMBER_OF_FACES);
    		NUMBER_OF_FACE_DETECTED=faceDetector.findFaces(myBitmap, detectedFaces);
    		
    		canvas.drawBitmap(myBitmap, 0,0, null);
    		Paint myPaint = new Paint();
            myPaint.setColor(Color.GREEN);
            myPaint.setStyle(Paint.Style.STROKE); 
            myPaint.setStrokeWidth(3);

            for(int count=0;count<NUMBER_OF_FACE_DETECTED;count++)
            {
            	Face face=detectedFaces[count];
            	PointF midPoint=new PointF();
            	face.getMidPoint(midPoint);
            	
            	eyeDistance=face.eyesDistance();
            	canvas.drawRect(midPoint.x-eyeDistance, midPoint.y-eyeDistance, midPoint.x+eyeDistance, midPoint.y+eyeDistance, myPaint);
            }
    	}
    	
    }
    
    @Override
	public void onBackPressed() {
	}
    
    //Gesture events
 		@Override 
 		public boolean onTouchEvent(MotionEvent event){ 
 		     this.mDetector.onTouchEvent(event);
 		     // Be sure to call the superclass implementation
 		   return super.onTouchEvent(event);
 		}

 		@Override
 		public boolean onDown(MotionEvent event) {
 			//Log.info("onDown: " + event.toString());
 			dManager.saveData("onDown",System.currentTimeMillis());
 			
 		  return true;
 		}

 		@Override
 		public boolean onFling(MotionEvent event1, MotionEvent event2, 
 		    float velocityX, float velocityY) {
 		  	//Log.info("onFling: " + event1.toString()+event2.toString());
 		  	dManager.saveData("onFling",System.currentTimeMillis());
 		  return true;
 		}

 		@Override
 		public void onLongPress(MotionEvent event) {
 		  	//Log.info("onLongPress: " + event.toString());
 		  	dManager.saveData("onLongPress",System.currentTimeMillis());
 		}

 		@Override
 		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
 		            float distanceY) {
 		   	//Log.info("onScroll: " + e1.toString()+e2.toString());
 		   	dManager.saveData("onScroll",System.currentTimeMillis());     
 		 return true;
 		}

 		@Override
 		public void onShowPress(MotionEvent event) {
 		  	//Log.info("onShowPress: " + event.toString());
 		  	dManager.saveData("onPressNoMovement",System.currentTimeMillis());    
 		}

 		@Override
 		public boolean onSingleTapUp(MotionEvent event) {
 		   	//Log.info("onSingleTapUp: " + event.toString());
 		   	dManager.saveData("onUp",System.currentTimeMillis());    
 		 return true;
 		}

 		@Override
 		public boolean onDoubleTap(MotionEvent event) {
 		  	//Log.info("onDoubleTap: " + event.toString());
 		  	dManager.saveData("onDoubleTap" ,System.currentTimeMillis());
 		 return true;
 		}

 		@Override
 		public boolean onDoubleTapEvent(MotionEvent event) {
 		   	//Log.info("onDoubleTapEvent: " + event.toString());
 		     
 		 return true;
 		}

 		@Override
 		public boolean onSingleTapConfirmed(MotionEvent event) {
 		   	//Log.info("onSingleTapConfirmed: " + event.toString());
 		   	
 		 return true;
 		} 

 	 
 		//Lifecycle activity management
 		@Override
 		public void onStart(){
 			super.onStart();
 			Commons.currentTask = "Face detection result";
 		}
 		
 		@Override
 		public void onRestart(){
 			super.onRestart();
 			Commons.currentTask = "Face detection result";
 		}


}
