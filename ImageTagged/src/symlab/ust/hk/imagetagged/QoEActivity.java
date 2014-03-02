package symlab.ust.hk.imagetagged;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import symlab.ust.hk.imagetagged.Utilities.Commons;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QoEActivity extends Activity implements android.view.View.OnClickListener {
	
	private static final int TAKE_PICTURE_CODE = 100;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qoe_rating);
        
        Button yourPicture = (Button) findViewById(R.id.btn_QoEPicture);
        yourPicture.setOnClickListener(this);
        
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
				
			case R.id.btn_QoEPicture:
				
				//dManager.saveData("Button - Take Picture", "Press/Release event", press, release);
				openCamera();
				break;
		}		
		
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
}
