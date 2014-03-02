package symlab.ust.hk.imagetagged;

import java.io.File;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class FaceDetectionView extends Activity implements android.view.View.OnClickListener{
	
	/** Called when the activity is first created. */
	
	static Logger Log = Logger.getLogger(FaceDetectionView.class.getName());

    private static String selectedImage;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(R.layout.activity_picture_viewer);
        
        Bundle extras = getIntent().getExtras();
        selectedImage = extras.getString("selectedImage");
        
        MyView imageView = (MyView) findViewById(R.id.image_view);
        BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
		bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.faceswapping,bitmapFatoryOptions));
        
        imageView.buildDrawingCache();
        
        Button btn_finish = (Button) findViewById(R.id.finish_task);
        btn_finish.setOnClickListener(this);
        
        
    }
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		
			case R.id.finish_task: 
			
				 //dManager.saveData("Button - Enter", "Press/Release event", press, release);
				 Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
				 //intent.putExtra("db", dbUri);
		         startActivity(intent);       
		        
				break;	
				
			
		}		
		
	}
    
    public static class MyView extends ImageView
    {
    	private Bitmap myBitmap;
    	private int width, height;
    	private FaceDetector.Face[] detectedFaces;
    	private int NUMBER_OF_FACES=4;
    	private FaceDetector faceDetector;
    	private int NUMBER_OF_FACE_DETECTED;
    	private float eyeDistance;
    	
    	public MyView(Context context) 
    	{
    		super(context);
    		
    	}
    	
    	public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }
    	
    	@Override
    	protected void onDraw(Canvas canvas) 
    	{
    		//Log.info("Here we are");
			File myFile = new File(selectedImage); 
			BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
			
			myBitmap = BitmapFactory.decodeFile(myFile.getAbsolutePath(), bitmapFatoryOptions);
 
    		width=myBitmap.getWidth();
    		height=myBitmap.getHeight();
    		detectedFaces=new FaceDetector.Face[NUMBER_OF_FACES];
    		faceDetector=new FaceDetector(width,height,NUMBER_OF_FACES);
    		NUMBER_OF_FACE_DETECTED=faceDetector.findFaces(myBitmap, detectedFaces);
    		
    		canvas.drawBitmap(myBitmap, 0,0, null);
    		Paint myPaint = new Paint();
            myPaint.setColor(Color.GREEN);
            myPaint.setStyle(Paint.Style.STROKE); 
            myPaint.setStrokeWidth(3);

            for(int count=0;count<NUMBER_OF_FACE_DETECTED;count++)
            {
            	Face face=detectedFaces[count];
            	PointF midPoint=new PointF();
            	face.getMidPoint(midPoint);
            	
            	eyeDistance=face.eyesDistance();
            	canvas.drawRect(midPoint.x-eyeDistance, midPoint.y-eyeDistance, midPoint.x+eyeDistance, midPoint.y+eyeDistance, myPaint);
            }
    	}
    	
    }

	

}
