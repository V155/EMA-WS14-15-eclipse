package de.hsworms.fritz.ema.taschenrechner;

import de.hsworms.fritz.ema.R;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Button;
import android.widget.TextView;

public class Taschenrechner_Activity extends Activity {

    private String a = "";
    private String b = "";
    private char operator;
    private boolean operator_pressed = false;
    private boolean period_pressed = false;

    private TextView res_view;

//    private Button but_zero;
//    private Button but_period;
//    private Button but_equals;
//    private Button but_minus;
//
//    private Button but_one;
//    private Button but_two;
//    private Button but_three;
//    private Button but_plus;
//
//    private Button but_four;
//    private Button but_five;
//    private Button but_six;
//    private Button but_div;
//
//    private Button but_seven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taschenrechner_);
        Typeface defaultTypeface = Typeface.createFromAsset(getAssets(), "digital-7.ttf");
        res_view = (TextView) findViewById(R.id.res_view);
        res_view.setTypeface(defaultTypeface, Typeface.NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.taschenrechner_, menu);
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

    public void times_pressed(View view) {

        if (!operator_pressed) {
            operator = '*';
            operator_pressed = true;
            refresh_display();
        }

    }

    public void minus_pressed(View view) {

        if (!operator_pressed) {
            operator = '-';
            operator_pressed = true;
            refresh_display();
        }

    }

    public void plus_pressed(View view) {

        if (!operator_pressed) {
            operator = '+';
            operator_pressed = true;
            refresh_display();
        }
    }

    public void div_pressed(View view) {

        if (!operator_pressed) {
            operator = '/';
            operator_pressed = true;
            refresh_display();
        }
    }

    public void equals_pressed(View view) {

        if(operator_pressed && b.length() > 0){
            double da = Double.parseDouble(a);
            double db = Double.parseDouble(b);
            double result = 0;

            switch(operator){
                case '+':
                    result = da + db;
                    res_view.setText( "" + result);
                    reset_vars();
                    a = "" + result;
                    break;
                case '-':
                    result = da - db;
                    res_view.setText( "" + result);
                    reset_vars();
                    a = "" + result;
                    break;
                case '*':
                    result = da * db;
                    res_view.setText( "" + result);
                    reset_vars();
                    a = "" + result;
                    break;
                case '/':
                    if (db == 0){
                        res_view.setText("ERROR: div by zero");
                        reset_vars();
                    } else {
                        result = da / db;
                        res_view.setText( "" + result);
                        reset_vars();
                        a = "" + result;
                    }
                    break;
            }
        }
    }

    private void reset_vars() {
        a = "";
        b = "";
        operator = 0;
        operator_pressed = false;
        period_pressed = false;
    }

    public void period_pressed(View view) {
        if (!period_pressed){
            if(operator_pressed){
                b = b + ".";
                period_pressed = true;
                refresh_display();
            } else {
                a = a + ".";
                period_pressed = true;
                refresh_display();
            }
        }
    }

    public void zero_pressed(View view) {
        if(operator_pressed){
            b = b  + "0";
        } else {
            a = a + "0";
        }

        refresh_display();
    }



    public void three_pressed(View view) {
        if(operator_pressed){
            b = b  + "3";
        } else {
            a = a + "3";
        }

        refresh_display();
    }

    public void two_pressed(View view) {
        if(operator_pressed){
            b = b  + "2";
        } else {
            a = a + "2";
        }

        refresh_display();
    }

    public void one_pressed(View view) {
        if(operator_pressed){
            b = b  + "1";
        } else {
            a = a + "1";
        }

        refresh_display();
    }

    private void refresh_display() {
        res_view.setText(a + operator + b);
    }

    public void four_pressed(View view) {
        if(operator_pressed){
            b = b  + "4";
        } else {
            a = a + "4";
        }

        refresh_display();
    }

    public void five_pressed(View view) {
        if(operator_pressed){
            b = b  + "5";
        } else {
            a = a + "5";
        }

        refresh_display();
    }

    public void six_pressed(View view) {
        if(operator_pressed){
            b = b  + "6";
        } else {
            a = a + "6";
        }

        refresh_display();
    }


    public void seven_pressed(View view) {
        if(operator_pressed){
            b = b  + "7";
        } else {
            a = a + "7";
        }

        refresh_display();
    }

    public void eight_pressed(View view) {
        if(operator_pressed){
            b = b  + "8";
        } else {
            a = a + "8";
        }

        refresh_display();
    }

    public void nine_pressed(View view) {
        if(operator_pressed){
            b = b  + "9";
        } else {
            a = a + "9";
        }

        refresh_display();
    }


    public void ac_pressed(View view) {

        reset_vars();
        refresh_display();

    }
}
