package de.hsworms.fritz.ema.aufgabe03;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import de.hsworms.fritz.ema.R;

public class
        TodoActivity extends ListActivity {

    private ArrayList<String> todoList = new ArrayList<String>();
    private ArrayList<TodoEntry> entryList = new ArrayList<TodoEntry>();
    private String todoCategory;
    private int todoCategoryId;
    private Intent intent;
    private ArrayAdapter<String> adapter;
    private static String TAG = "TodoActivity";
    private TodoDatabaseProvider tdp;
    private ShareActionProvider mShareActionProvider;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_todo);
        intent = getIntent();
        todoCategoryId = Integer.parseInt(intent.getStringExtra(Aufgabe03.KEY_EXTRA_CATEGORY_NAME).split(";")[0]);
        todoCategory = intent.getStringExtra(Aufgabe03.KEY_EXTRA_CATEGORY_NAME).split(";")[1];
        this.setTitle(todoCategory);
        tdp = new TodoDatabaseProvider();
        rereadFromDb();

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
//                    	entryList.remove(position);
//                        todoList.remove(position);
                    	rereadFromDb();
                        adapter.notifyDataSetChanged();
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
    	
//    	Intent sendIntent = new Intent();
//    	sendIntent.setAction(Intent.ACTION_SEND);
//    	sendIntent.putExtra(Intent.EXTRA_STREAM, "file://export.jpeg");
//    	sendIntent.setType("image/jpeg");
////    	startActivity(sendIntent);    	
                
     // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.todo, menu);

//        // Locate MenuItem with ShareActionProvider
//        MenuItem item = menu.findItem(R.id.menu_item_share);
//
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
//        mShareActionProvider.setShareIntent(sendIntent);

        // Return true to display menu
        return true;
    }
    
// // Call to update the share intent
//    private void setShareIntent(Intent shareIntent) {
//        if (mShareActionProvider != null) {
//            mShareActionProvider.setShareIntent(shareIntent);
//        }
//    }

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
                    	Log.d(TAG, "Adding Todo " + todo + " to cat " + todoCategory + " " + todoCategoryId);
                    	addTodoEntry(new TodoEntry(todoCategoryId, todoCategory, todo));
                    	rereadFromDb();
                        adapter.notifyDataSetChanged();
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
        } else if(id == R.id.menu_item_share){
        	serializeCategory();
        	Log.d(TAG, "serialized category");
        	
        }

        return super.onOptionsItemSelected(item);
    }


    
    public void deleteTodoEntry(TodoEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	
    	tdp.deleteTodoFromDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void addTodoEntry(TodoEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	
    	tdp.writeTodoToDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void rereadFromDb(){
    	todoList.clear();
        entryList.clear();
        entryList.addAll(tdp.readTodoEntries(todoCategoryId, getApplicationContext()));
        for (TodoEntry te : entryList){
        	todoList.add(te.getText());
        }
    }
    
    public Object serializeCategory(){
    	
    	try {
    		FileOutputStream fos = openFileOutput("export.jpeg", Context.MODE_PRIVATE);
    		ObjectOutputStream out = new ObjectOutputStream(fos);
    		out.writeObject(entryList);
    		out.close();
    		fos.close();
    	} catch(FileNotFoundException e){
    		
    	} catch(IOException f){
    	
    	}
    	
    	return new Object();
    }
}