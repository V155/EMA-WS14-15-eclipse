package de.hsworms.fritz.ema.aufgabe03;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
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
import de.hsworms.fritz.ema.aufgabe03.TodoContract.CategoryDbEntry;

public class Aufgabe03 extends ListActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    public static final String KEY_OF_CATEGORIES_LIST = "Kategorien";
    private ArrayList<CatListItem> catList;
    private ArrayList<CategoryEntry> entryList;
//    private ArrayList<String> categoryList;
    private CatListAdapter catListAdapter;
    public static final String KEY_EXTRA_CATEGORY_NAME = "catname";
    private static final String TAG = "Aufgabe03";
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        catList = new ArrayList<CatListItem>();
//
//        preferences = this.getSharedPreferences(getString(R.string.categoryListPreferencesKey), Context.MODE_PRIVATE);
//        preferencesEditor = preferences.edit();
//        preferencesEditor.putString(KEY_OF_CATEGORIES_LIST, "Einkaufen;Kategorie2;Kategorie3;Kategorie4");
//        preferencesEditor.commit();
//        String kategorien = preferences.getString(KEY_OF_CATEGORIES_LIST, "");
//        String[] categories = kategorien.split(";");
//        preferencesEditor.putString(categories[0], "Brot; Toast; Würstchen; Bier");
//        preferencesEditor.commit();
        

//        for (String cat : categoryList){
//
//            String entries = preferences.getString(cat, "");
//            int numOfEntries = 0;
//
//            if (!entries.isEmpty()) {
//                numOfEntries = entries.split(";").length;
//            }
//
//            catList.add(new CatListItem(cat, numOfEntries));
//        }

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
                	deleteCategoryEntry(entryList.get(position));
//                    catList.remove(position);
                	rereadFromDb();
                    catListAdapter.notifyDataSetChanged();
//                    savePreferences();
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
//                        catList.add(new CatListItem(cat, 0));
                        catListAdapter.notifyDataSetChanged();
//                        savePreferences();
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



//    public void savePreferences(){
//
//        String categoriesString = "";
//
//        for (int i = 0; i < catList.size(); i++){
//            if(i == 0){
//                categoriesString = catList.get(i).getName();
//            } else {
//                categoriesString += ";"+catList.get(i).getName();
//            }
//        }
//        preferencesEditor = preferences.edit();
//        preferencesEditor.putString(KEY_OF_CATEGORIES_LIST, categoriesString);
//        preferencesEditor.commit();
//
//    }
    
public ArrayList<CategoryEntry> readCategoryEntries(){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	TodoDatabaseProvider tdp = new TodoDatabaseProvider();
    	Cursor cursor = tdp.readCategoryFromDb(mDbHelper);
    	ArrayList<CategoryEntry> entList = new ArrayList<CategoryEntry>();
    	
    	String cat = "";
    	String id = "";
    	
    	while(cursor.moveToNext()){
    		
    		cat = cursor.getString(cursor.getColumnIndex(CategoryDbEntry.COLUMN_NAME_NAME));
    		int i = cursor.getColumnIndex(CategoryDbEntry._ID);
    		if (i != -1) {
    			id = cursor.getString(i);
    		} else {
    			id = "" + 0;
    		}
    		Log.d(TAG, "Read category " + id + " " + cat);
    		entList.add(new CategoryEntry(id, cat));
    		
    	}
    	cursor.close();
    	//mDbHelper.close();
    	return entList;
    }
    
    public void deleteCategoryEntry(CategoryEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	TodoDatabaseProvider tdp = new TodoDatabaseProvider();
    	
    	tdp.deleteCategoryFromDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void addCategoryEntry(CategoryEntry entry){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
    	TodoDatabaseProvider tdp = new TodoDatabaseProvider();
    	
    	tdp.writeCategoryToDb(mDbHelper, entry);
    	
    	mDbHelper.close();
    }
    
    public void rereadFromDb(){
//    	categoryList = new ArrayList<String>();
    	catList = new ArrayList<CatListItem>();
        entryList = new ArrayList<CategoryEntry>();
        entryList = readCategoryEntries();
        catListAdapter = new CatListAdapter(this, R.layout.category_list_item, catList);
//        for (CategoryEntry ce : entryList){
//        	categoryList.add(ce.getCategoryName());
//        }
        for (CategoryEntry catE : entryList){
        	int numOfEntries = getNumOfEntries(catE.getId());
        	catList.add(new CatListItem(catE.getCategoryName(), numOfEntries));
        }
        Log.d(TAG, "Number of TodoCategories: " + entryList.size());
    }
    
    public int getNumOfEntries(String catId){
    	
    	TodoDbHelper mDbHelper = new TodoDbHelper(getApplicationContext());
     	TodoDatabaseProvider tdp = new TodoDatabaseProvider();
        int numOfEntries = tdp.getNumOfEntriesInCategory(mDbHelper, Integer.parseInt(catId));
        mDbHelper.close();
        return numOfEntries;
    	
    }
    
    
    
    
}
