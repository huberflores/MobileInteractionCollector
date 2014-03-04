package symlab.ust.hk.imagetagged;


import java.io.IOException;
import java.util.logging.Logger;
import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.ProcessTask.MessageReceiver;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.Utilities.DatabaseCommons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
	
	public static String statusFinished = "Finished";
	public static String statusProcessing = "Processing...";
	MessageReceiver receiver;
	
	private boolean isActive = false;
	private String selectedImage; 
	
	Button btn_task1;
	Button btn_task2;  
	Button btn_task3;
	Button btn_task4; 
	Button btn_task5;
	Button btn_task6;
	Button btn_task7;
	Button btn_task8;
	Button btn_task9;
	Button btn_task10;
	Button btn_QoERating;
	Button btn_extractDB;

	
	
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
					 intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 intent1.putExtra("taskName", "Task 1");
					 if (isActive==true){
						 intent1.putExtra("selectedImage", selectedImage);
						 intent1.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 startActivity(intent1);
	
					 
					 
					break;		
					
				case R.id.btn_task2:  
					
					 dManager.saveData("Button \"Task 2\"", "Press/Release event", press, release);
					 
					 Intent intent2 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent2.putExtra("selectedImage", selectedImage);
						 intent2.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent2.putExtra("taskName", "Task 2");
					 startActivity(intent2);
	
			        
					break;
					
				case R.id.btn_task3: 
					
					 dManager.saveData("Button \"Task 3\"", "Press/Release event", press, release);
					 Intent intent3 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent3.putExtra("selectedImage", selectedImage);
						 intent3.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent3.putExtra("taskName", "Task 3");
					 startActivity(intent3);
					 
			        
					break;
					
				case R.id.btn_task4: 
					
					 dManager.saveData("Button \"Task 4\"", "Press/Release event", press, release);
					 Intent intent4 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent4.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent4.putExtra("selectedImage", selectedImage);
						 intent4.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent4.putExtra("taskName", "Task 4");
					 startActivity(intent4);
			        
					break;
					
				case R.id.btn_task5: 
					
					 dManager.saveData("Button \"Task 5\"", "Press/Release event", press, release);
					 Intent intent5 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent5.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent5.putExtra("selectedImage", selectedImage);
						 intent5.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent5.putExtra("taskName", "Task 5");
					 startActivity(intent5);
			        
					break;
					
				case R.id.btn_task6: 
					
					 dManager.saveData("Button \"Task 6\"", "Press/Release event", press, release);
					 Intent intent6 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent6.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent6.putExtra("selectedImage", selectedImage);
						 intent6.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent6.putExtra("taskName", "Task 6");
					 startActivity(intent6);
			        
					break;
					
				case R.id.btn_task7: 
					
					 dManager.saveData("Button \"Task 7\"", "Press/Release event", press, release);
					 Intent intent7 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent7.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent7.putExtra("selectedImage", selectedImage);
						 intent7.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent7.putExtra("taskName", "Task 7");
					 startActivity(intent7);
			        
					break;
				
				case R.id.btn_task8: 
					
					 dManager.saveData("Button \"Task 8\"", "Press/Release event", press, release);
					 Intent intent8 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent8.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent8.putExtra("selectedImage", selectedImage);
						 intent8.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent8.putExtra("taskName", "Task 8");
					 startActivity(intent8);
			        
					break;
					
				case R.id.btn_task9: 
					
					 dManager.saveData("Button \"Task 9\"", "Press/Release event", press, release);
					 Intent intent9 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent9.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent9.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent9.putExtra("selectedImage", selectedImage);
						 intent9.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent9.putExtra("taskName", "Task 9");
					 startActivity(intent9);
			        
					break;
					
				case R.id.btn_task10: 
					
					 dManager.saveData("Button \"Task 10\"", "Press/Release event", press, release);
					 Intent intent10 = new Intent(getApplicationContext(), ProcessTask.class);
					 intent10.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 intent10.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 
					 if (isActive==true){
						 intent10.putExtra("selectedImage", selectedImage);
						 intent10.putExtra("taskValue", true);
						 isActive=false;
					 }
					 
					 intent10.putExtra("taskName", "Task 10");
					 startActivity(intent10);
			        
					break;
					
				case R.id.QoE:
					  
					dManager.saveData("Button \"QoE\"", "Press/Release event", press, release);
					
					 Intent intentQoE = new Intent(getApplicationContext(), QoEActivity.class);
					 intentQoE.putExtra("taskName", "QoE");
			         startActivity(intentQoE);
					 
					
					break;
					
				case R.id.btn_sendDatabase:
					String userMood = getMood();
					if (userMood==null){
						userMood = "default";
					}
					extractDatabaseFile(new DatabaseCommons(userMood + "_"));
					finish();
					break;
					
				
			}		
			
		}
	 
	 
	 	public void taskButtons(){
	 		btn_task1 = (Button) findViewById(R.id.btn_task1);
	        btn_task1.setOnClickListener(this);
	        btn_task1.setOnTouchListener(btnTouchTask1);
	        
	        btn_task2 = (Button) findViewById(R.id.btn_task2);
	        btn_task2.setOnClickListener(this);
	        btn_task2.setOnTouchListener(btnTouchTask2);
	        btn_task2.setEnabled(false);
	        
	        btn_task3 = (Button) findViewById(R.id.btn_task3);
	        btn_task3.setOnClickListener(this);
	        btn_task3.setOnTouchListener(btnTouchTask3);
	        btn_task3.setEnabled(false);
	        
	        btn_task4 = (Button) findViewById(R.id.btn_task4);
	        btn_task4.setOnClickListener(this);
	        btn_task4.setOnTouchListener(btnTouchTask4);
	        btn_task4.setEnabled(false);
	        
	        btn_task5 = (Button) findViewById(R.id.btn_task5);
	        btn_task5.setOnClickListener(this);
	        btn_task5.setOnTouchListener(btnTouchTask5);
	        btn_task5.setEnabled(false);
	        
	        btn_task6 = (Button) findViewById(R.id.btn_task6);
	        btn_task6.setOnClickListener(this);
	        btn_task6.setOnTouchListener(btnTouchTask6);
	        btn_task6.setEnabled(false);
	        
	        btn_task7 = (Button) findViewById(R.id.btn_task7);
	        btn_task7.setOnClickListener(this);
	        btn_task7.setOnTouchListener(btnTouchTask7);
	        btn_task7.setEnabled(false);
	        
	        btn_task8 = (Button) findViewById(R.id.btn_task8);
	        btn_task8.setOnClickListener(this);
	        btn_task8.setOnTouchListener(btnTouchTask8);
	        btn_task8.setEnabled(false);
	        
	        btn_task9 = (Button) findViewById(R.id.btn_task9);
	        btn_task9.setOnClickListener(this);
	        btn_task9.setOnTouchListener(btnTouchTask9);
	        btn_task9.setEnabled(false);
	        
	        btn_task10 = (Button) findViewById(R.id.btn_task10);
	        btn_task10.setOnClickListener(this);
	        btn_task10.setOnTouchListener(btnTouchTask10);
	        btn_task10.setEnabled(false);
	        
	        btn_QoERating = (Button) findViewById(R.id.QoE);
	        btn_QoERating.setOnClickListener(this);
	        btn_QoERating.setOnTouchListener(btnTouchTaskQoE);
	        btn_QoERating.setEnabled(false);
	        
	        btn_extractDB = (Button) findViewById(R.id.btn_sendDatabase);
	        btn_extractDB.setOnClickListener(this);
	        btn_extractDB.setOnTouchListener(btnTouchDB);
	        btn_extractDB.setEnabled(false);
	        
	        
	        
	 	}
	 	
	 	/*
	 	 * Duplication can be avoided by using only one View.OnTouchListener
	 	 * For this experiment, we declare multiple methods in order to avoid 
	 	 * potential conflicts
	 	 */
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
		  
		  private View.OnTouchListener btnTouchTaskQoE = new View.OnTouchListener() {
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
		  
		  private View.OnTouchListener btnTouchDB = new View.OnTouchListener() {
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
		
		//Disable back to previous activity
		@Override
		public void onBackPressed() {
		}
		
		@Override
		public void onResume(){
			IntentFilter filter;
		    filter = new IntentFilter(ProcessTask.taskStatusText);
		    receiver = new MessageReceiver();
		    registerReceiver(receiver, filter);
		    
			
			super.onResume();
			activateCurrentTaskButton();
			activateQoEButton();
		}
		
		
		@Override
		public void onPause() {
		    unregisterReceiver(receiver);
		    super.onPause();
		}
		
		
		public void activateQoEButton(){ 
		
			if (Commons.activateQoE==true){
				switch(Commons.counterTask){
				case 1: btn_task1.setText(statusFinished);
						btn_task1.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
				
				case 2: btn_task2.setText(statusFinished);
						btn_task2.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
				
				case 3: btn_task3.setText(statusFinished);
						btn_task3.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
					
				case 4: btn_task4.setText(statusFinished);
						btn_task4.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
					
				case 5: btn_task5.setText(statusFinished);
						btn_task5.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
					
				case 6: btn_task6.setText(statusFinished);
						btn_task6.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;	
				
				case 7: btn_task7.setText(statusFinished);
						btn_task7.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
					
				case 8: btn_task8.setText(statusFinished);
						btn_task8.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
					
				case 9: btn_task9.setText(statusFinished);
						btn_task9.setEnabled(false);
						btn_QoERating.setEnabled(true);
					break;
					
					
				case 10: btn_task10.setText(statusFinished);
						btn_task10.setEnabled(false);
						btn_QoERating.setEnabled(true);
						break;
				}
			}else{
				btn_QoERating.setEnabled(false);
			}
			
			
		}
		
		
		public void activateCurrentTaskButton(){
			switch(Commons.counterTask){
			case 1: btn_task1.setEnabled(true);
					
				break;
			
			case 2: btn_task1.setEnabled(false);
					btn_task2.setEnabled(true);
				break;
			
			case 3: btn_task2.setEnabled(false);
					btn_task3.setEnabled(true);
				break;
				
			case 4: btn_task3.setEnabled(false);
					btn_task4.setEnabled(true);
				break;
				
			case 5: btn_task4.setEnabled(false);
					btn_task5.setEnabled(true);
				break;
				
			case 6: btn_task5.setEnabled(false);
					btn_task6.setEnabled(true);
				break;	
			
			case 7: btn_task6.setEnabled(false);
					btn_task7.setEnabled(true);
				break;
				
			case 8: btn_task7.setEnabled(false);
					btn_task8.setEnabled(true);
				break;
				
			case 9: btn_task8.setEnabled(false);
					btn_task9.setEnabled(true);
				break;
				
				
			case 10:btn_task9.setEnabled(false);
					btn_task10.setEnabled(true);
				break;
			
			case 11:btn_task10.setEnabled(false); 
					btn_extractDB.setEnabled(true);
				break;
			
			}
		}
		
		
		public class MessageReceiver extends BroadcastReceiver {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	         isActive = true;
	         boolean iscloud = intent.getBooleanExtra("taskValue", false);
	         if(iscloud){
	         selectedImage = intent.getStringExtra("selectedImage");
	         }
	         
	         try {
	                Uri notification = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION);
	                android.media.Ringtone r = android.media.RingtoneManager.getRingtone(getApplicationContext(), notification);
	                r.play();
	            } catch (Exception e) {}
	        }
	      }
	 	
		public void extractDatabaseFile(DatabaseCommons db){			
			   try { 
				db.copyDatabaseFile();
			   } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			   }
		}
		
		public String getMood(){
			final CharSequence states[] = new CharSequence[] {"Normal", "Emotional"};
			String selectedState = null;

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Pick a color");
			builder.setItems(states, new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        String selectedState = states[which].toString();
			    }
			});
			builder.create().show();
			return selectedState;
			
			
		}

		
		
	 
}
