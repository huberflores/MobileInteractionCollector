package symlab.ust.hk.imagetagged.Utilities;

import java.io.File;

import android.os.Environment;

public class Commons {
	public static String currentTask = "";
	
	public static int counterTask = 1;
		
	public static boolean activateQoE = false; 
	
	public static String appPicturesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MyAppQoE";
	

}
