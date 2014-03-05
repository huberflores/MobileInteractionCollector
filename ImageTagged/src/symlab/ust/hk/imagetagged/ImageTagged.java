package symlab.ust.hk.imagetagged;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.R;
import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.media.ExifInterface;
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
    
    private String userMood = "Default";
    
    private Button btn_start;
    private Button btn_takePicture;
	
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
        
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        
        btn_start.setOnTouchListener(btnTouch); 
        
        btn_takePicture = (Button) findViewById(R.id.btn_takePicture);
        btn_takePicture.setOnClickListener(this);
        
        btn_takePicture.setOnTouchListener(btnTouch);
        
        getDir(Commons.appPicturesPath);
        checkDirPictures();
        
        dManager = new DatabaseManager(this);
    	dManager.setDbUri(dbUri);
    	
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);

        
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
			try {
				openCamera();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
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
	private File outputFileName;
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
         
            if(TAKE_PICTURE_CODE == requestCode){  
                    processCameraImage(data);
            }
    }
	
	private void openCamera() throws IOException{
		
		outputFileName = createImageFile(".tmp");
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFileName));
     
		startActivityForResult(takePictureIntent, TAKE_PICTURE_CODE);
	}
	
		private void processCameraImage(Intent intent){
	    			
		int imageExifOrientation = 0;

			    try
			    {


			    ExifInterface exif;
			        exif = new ExifInterface(outputFileName.getAbsolutePath());
			        imageExifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
			                                    ExifInterface.ORIENTATION_NORMAL);
			    }
			    catch (IOException e1)
			    {
			        e1.printStackTrace();
			    }

			    int rotationAmount = 0;

			    if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
			    {
			        // Need to do some rotating here...
			        rotationAmount = 270;
			    }
			    if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
			    {
			        // Need to do some rotating here...
			        rotationAmount = 90;
			    }
			    if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
			    {
			        // Need to do some rotating here...
			        rotationAmount = 180;
			    }       

	    
	    int targetW = 240;
	    int targetH = 320; 

	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(outputFileName.getAbsolutePath(), bmOptions);
	    int photoWidth = bmOptions.outWidth;
	    int photoHeight = bmOptions.outHeight;

	    int scaleFactor = Math.min(photoWidth/targetW, photoHeight/targetH);

	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap scaledDownBitmap = BitmapFactory.decodeFile(outputFileName.getAbsolutePath(), bmOptions);
	    
	    if (rotationAmount != 0)
	    {
	        Matrix mat = new Matrix();
	        mat.postRotate(rotationAmount);
	        scaledDownBitmap = Bitmap.createBitmap(scaledDownBitmap, 0, 0, scaledDownBitmap.getWidth(), scaledDownBitmap.getHeight(), mat, true);
	    }    

	    FileOutputStream outFileStream = null;
	    try
	    {
	        File mLastTakenImageAsJPEGFile = createImageFile(".jpg");
	        outFileStream = new FileOutputStream(mLastTakenImageAsJPEGFile);
	        scaledDownBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outFileStream);
	    }
	    catch (Exception e)
	    {  
	        e.printStackTrace(); 
	    } 
	    

		
		
	}
	
	
	private File createImageFile(String fileExtensionToUse) throws IOException 
	{

	    File storageDir = new File(
	            Environment.getExternalStoragePublicDirectory(
	                Environment.DIRECTORY_PICTURES
	            ), 
	            "MyAppQoE"
	        );      

	    if(!storageDir.exists())
	    {
	        if (!storageDir.mkdir())
	        {
	            //Log.d(TAG,"was not able to create it");
	        }
	    }
	    if (!storageDir.isDirectory())
	    {
	        //Log.d(TAG,"Don't think there is a dir there.");
	    }

	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "FOO_" + timeStamp + "_image";

	    File image = File.createTempFile(
	        imageFileName, 
	        fileExtensionToUse, 
	        storageDir
	    );

	    return image;
	}    
	
	private void getDir(String dirPath)
	 {
		 
		 listOfImages = new ArrayList<String>();

		 File f = new File(dirPath);
		 
		 if (!f.exists()){
			 f.mkdir();
		 }
		 
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
	
	@Override
	protected void onResume(){
		super.onResume();
		getDir(Commons.appPicturesPath);
		checkDirPictures();
	}
	
	public void checkDirPictures(){
        if (listOfImages.size() > 0){
        	btn_start.setEnabled(true);
        }else{
        	btn_start.setEnabled(false); 
        }
        
	}

}
