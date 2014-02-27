package symlab.ust.hk.imagetagged;

import java.io.IOException;

import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.Utilities.DatabaseCommons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.TaskDescriptor;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.net.Uri;


public class ImageTagged extends Activity implements android.view.View.OnClickListener{
 
	private Uri taskUri;
	private double press = 0f;
	private double release = 0f;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_tagged);
        
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        
        btn_start.setOnTouchListener(btnTouch); 
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_tagged, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
			case R.id.btn_start: 
			
				 saveData("Button Enter", "Press/Release event", press, release);
				 
				 //extractDatabaseFile(new DatabaseCommons());
				 Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
				 intent.putExtra("", taskUri);
		         startActivity(intent);       
		        
				break;		
		}		
		
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
	
	
	   public void extractDatabaseFile(DatabaseCommons db){			
		   try { 
			db.copyDatabaseFile();
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	   }
	   
	   

	   private View.OnTouchListener btnTouch = new View.OnTouchListener() {
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

	
    
}
