package symlab.ust.hk.imagetagged;

import symlab.ust.hk.imagetagged.Utilities.Commons;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProcessTask extends Activity implements android.view.View.OnClickListener {

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
	      
	      Bundle extras = getIntent().getExtras();
	      buttonAction = false;
	      
	      if (extras!=null){
	    	  //pContext = this;
	    	  initialLabels(extras.getString("taskName"));
	    	  runTask();
	       }
	      
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
						Intent listOfTasks= new Intent(ProcessTask.this, TasksActivity.class);
					    listOfTasks.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					    startActivity(listOfTasks);
						
					}else{
						//Go results
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
		  taskState.setText("Go Back!");
		  taskState.setOnClickListener(this);
		  
	      taskName = (TextView) findViewById(R.id.taskName);
	      taskName.setText(t);
	      
	      taskStatus = (TextView) findViewById(R.id.taskStatus);
	      taskStatus.setText(taskStatusText);
	      
	 }
	 
	 
	 
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
	   public void onResume() {
	        IntentFilter filter;
	        filter = new IntentFilter(ProcessTask.taskStatusText);
	        receiver = new MessageReceiver();
	        registerReceiver(receiver, filter);
	    
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
	    
	    
	    
			    
}
