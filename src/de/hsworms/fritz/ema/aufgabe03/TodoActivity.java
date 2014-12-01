package de.hsworms.fritz.ema.aufgabe03;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import de.hsworms.fritz.ema.R;

public class
        TodoActivity extends ListActivity {

    private ArrayList<String> todoList;
    private String todoCategory;
    private Intent intent;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_todo);
        todoList = new ArrayList<String>();
        intent = getIntent();
        todoCategory = intent.getStringExtra(Aufgabe03.KEY_EXTRA_CATEGORY_NAME);
        this.setTitle(todoCategory);
        preferences = this.getSharedPreferences(getString(R.string.categoryListPreferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        preferences = this.getSharedPreferences(getString(R.string.categoryListPreferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        String entriesString = preferences.getString(todoCategory, "");

        if (!entriesString.isEmpty()) {
            String[] entries = entriesString.split(";");
            for (String entry : entries) {
                todoList.add(entry);
            }
        }

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
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();
                        savePreferences();
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
            builder.setTitle("Add Category");
            View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
            builder.setView(dialogView);
            final EditText textfield02 = (EditText) dialogView.findViewById(R.id.dialogEditText);
            textfield02.setHint("Category Name");
            builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String todo = textfield02.getText().toString();
                    if (!todo.isEmpty()) {
                        todoList.add(todo);


                        adapter.notifyDataSetChanged();
                        savePreferences();
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

    public void savePreferences() {

        String todoString = "";

        for (int i = 0; i < todoList.size(); i++) {
            if (i == 0) {
                todoString = todoList.get(i);
            } else {
                todoString += ";" + todoList.get(i);
            }
        }

        preferencesEditor.putString(todoCategory, todoString);
        preferencesEditor.commit();

    }
}