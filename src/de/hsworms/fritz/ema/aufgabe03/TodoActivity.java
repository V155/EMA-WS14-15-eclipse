package de.hsworms.fritz.ema.aufgabe03;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SyncStateContract.Columns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import de.hsworms.fritz.ema.R;
import de.hsworms.fritz.ema.aufgabe03.TodoContract.TodoDbEntry;

public class
        TodoActivity extends ListActivity {

    private ArrayList<String> todoList;
    private ArrayList<TodoEntry> entryList;
    private String todoCategory;
    private int todoCategoryId;
    private Intent intent;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private ArrayAdapter<String> adapter;
    private static String TAG = "TodoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_todo);
        intent = getIntent();
        todoCategoryId = Integer.parseInt(intent.getStringExtra(Aufgabe03.KEY_EXTRA_CATEGORY_NAME).split(";")[0]);
        todoCategory = intent.getStringExtra(Aufgabe03.KEY_EXTRA_CATEGORY_NAME).split(";")[1];
        this.setTitle(todoCategory);
        rereadFromDb();

//        preferences = this.getSharedPreferences(getString(R.string.categoryListPreferencesKey), Context.MODE_PRIVATE);
//        preferencesEditor = preferences.edit();
//        preferences = this.getSharedPreferences(getString(R.string.categoryListPreferencesKey), Context.MODE_PRIVATE);
//        preferencesEditor = preferences.edit();
//        String entriesString = preferences.getString(todoCategory, "");

//        if (!entriesString.isEmpty()) {
//            String[] entries = entriesString.split(";");
//            for (String entry : entries) {
//                todoList.add(entry);
//            }
//        }
        

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoList);
        this.setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
                builder.setTitle("Remove Todo");
                builder.setMessage("Do you really want to remove this todo?");

                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    	Log.d(TAG, "deleting todo " + position + " " + entryList.get(position).getCategory() + " " + entryList.get(position).getCategoryId());
                    	deleteTodoEntry(entryList.get(position));
                    	entryList.remove(position);
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();
                        //savePreferences();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @SuppressLint("InflateParams") @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_todo) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setTitle("Add Todo");
            View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
            builder.setView(dialogView);
            final EditText textfield02 = (EditText) dialogView.findViewById(R.id.dialogEditText);
            textfield02.setHint("Todo Description");
            builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String todo = textfield02.getText().toString();
                    if (!todo.isEmpty()) {
//                        todoList.add(todo);
                    	Log.d(TAG, "Adding Todo " + todo + " to cat " + todoCategory + " " + todoCategoryId);
                    	addTodoEntry(new TodoEntry(todoCategoryId, todoCategory, todo));
                    	rereadFromDb();
                        adapter.notifyDataSetChanged();
//                        savePreferences();
                        dialogInterface.dismiss();
                    } else {
                        Toast.makeText(TodoActivity.this, "Todo name not set", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void savePreferences() {
//
//        String todoString = "";
//
//        for (int i = 0; i < todoList.size(); i++) {
//            if (i == 0) {
//                todoString = todoList.get(i);
//            } else {
//                todoString += ";" + todoList.get(i);
//            }
//        }
//
//        preferencesEditor.putString(todoCategory, todoString);
//        preferencesEditor.commit();
//
//    }
    
    public ArrayList<TodoEntry> readTodoEntries(int todoCategoryId){
    	
    	Log.d(TAG, "reading Entries for Category " + todoCategoryId);
    	
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	TodoDatabaseProvider tdp = new TodoDatabaseProvider();
    	Cursor cursor = tdp.readTodoFromDb(mDbHelper, todoCategoryId);
    	ArrayList<TodoEntry> entList = new ArrayList<TodoEntry>();
    	
    	String cat = "";
    	String text = "";
    	String id = "";
    	
    	while(cursor.moveToNext()){
    		
    		cat = cursor.getString(cursor.getColumnIndex(TodoDbEntry.COLUMN_NAME_CATEGORY));
    		text = cursor.getString(cursor.getColumnIndex(TodoDbEntry.COLUMN_NAME_TEXT));
    		id = cursor.getString(cursor.getColumnIndex(TodoDbEntry._ID));
    		Log.d(TAG, "Read entry " + text + " in cat " + cat + " " + todoCategoryId);
    		entList.add(new TodoEntry(id, todoCategoryId, cat, text)); //TODO think about if its evil to use todoCategoryId here
    		
    	}
    	cursor.close();
//    	mDbHelper.close();
    	return entList;
    }
    
    public void deleteTodoEntry(TodoEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	TodoDatabaseProvider tdp = new TodoDatabaseProvider();
    	
    	tdp.deleteTodoFromDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void addTodoEntry(TodoEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	TodoDatabaseProvider tdp = new TodoDatabaseProvider();
    	
    	tdp.writeTodoToDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void rereadFromDb(){
    	todoList = new ArrayList<String>();
        entryList = new ArrayList<TodoEntry>();
        entryList = readTodoEntries(todoCategoryId);
        for (TodoEntry te : entryList){
        	todoList.add(te.getText());
        }
    }
}