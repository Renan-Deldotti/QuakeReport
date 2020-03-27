package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=100&minmagnitude=2&orderby=time";
        EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask();
        earthquakeAsyncTask.execute(urlString);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String,Void, List<Earthquakes>>{

        @Override
        protected List<Earthquakes> doInBackground(String... strings) {
            // Checa se existe uma String para ser convertida em URL
            if (strings.length <1 || strings[0] == null){
                return null;
            }
            // Chama o metodo para pegar o JSON da url
            List<Earthquakes> earthquakes = QueryUtils.extractEarthquakes(strings[0]);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(List<Earthquakes> earthquakes) {
            // Pega a referencia ao ListView "list"
            ListView earthquakeListView = findViewById(R.id.list);
            // Cria o adapter para o ArrayList Earthquake
            ArrayList<Earthquakes> earthquakesArrayList = (ArrayList<Earthquakes>) earthquakes;
            final EarthquakeAdapter adapter = new EarthquakeAdapter(EarthquakeActivity.this,earthquakesArrayList);
            // Adiciona o Adapter ao ListView
            earthquakeListView.setAdapter(adapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(EarthquakeActivity.this,"Abrindo a noticia no navegador...",Toast.LENGTH_LONG).show();
                    Earthquakes currentEarthquakes = adapter.getItem(position);
                /*Uri uri = Uri.parse(currentEarthquakes.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);*/
                    // Jeito mais pratico
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthquakes.getUrl())));
                }
            });
        }
    }
}