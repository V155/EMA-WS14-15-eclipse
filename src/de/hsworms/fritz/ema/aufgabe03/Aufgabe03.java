package de.hsworms.fritz.ema.aufgabe03;

import java.io.File;
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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import de.hsworms.fritz.ema.R;

public class Aufgabe03 extends ListActivity {

//    public static final String KEY_OF_CATEGORIES_LIST = "Kategorien";
    private ArrayList<CatListItem> catList = new ArrayList<CatListItem>();
    private ArrayList<CategoryEntry> entryList = new ArrayList<CategoryEntry>();
    private CatListAdapter catListAdapter;
    public static final String KEY_EXTRA_CATEGORY_NAME = "catname";
    private static final String TAG = "Aufgabe03";
    private TodoDatabaseProvider tdp;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tdp = new TodoDatabaseProvider();
        rereadFromDb();
        
        catListAdapter = new CatListAdapter(this, R.layout.category_list_item, catList);
        this.setListAdapter(catListAdapter);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Aufgabe03.this);
            builder.setTitle("Remove Category");
            builder.setMessage("Do you really want to remove this category?");

            builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                	deleteCategoryEntry(entryList.get(position));
//                	rereadFromDb();
//                    catListAdapter.notifyDataSetChanged();
                	
                	Uri uri = serializeCategory(position);
                	
                	Intent shareIntent = new Intent();
                	shareIntent.setAction(Intent.ACTION_SEND);
                	shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                	shareIntent.setType("image/jpeg");
                	shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                	shareIntent.setData(uri);
                	startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                	
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
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(Aufgabe03.this, TodoActivity.class);
        intent.putExtra(KEY_EXTRA_CATEGORY_NAME, entryList.get(position).getId() + ";" + entryList.get(position).getCategoryName());
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.aufgabe03, menu);
        return true;
    }

    @SuppressLint("InflateParams") @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_category) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Aufgabe03.this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setTitle("Add Category");
            View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
            builder.setView(dialogView);
            final EditText textfield02 = (EditText) dialogView.findViewById(R.id.dialogEditText);
            textfield02.setHint("Category Name");
            builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String cat = textfield02.getText().toString();
                    if (!cat.isEmpty()) {
                    	addCategoryEntry(new CategoryEntry(cat));
                    	rereadFromDb();
                        catListAdapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    } else {
                        Toast.makeText(Aufgabe03.this, "Category name not set", Toast.LENGTH_SHORT).show();
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

    
    
    public void deleteCategoryEntry(CategoryEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	    	
    	tdp.deleteCategoryFromDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void addCategoryEntry(CategoryEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	    	
    	tdp.writeCategoryToDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void rereadFromDb(){
    	
    	catList.clear();
        entryList.clear();
        entryList.addAll(tdp.readCategoryEntries(getApplicationContext()));
        
        for (CategoryEntry catE : entryList){
        	int numOfEntries = getNumOfTodoEntries(catE.getId());
        	catList.add(new CatListItem(catE.getCategoryName(), numOfEntries));
        }
        Log.d(TAG, "Number of TodoCategories: " + entryList.size());
//        Log.d(TAG, entryList.get(1).toString(getApplicationContext()));
    }
    
    public int getNumOfTodoEntries(String catId){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
     	int numOfEntries = tdp.getNumOfEntriesInCategory(mDbHelper, Integer.parseInt(catId));
        mDbHelper.close();
        return numOfEntries;
    }
    
public Uri serializeCategory(int todoCategoryId){
	
		ArrayList<TodoEntry> tel = tdp.readTodoEntries(todoCategoryId, getApplicationContext());
		ArrayList<String> todoEntries = new ArrayList<String>(); 
		for (TodoEntry te : tel){
			todoEntries.add(te.getText());		
		}
		CategoryExport ce = new CategoryExport(entryList.get(todoCategoryId).getCategoryName(), todoEntries);
		File exportPath = new File(getApplicationContext().getFilesDir(), "export");
		File file = new File(exportPath, "export.jpeg");
		Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "de.hsworms.fritz.ema.fileprovider", file);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(ce);
			out.close();
			fos.close();
		} catch(FileNotFoundException e){
    		
			Log.e(TAG, e.toString());
    		
		} catch(IOException e){
    		
			Log.e(TAG, e.toString());
    	
		}
		return contentUri;
    	
    }
}
