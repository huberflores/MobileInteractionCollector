package symlab.ust.hk.imagetagged;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.Utilities.DatabaseCommons;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
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
	private static final int TAKE_PICTURE_CODE = 100;

	private Uri dbUri; 
	private double press = 0f;
	private double release = 0f;
	
	private List<String> listOfImages = null;
	private String root="/";
	
    private GestureDetectorCompat mDetector;
    
    private DatabaseManager dManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_tagged);
        
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction(); 
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                Log.info("Main Activity is not the root.  Finishing Main Activity instead of launching.");
                finish();
                return;       
            }
        }
        
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        
        btn_start.setOnTouchListener(btnTouch); 
        
        Button btn_takePicture = (Button) findViewById(R.id.btn_takePicture);
        btn_takePicture.setOnClickListener(this);
        
        btn_takePicture.setOnTouchListener(btnTouch);
        
        getDir(Commons.appPicturesPath);
        if (listOfImages.size() > 0){
        	
        }else{
        	btn_start.setEnabled(false);
        }
        
        
        
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
			
				 dManager.saveData("Button - Enter", "Press/Release event", press, release);
				 Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
				 intent.putExtra("db", dbUri);
		         startActivity(intent);       
		        
				break;	
				
			case R.id.btn_takePicture:
				
				dManager.saveData("Button - Take Picture", "Press/Release event", press, release);
				openCamera();
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
	
	//Camera
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
     
            if(TAKE_PICTURE_CODE == requestCode){
                    processCameraImage(data);
            }
    }
 
	private void openCamera(){
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
     
		startActivityForResult(intent, TAKE_PICTURE_CODE);
	}
	
	private void processCameraImage(Intent intent){
	    
	     
	    Bitmap rawBitmap = (Bitmap)intent.getExtras().get("data");

	    ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	    rawBitmap.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos); 
	    byte[] bitmapdata = bos.toByteArray();
	    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
	   
	    
	    BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
		bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
		Bitmap cameraBitmap=BitmapFactory.decodeStream(bs);
		
	    
		
		String filepath = Commons.appPicturesPath + "facedetect" + System.currentTimeMillis() + ".jpg";
        
        try {
                FileOutputStream fos = new FileOutputStream(filepath);
                 
                cameraBitmap.compress(CompressFormat.JPEG, 90, fos);
                 
                fos.flush();
                fos.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }

		
		
	}
	
	private void getDir(String dirPath)
	 {
		 
		 listOfImages = new ArrayList<String>();

		 File f = new File(dirPath);
		 File[] files = f.listFiles();

		 for(int i=0; i < files.length; i++)
		 {
			 File file = files[i];
			 String filename = file.getName();
			 String ext = filename.substring(filename.lastIndexOf('.')+1, filename.length());

			  if(ext.equals("JPG")||ext.equals("jpg")||ext.equals("PNG")||ext.equals("png"))
			  {
				listOfImages.add(file.getPath());  
			  }
			   
		 }

		 
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
