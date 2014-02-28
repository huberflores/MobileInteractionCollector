package symlab.ust.hk.imagetagged;

import java.io.IOException;
import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.Utilities.DatabaseCommons;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.view.GestureDetector;


public class ImageTagged extends Activity implements android.view.View.OnClickListener, GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener{
	
	private Logger Log = Logger.getLogger(ImageTagged.class.getName());

	private Uri dbUri;
	private double press = 0f;
	private double release = 0f;
	
    private GestureDetectorCompat mDetector;
    
    private DatabaseManager dManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_tagged);
        
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        
        btn_start.setOnTouchListener(btnTouch); 
        
        dManager = new DatabaseManager(this);
    	dManager.setDbUri(dbUri);
    	
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);

        
        
        //extractDatabaseFile(new DatabaseCommons());
          
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
			
				 dManager.saveData("Button Enter", "Press/Release event", press, release, "task");
				 Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
				 intent.putExtra("db", dbUri);
		         startActivity(intent);       
		        
				break;		
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
		Commons.currentTask = "Intro";
	}
			
	@Override
	public void onRestart(){
		super.onRestart();
		Commons.currentTask = "Intro";
	}

}
