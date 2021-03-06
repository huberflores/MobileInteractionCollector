package symlab.ust.hk.imagetagged.data;

import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class DatabaseManager {
		
	private Uri dbUri;
	private Context dContext;
	
	public DatabaseManager(Context c){
		this.dContext = c;
	}
	
	public void saveData(String buttonName, String description, double pressTime, double releaseTime){
		ContentValues values = new ContentValues();
		values.put(TaskDescriptor.COLUMN_CURRENT_TASK_NAME, "Activity: " + Commons.currentTask);
		values.put(TaskDescriptor.COLUMN_TASK_BUTTON_ID, buttonName);
		values.put(TaskDescriptor.COLUMN_TASK_DESCRIPTION, description);
		values.put(TaskDescriptor.COLUMN_TASK_INITIAL_TIME, pressTime);
		values.put(TaskDescriptor.COLUMN_TASK_FINISH_TIME, releaseTime);
		 	
		switchAuthority(values, "task");	
		
	 }
	
	
	public void saveData(String description, double eventTime){
		ContentValues values = new ContentValues();
		values.put(TapScreenDescriptor.COLUMN_CURRENT_TASK_NAME, "Activity: " + Commons.currentTask);
		values.put(TapScreenDescriptor.COLUMN_TAP_DESCRIPTION, description);
		values.put(TapScreenDescriptor.COLUMN_TAP_TIME, eventTime);
		 	
		switchAuthority(values, "tap");	
		
	}
	
	
	public void saveData(double rating){
		ContentValues values = new ContentValues();
		values.put(TaskQoEDescriptor.COLUMN_CURRENT_TASK_NAME, "Task: " + Commons.counterTask);
		values.put(TaskQoEDescriptor.COLUMN_TASK_QOE_RATING, rating);
		 	
		switchAuthority(values, "qoe");	
		
	}
	
	public void saveData(double startTime, double endTime, double addedDelay){
		ContentValues values = new ContentValues();
		values.put(TaskComputationalDescriptor.COLUMN_CURRENT_TASK_NAME, "Task: " + Commons.counterTask);
		values.put(TaskComputationalDescriptor.COLUMN_START_TIME, startTime);
		values.put(TaskComputationalDescriptor.COLUMN_END_TIME, endTime);
		values.put(TaskComputationalDescriptor.COLUMN_START_TIME, addedDelay);
		 	
		switchAuthority(values, "computational");	
		
	}
	
	
	public void switchAuthority(ContentValues values, String authority){
		
		if (authority.equals("task")){
			dbUri = dContext.getContentResolver().insert(MyTaskContentProvider.CONTENT_URI, values);
		}else{
			if (authority.equals("tap")){
				dbUri = dContext.getContentResolver().insert(MyTaskContentProvider.CONTENT_URI_TAPS, values);
			}else{
				if (authority.equals("qoe")){
					dbUri = dContext.getContentResolver().insert(MyTaskContentProvider.CONTENT_URI_QOES, values);
				}else{
					if (authority.equals("computational")){
						dbUri = dContext.getContentResolver().insert(MyTaskContentProvider.CONTENT_URI_COMPUTATIONALS, values);
					}
				}
			}
		}
		
	}
	
	
	public void setDbUri(Uri cursor){
		this.dbUri = cursor;
	}


}
