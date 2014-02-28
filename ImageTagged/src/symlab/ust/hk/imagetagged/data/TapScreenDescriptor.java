package symlab.ust.hk.imagetagged.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TapScreenDescriptor {

	//Database schema
		//Table
		public static final String TABLE_TAP = "tap";
		//Attributes
		public static final String COLUMN_TAP_ID = "_id";
		public static final String COLUMN_CURRENT_TASK_NAME = "actual_task";
		public static final String COLUMN_TAP_DESCRIPTION ="description";
		public static final String COLUMN_TAP_TIME ="initial_time";
		
		// Database creation SQL statement
		private static final String DATABASE_CREATE = "create table "
				+ TABLE_TAP
				+ "("
				+ COLUMN_TAP_ID + " integer primary key autoincrement, "
				+ COLUMN_CURRENT_TASK_NAME + " text not null, "
				+ COLUMN_TAP_DESCRIPTION + " text not null, "
				+ COLUMN_TAP_TIME + " real "
				+");";
		
		 //Database creation
		 public static void onCreate(SQLiteDatabase database) {
			    database.execSQL(DATABASE_CREATE);
		 }

		 
		 public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			      int newVersion) {
			    Log.w(TaskDescriptor.class.getName(), "Upgrading database from version "
			        + oldVersion + " to " + newVersion
			        + ", which will destroy all old data");
			    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TAP);
			    onCreate(database);
		 }
}
