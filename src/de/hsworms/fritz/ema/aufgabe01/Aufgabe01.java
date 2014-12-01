package de.hsworms.fritz.ema.aufgabe01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import de.hsworms.fritz.ema.R;

//import de.hsworms.fritz.ema.R;

public class Aufgabe01 extends Activity {

    private TextView textfield01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe01);
        textfield01 = (TextView) findViewById(R.id.tf01);
        textfield01.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("InflateParams") @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Aufgabe01.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setTitle("Edit Text");
                View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
                builder.setView(dialogView);
                final EditText textfield02 = (EditText) dialogView.findViewById(R.id.dialogEditText);
                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textfield01.setText(textfield02.getText());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();

            }
        });
        NumberPicker numPick01 = (NumberPicker) findViewById(R.id.numberPicker01);
        numPick01.setMinValue(8);
        numPick01.setMaxValue(24);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.aufgabe01, menu);
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

    public void showText(View v){
        //Intent intent = new Intent(this, TestActivity.class);

        Button button01 = (Button) findViewById(R.id.button01);
        if (textfield01.getVisibility() == View.INVISIBLE){
            textfield01.setVisibility(View.VISIBLE);
            button01.setText("Hide Text");
        } else {
            textfield01.setVisibility(View.INVISIBLE);
            button01.setText("Show Text");
        }

    }

    public void applySize(View v){
        TextView textfield01 = (TextView) findViewById(R.id.tf01);
        NumberPicker numPick01 = (NumberPicker) findViewById(R.id.numberPicker01);

        textfield01.setTextSize(numPick01.getValue());
        // textfield01.setVisibility(TextView.VISIBLE);



    }
}
