package symlab.ust.hk.imagetagged;

import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProcessTask extends Activity implements android.view.View.OnClickListener, GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener {
	
	 private Uri dbUri; 
	 private double press = 0f;
	 private double release = 0f;
	 
	 private GestureDetectorCompat mDetector;
	    
	 private DatabaseManager dManager;

	 private Button taskState;
	 private TextView taskName;
	 private TextView taskStatus;
	 
	 public static String taskStatusText = "Processing...";
	 private boolean buttonAction;
	 MessageReceiver receiver;
	 
	 private String selectedImage; 
		 
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) { 
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_process_task);
	      Commons.currentTask = "Processing Task " + Commons.counterTask;
	      Bundle extras = getIntent().getExtras();
	     
	      dbUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
		  	    	.getParcelable(MyTaskContentProvider.CONTENT_ITEM_TYPE);

	      buttonAction = false;
	       
	      if (extras!=null){
	    	  initialLabels(extras.getString("taskName"));
	    	  runTask();
	    	  
	    	  boolean iscloud = extras.getBoolean("taskValue", false);
		      if(iscloud){
		         selectedImage = extras.getString("selectedImage");
		         buttonAction = true;
		      }
	    	  
	      }
	      	
	  	  dManager = new DatabaseManager(this);
	  	  dManager.setDbUri(dbUri);

	  	  mDetector = new GestureDetectorCompat(this,this);
	  	  mDetector.setOnDoubleTapListener(this);
	      
	 }      
	 
	 
	 public void runTask(){
		 startService(new Intent(this, FaceDetectionTask.class));
	 }
	 
	 @Override
		public void onClick(View v) {
			switch (v.getId()) { 
			
				case R.id.taskButton: 
			
					
					if (buttonAction==false){
						//Go back
						dManager.saveData("Button \"GoBack!\"", "Press/Release event", press, release);
						Intent listOfTasks= new Intent(ProcessTask.this, TasksActivity.class);
					    listOfTasks.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					    startActivity(listOfTasks);
						
					}else{
						//Go results						
						dManager.saveData("Button \"GetResults\"", "Press/Release event", press, release);
						Intent intent = new Intent(getApplicationContext(), FaceDetectionView.class);
						intent.putExtra("selectedImage", selectedImage);
				        startActivity(intent);
				        finish();

					}
				  
					break;		
			}	
		}
	 
	 public void initialLabels(String t){
		  taskState = (Button) findViewById(R.id.taskButton);
		  taskState.setText("Go Back and check later!");
		  taskState.setOnClickListener(this);
		  taskState.setOnTouchListener(btnTouchState);
		  
	      taskName = (TextView) findViewById(R.id.taskName);
	      taskName.setText(t);
	      
	      taskStatus = (TextView) findViewById(R.id.taskStatus);
	      taskStatus.setText(taskStatusText);
	      
	 }
	 
	 private View.OnTouchListener btnTouchState = new View.OnTouchListener() {
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
	 
	 
	   private void changeTaskStatus(String status){
	     taskStatus.setText(status);
	     taskState.setText("Get Results!");
	     buttonAction = true;
	    }
	 
	   public class MessageReceiver extends BroadcastReceiver {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        
	         boolean iscloud = intent.getBooleanExtra("taskValue", false);
	         if(iscloud){
	         String status = intent.getStringExtra("taskStatusValue");
	         selectedImage = intent.getStringExtra("selectedImage");
	         		changeTaskStatus(status);
	         }
	         
	         try {
	                Uri notification = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION);
	                android.media.Ringtone r = android.media.RingtoneManager.getRingtone(getApplicationContext(), notification);
	                r.play();
	            } catch (Exception e) {}
	        }
	      }
	 	  
	   
	   @Override
	   protected void onNewIntent(Intent intent) {
	       super.onNewIntent(intent);
	       setIntent(intent);
	   }
	   
	   @Override
	   public void onResume() {
		    Commons.currentTask = "Processing Task " + Commons.counterTask;
	        IntentFilter filter;
	        filter = new IntentFilter(ProcessTask.taskStatusText);
	        receiver = new MessageReceiver();
	        registerReceiver(receiver, filter);
	        
	        Intent extras= getIntent();
		      
		      if (extras!=null){
		    	  boolean iscloud = extras.getBooleanExtra("taskValue", false);
			      if(iscloud){
			         selectedImage = extras.getStringExtra("selectedImage"); 
			         changeTaskStatus("Completed");
			         buttonAction = true;
			      } 
		      } 
	        
	        
	        super.onResume();
	
	   
	   } 

      @Override
      public void onPause() {
    	  unregisterReceiver(receiver);
	      super.onPause();
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
			
				    
			    
}
