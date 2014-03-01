package symlab.ust.hk.imagetagged;


import java.util.logging.Logger;
import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.support.v4.view.GestureDetectorCompat;


public class TasksActivity extends Activity implements android.view.View.OnClickListener, GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener{
	
	private Logger Log = Logger.getLogger(TasksActivity.class.getName());
	private Uri dbUri;
	private DatabaseManager dManager;
	
	private double press = 0f;
	private double release = 0f;
	
	private GestureDetectorCompat mDetector;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState); 
	        setContentView(R.layout.activity_tasks_list);
	        
	        taskButtons();
	        
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
	 
	 
    	@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
				case R.id.btn_task1: 
				
					 dManager.saveData("Button \"Task 1\"", "Press/Release event", press, release);
					 Intent intent1 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent1.putExtra("taskName", "Task 1");
			         startActivity(intent1);
					 
					 
					break;		
					
				case R.id.btn_task2: 
					
					 dManager.saveData("Button \"Task 2\"", "Press/Release event", press, release);
			        
					break;
					
				case R.id.btn_task3: 
					
					 dManager.saveData("Button \"Task 3\"", "Press/Release event", press, release);
			        
					break;
					
				case R.id.btn_task4: 
					
					 dManager.saveData("Button \"Task 4\"", "Press/Release event", press, release);
			        
					break;
					
				case R.id.btn_task5: 
					
					 dManager.saveData("Button \"Task 5\"", "Press/Release event", press, release);
			        
					break;
					
				case R.id.btn_task6: 
					
					 dManager.saveData("Button \"Task 6\"", "Press/Release event", press, release);
			        
					break;
					
				case R.id.btn_task7: 
					
					 dManager.saveData("Button \"Task 7\"", "Press/Release event", press, release);
			        
					break;
				
				case R.id.btn_task8: 
					
					 dManager.saveData("Button \"Task 8\"", "Press/Release event", press, release);
			        
					break;
					
				case R.id.btn_task9: 
					
					 dManager.saveData("Button \"Task 9\"", "Press/Release event", press, release);
			        
					break;
					
				case R.id.btn_task10: 
					
					 dManager.saveData("Button \"Task 10\"", "Press/Release event", press, release);
			        
					break;
			}		
			
		}
	 
	 
	 	public void taskButtons(){
	 		Button btn_task1 = (Button) findViewById(R.id.btn_task1);
	        btn_task1.setOnClickListener(this);
	        btn_task1.setOnTouchListener(btnTouchTask1);
	        
	        Button btn_task2 = (Button) findViewById(R.id.btn_task2);
	        btn_task2.setOnClickListener(this);
	        btn_task2.setOnTouchListener(btnTouchTask2);
	        btn_task2.setEnabled(false);
	        
	        Button btn_task3 = (Button) findViewById(R.id.btn_task3);
	        btn_task3.setOnClickListener(this);
	        btn_task3.setOnTouchListener(btnTouchTask3);
	        btn_task3.setEnabled(false);
	        
	        Button btn_task4 = (Button) findViewById(R.id.btn_task4);
	        btn_task4.setOnClickListener(this);
	        btn_task4.setOnTouchListener(btnTouchTask4);
	        btn_task4.setEnabled(false);
	        
	        Button btn_task5 = (Button) findViewById(R.id.btn_task5);
	        btn_task5.setOnClickListener(this);
	        btn_task5.setOnTouchListener(btnTouchTask5);
	        btn_task5.setEnabled(false);
	        
	        Button btn_task6 = (Button) findViewById(R.id.btn_task6);
	        btn_task6.setOnClickListener(this);
	        btn_task6.setOnTouchListener(btnTouchTask6);
	        btn_task6.setEnabled(false);
	        
	        Button btn_task7 = (Button) findViewById(R.id.btn_task7);
	        btn_task7.setOnClickListener(this);
	        btn_task7.setOnTouchListener(btnTouchTask7);
	        btn_task7.setEnabled(false);
	        
	        Button btn_task8 = (Button) findViewById(R.id.btn_task8);
	        btn_task8.setOnClickListener(this);
	        btn_task8.setOnTouchListener(btnTouchTask8);
	        btn_task8.setEnabled(false);
	        
	        Button btn_task9 = (Button) findViewById(R.id.btn_task9);
	        btn_task9.setOnClickListener(this);
	        btn_task9.setOnTouchListener(btnTouchTask9);
	        btn_task9.setEnabled(false);
	        
	        Button btn_task10 = (Button) findViewById(R.id.btn_task10);
	        btn_task10.setOnClickListener(this);
	        btn_task10.setOnTouchListener(btnTouchTask10);
	        btn_task10.setEnabled(false);
	        
	        
	 	}
	 	
	 	private View.OnTouchListener btnTouchTask1 = new View.OnTouchListener() {
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
	 	
		 
		 private View.OnTouchListener btnTouchTask2 = new View.OnTouchListener() {
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
		 
		 
		 private View.OnTouchListener btnTouchTask3 = new View.OnTouchListener() {
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
		 
		 private View.OnTouchListener btnTouchTask4 = new View.OnTouchListener() {
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
		  
		  private View.OnTouchListener btnTouchTask5 = new View.OnTouchListener() {
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
		  
		  private View.OnTouchListener btnTouchTask6 = new View.OnTouchListener() {
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
		  
		  private View.OnTouchListener btnTouchTask7 = new View.OnTouchListener() {
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
		  
		  private View.OnTouchListener btnTouchTask8 = new View.OnTouchListener() {
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
		  
		  private View.OnTouchListener btnTouchTask9 = new View.OnTouchListener() {
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
		  
		  private View.OnTouchListener btnTouchTask10 = new View.OnTouchListener() {
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
