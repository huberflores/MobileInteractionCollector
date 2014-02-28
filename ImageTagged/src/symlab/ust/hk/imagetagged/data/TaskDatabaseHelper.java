package symlab.ust.hk.imagetagged.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "perception-data.db";
	
	private static final int DATABASE_VERSION = 1;

	
	public TaskDatabaseHelper(Context context) {

	    super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		TaskDescriptor.onCreate(db);
		TapScreenDescriptor.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		TaskDescriptor.onUpgrade(db, oldVersion, newVersion);
	
	}

}


