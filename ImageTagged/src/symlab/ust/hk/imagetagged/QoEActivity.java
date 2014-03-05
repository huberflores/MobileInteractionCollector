package symlab.ust.hk.imagetagged;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.Utilities.Commons;
import symlab.ust.hk.imagetagged.contentprovider.MyTaskContentProvider;
import symlab.ust.hk.imagetagged.data.DatabaseManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QoEActivity extends Activity implements android.view.View.OnClickListener , GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener {
	
	private static final int TAKE_PICTURE_CODE = 100;
	
	private RadioGroup radioQoEGroup;
	private RadioButton radioQoEButton;
	private Button btnDisplay;
	
	private Logger Log = Logger.getLogger(QoEActivity.class.getName());
	
	private Uri dbUri; 
	private double press = 0f;
	private double release = 0f;
	 
	private GestureDetectorCompat mDetector;
	    
	private DatabaseManager dManager;

	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qoe_rating);
        
        Bundle extras = getIntent().getExtras();
	     
        dbUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
    	    	.getParcelable(MyTaskContentProvider.CONTENT_ITEM_TYPE);

         
        Button yourPicture = (Button) findViewById(R.id.btn_QoEPicture);
        yourPicture.setOnClickListener(this);
        yourPicture.setOnTouchListener(btnTouchYourPicture);
         
        Button submitQoE = (Button) findViewById(R.id.btn_submitQoE);
        submitQoE.setOnClickListener(this);
        submitQoE.setOnTouchListener(btnTouchQoE);
        
        dManager = new DatabaseManager(this);
    	dManager.setDbUri(dbUri);
    	
    	mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
                
	}
	
	private View.OnTouchListener btnTouchYourPicture = new View.OnTouchListener() {
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
	 
	 private View.OnTouchListener btnTouchQoE = new View.OnTouchListener() {
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
	

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
				
			case R.id.btn_QoEPicture:
				
				dManager.saveData("Button - Provide picture", "Press/Release event", press, release);
			try {
				openCamera();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			case R.id.btn_submitQoE:
				
				
				dManager.saveData("Button - Submit QoE", "Press/Release event", press, release);
				radioQoEGroup = (RadioGroup) findViewById(R.id.radioGroupQoE);
				int selectedId = radioQoEGroup.getCheckedRadioButtonId();
				
				radioQoEButton = (RadioButton) findViewById(selectedId);			
				
				String value = radioQoEButton.getText().toString().trim();
				String newValue = value.substring(0,1);
				
				dManager.saveData(Float.parseFloat(newValue));
				
				Intent listOfTasks= new Intent(getApplicationContext(), TasksActivity.class);
				listOfTasks.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(listOfTasks);
				finish();
				Commons.activateQoE = false;
				Commons.counterTask++;
				
				break;
		}		
		
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
			Commons.currentTask = "QoE Rating ";
		}
		
		@Override
		public void onRestart(){
			super.onRestart();
			Commons.currentTask = "QoE Rating ";
		}

}
