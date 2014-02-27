package symlab.ust.hk.imagetagged;


import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.TaskDescriptor;
import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;


public class TasksActivity extends Activity{
	
	private Uri taskUri;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState); 
	        setContentView(R.layout.activity_tasks_list);
	        
	        Bundle extras = getIntent().getExtras();
	        
	        taskUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
	                .getParcelable(MyTaskContentProvider.CONTENT_ITEM_TYPE);
	

	        if (extras != null) {
	         taskUri = extras
	               .getParcelable(MyTaskContentProvider.CONTENT_ITEM_TYPE);

	        }
	        

	       //saveData("Button Enter", "Press/Release event", 0.3, 0.21);
	        
	        	        
	   }
	 
	 	public void saveData(String buttonName, String description, double pressTime, double releaseTime){
		   ContentValues values = new ContentValues();
		    values.put(TaskDescriptor.COLUMN_CURRENT_TASK_NAME, "task " + Commons.currentTask);
		    values.put(TaskDescriptor.COLUMN_TASK_BUTTON_ID, buttonName);
		    values.put(TaskDescriptor.COLUMN_TASK_DESCRIPTION, description);
		    values.put(TaskDescriptor.COLUMN_TASK_INITIAL_TIME, pressTime);
		    values.put(TaskDescriptor.COLUMN_TASK_FINISH_TIME, releaseTime);
		 
		    if (taskUri == null) {
			      //New
			      taskUri = getContentResolver().insert(MyTaskContentProvider.CONTENT_URI, values);
			} else { 
			      //Update
			      getContentResolver().update(taskUri, values, null, null);
			}
	   }
	
	
	
	 
}
