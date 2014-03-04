package symlab.ust.hk.imagetagged.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskQoEDescriptor {

		//Database schema
		//Table
		public static final String TABLE_QOE = "qoe";
		//Attributes
		public static final String COLUMN_TASK_ID = "_id";
		public static final String COLUMN_CURRENT_TASK_NAME = "actual_task";
		public static final String COLUMN_TASK_QOE_RATING = "rating";
		
		
		// Database creation SQL statement
		private static final String DATABASE_CREATE = "create table "
				+ TABLE_QOE 
				+ "("
				+ COLUMN_TASK_ID + " integer primary key autoincrement, "
				+ COLUMN_CURRENT_TASK_NAME + " text not null, "
				+ COLUMN_TASK_QOE_RATING+ " real"
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
			    database.execSQL("DROP TABLE IF EXISTS " + TABLE_QOE);
			    onCreate(database);
		 }
		
	}

