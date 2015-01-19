package de.hsworms.fritz.ema.todo;

import de.hsworms.fritz.ema.todo.TodoContract.CategoryDbEntry;
import de.hsworms.fritz.ema.todo.TodoContract.TodoDbEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDbHelper extends SQLiteOpenHelper{
	
	final String TEXT_TYPE = " TEXT";
	final String COMMA_SEP = ",";
	final String INT_TYPE = " INTEGER";
	final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + TodoDbEntry.TABLE_NAME + " (" +
	    TodoDbEntry._ID + " INTEGER PRIMARY KEY," +
	    TodoDbEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
	    TodoDbEntry.COLUMN_NAME_CATEGORY + INT_TYPE + COMMA_SEP +
	    TodoDbEntry.COLUMN_NAME_UPDATED + INT_TYPE + COMMA_SEP +
	    " FOREIGN KEY(" + TodoDbEntry.COLUMN_NAME_CATEGORY + ") REFERENCES " + CategoryDbEntry.TABLE_NAME + "(" + CategoryDbEntry._ID + ")" + ")";
	final String SQL_CREATE_CATEGORIES = 
			"CREATE TABLE " + CategoryDbEntry.TABLE_NAME + " (" +
			CategoryDbEntry._ID + " INTEGER PRIMARY KEY," +
			CategoryDbEntry.COLUMN_NAME_NAME + TEXT_TYPE + " )";

	final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + TodoDbEntry.TABLE_NAME;
	
	final String SQL_DELETE_CATEGORIES = 
		"DROP TABLE IF EXISTS " + CategoryDbEntry.TABLE_NAME;
	
	
	    // If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "Todo.db";

	    public TodoDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    public void onCreate(SQLiteDatabase db) {
	    	db.execSQL(SQL_CREATE_CATEGORIES);
	    	db.execSQL(SQL_CREATE_ENTRIES);
	    }
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        db.execSQL(SQL_DELETE_CATEGORIES);
	        onCreate(db);
	    }
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	

}
