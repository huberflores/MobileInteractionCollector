package symlab.ust.hk.imagetagged;


import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import android.support.v4.view.GestureDetectorCompat;



public class TasksActivity extends Activity implements GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener{
	
	private Logger Log = Logger.getLogger(TasksActivity.class.getName());
	private Uri dbUri;
	private DatabaseManager dManager;
	
	private GestureDetectorCompat mDetector;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState); 
	        setContentView(R.layout.activity_tasks_list);
	        
	    	Bundle extras = getIntent().getExtras();

	    	dbUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
	    	.getParcelable(MyTaskContentProvider.CONTENT_ITEM_TYPE);


	    	if (extras != null) {
	    	dbUri = extras
	    	.getParcelable(MyTaskContentProvider.CONTENT_ITEM_TYPE);

	    	}
	    	
	    	dManager = new DatabaseManager(this);
	    	dManager.setDbUri(dbUri);
	    	

	    	mDetector = new GestureDetectorCompat(this,this);
	        mDetector.setOnDoubleTapListener(this);
	        
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
			Commons.currentTask = "List of tasks";
		}
		
		@Override
		public void onRestart(){
			super.onRestart();
			Commons.currentTask = "List of tasks";
		}
		
	 
}
