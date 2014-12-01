package de.hsworms.fritz.ema.wetterstation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import de.hsworms.fritz.ema.R;

//import de.hsworms.fritz.ema.R;

public class Wetterstation_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wetterstation_);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.wetterstation_, menu);
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


    public void refresh(View view) {

        //String csv = "20141128215406;baro_rs;1011.2099179;4.8;4.8;4.8;54;NE;85;0;no;0;0;0;59.436;59.436;17;52";
        new BackgroundJob().execute();


    }

    private class BackgroundJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = "http://wetter2.mt-labor.it.fh-worms.de/api/csv";
            URL url;
            String csv = "";
            try {
                url = new URL(urlString);
                URLConnection conn = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                if ((inputLine = br.readLine()) != null){
                    csv += inputLine;
                }
                br.close();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return csv;

        }

        @Override
        protected void onPostExecute(String csv){

            weatherData data = new weatherData();
            data.parseCsv(csv);

            TextView tsView = (TextView) findViewById(R.id.tsView);
            TextView btView = (TextView) findViewById(R.id.btView);
            TextView tempView = (TextView) findViewById(R.id.tempView);
            TextView wasView = (TextView) findViewById(R.id.wasView);
            TextView humView = (TextView) findViewById(R.id.humView);

            tsView.setText("Time: " + data.getTimestamp());
            btView.setText("BaroTendency: " + data.getBaroTendency());
            tempView.setText("Temperature: " + data.getTemperature() + " C");
            wasView.setText("WindAvgSpeed: " + data.getWindAverageSpeed() + " km/h");
            humView.setText("Humidity: " + data.getHumidity() + " %");
        }
    }
}
