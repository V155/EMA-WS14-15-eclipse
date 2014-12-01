package de.hsworms.fritz.ema;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.hsworms.fritz.ema.Aufgabe3b.Taschenrechner_Activity;
import de.hsworms.fritz.ema.aufgabe01.Aufgabe01;
import de.hsworms.fritz.ema.aufgabe02.Aufgabe02;
import de.hsworms.fritz.ema.aufgabe03.Aufgabe03;
import de.hsworms.fritz.ema.wetterstation.Wetterstation_Activity;


public class Main extends ListActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        String[] entriesNames = getResources().getStringArray(R.array.main_list_entries_names);
        ArrayAdapter<String> mainListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entriesNames);
        this.setListAdapter(mainListAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                intent = new Intent(Main.this, Aufgabe01.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(Main.this, Aufgabe02.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(Main.this, Aufgabe03.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(Main.this, Taschenrechner_Activity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(Main.this, Wetterstation_Activity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
