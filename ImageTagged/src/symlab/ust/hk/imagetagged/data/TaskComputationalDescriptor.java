package symlab.ust.hk.imagetagged.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskComputationalDescriptor {
		//Database schema
		//Table
		public static final String TABLE_COMPUTATIONAL = "computational";
		//Attributes
		public static final String COLUMN_TASK_ID = "_id";
		public static final String COLUMN_CURRENT_TASK_NAME = "actual_task";
		public static final String COLUMN_START_TIME = "start_time";
		public static final String COLUMN_END_TIME = "end_time";
		public static final String COLUMN_ADDED_DELAY = "added_delay";
		
		
		// Database creation SQL statement
		private static final String DATABASE_CREATE = "create table "
				+ TABLE_COMPUTATIONAL 
				+ "("
				+ COLUMN_TASK_ID + " integer primary key autoincrement, "
				+ COLUMN_CURRENT_TASK_NAME + " text not null, "
				+ COLUMN_START_TIME+ " real,"
				+ COLUMN_END_TIME+ " real,"
				+ COLUMN_ADDED_DELAY+ " real"
				+");";
		
		 //Database creation
		 public static void onCreate(SQLiteDatabase database) {
			    database.execSQL(DATABASE_CREATE);
		 }

		 
		 public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			      int newVersion) {
			    Log.w(TaskQoEDescriptor.class.getName(), "Upgrading database from version "
			        + oldVersion + " to " + newVersion
			        + ", which will destroy all old data");
			    database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPUTATIONAL);
			    onCreate(database);
		 }
		
	}

