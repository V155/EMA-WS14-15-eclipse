package de.hsworms.fritz.ema.aufgabe03;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.hsworms.fritz.ema.aufgabe03.TodoContract.CategoryDbEntry;
import de.hsworms.fritz.ema.aufgabe03.TodoContract.TodoDbEntry;

public class TodoDatabaseProvider {
	
	private final String TAG = "TodoDatabaseProvider";
	
	public TodoDatabaseProvider(){
		
	}
	
	
	
	public long writeTodoToDb(TodoDbHelper mDbHelper, TodoEntry entry){
	
	// Gets the data repository in write mode
	SQLiteDatabase db = mDbHelper.getWritableDatabase();

	// Create a new map of values, where column names are the keys
	ContentValues values = new ContentValues();
	values.put(TodoDbEntry.COLUMN_NAME_CATEGORY, entry.getCategoryId());
	values.put(TodoDbEntry.COLUMN_NAME_TEXT, entry.getText());
	values.put(TodoDbEntry.COLUMN_NAME_UPDATED, entry.getTimeUpdated());

	// Insert the new row, returning the primary key value of the new row
	long newRowId;
	newRowId = db.insert(
	         TodoDbEntry.TABLE_NAME,
	         null,
	         values);
	
	Log.d(TAG, "Added Todo " + newRowId + " to " + entry.getCategoryId());
	
	db.close();
	
	return newRowId;
	
	}
	
	
	
	public Cursor readTodoFromDb(TodoDbHelper mDbHelper, int catId){
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    TodoDbEntry._ID,
		    TodoDbEntry.COLUMN_NAME_CATEGORY,
		    TodoDbEntry.COLUMN_NAME_TEXT,
		    TodoDbEntry.COLUMN_NAME_UPDATED
		    };

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
		    TodoDbEntry.COLUMN_NAME_UPDATED + " DESC";
		
		String[] values = {
			catId + ""	
		};
		
		

		Cursor c = db.query(
		    TodoDbEntry.TABLE_NAME,	// The table to query
		    projection,				// The columns to return
		    TodoDbEntry.COLUMN_NAME_CATEGORY + "=?",// The columns for the WHERE clause
		    values,					// The values for the WHERE clause
		    null,					// don't group the rows
		    null,					// don't filter by row groups
		    sortOrder				// The sort order
		    );
		
//		db.close();
		
		return c;
	}
	
	public void deleteTodoFromDb(TodoDbHelper mDbHelper, TodoEntry entry){
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		// Define 'where' part of query.
		String selection = TodoDbEntry._ID + " LIKE ?";
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(entry.getId()) };
		// Issue SQL statement.
		db.delete(TodoDbEntry.TABLE_NAME, selection, selectionArgs);
		
		db.close();
	}
	
	//Methods to access the Category Table
	
	public long writeCategoryToDb(TodoDbHelper mDbHelper, CategoryEntry entry){
		
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(CategoryDbEntry.COLUMN_NAME_NAME, entry.getCategoryName());

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
		         CategoryDbEntry.TABLE_NAME,
		         null,
		         values);
		Log.d(TAG, "Adding Category " + newRowId + " " + entry.getCategoryName());
		
		db.close();
		
		return newRowId;
		
		}
		
		
		
		public Cursor readCategoryFromDb(TodoDbHelper mDbHelper){
			
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			
//			final String SQL_CREATE_CATEGORIES = 
//					"CREATE TABLE " + CategoryDbEntry.TABLE_NAME + " (" +
//					CategoryDbEntry._ID + " INTEGER PRIMARY KEY," +
//					CategoryDbEntry.COLUMN_NAME_NAME + " TEXT" + " )";
//			
//			db.execSQL(SQL_CREATE_CATEGORIES);
			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
				CategoryDbEntry._ID,
			    CategoryDbEntry.COLUMN_NAME_NAME
			    };

			Cursor c = db.query(
			    CategoryDbEntry.TABLE_NAME,	// The table to query
			    projection,				// The columns to return
			    null,					// The columns for the WHERE clause
			    null,					// The values for the WHERE clause
			    null,					// don't group the rows
			    null,					// don't filter by row groups
			    null					// The sort order
			    );
			
			//db.close();
			
			return c;
		}
		
		public void deleteCategoryFromDb(TodoDbHelper mDbHelper, CategoryEntry entry){
			
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			
			// Define 'where' part of query.
			String selection = CategoryDbEntry._ID + " LIKE ?";
			// Specify arguments in placeholder order.
			String[] selectionArgs = { String.valueOf(entry.getId()) };
			// Issue SQL statement.
			db.delete(CategoryDbEntry.TABLE_NAME, selection, selectionArgs);
			
			db.close();
		}
		
		public int getNumOfEntriesInCategory(TodoDbHelper mDbHelper, int catId){
			
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			
			int numOfEntries = (int)DatabaseUtils.queryNumEntries(db, TodoDbEntry.TABLE_NAME,
			                TodoDbEntry.COLUMN_NAME_CATEGORY + "=?", new String[] {catId + ""});
			
			db.close();
			return numOfEntries;
		}

}
