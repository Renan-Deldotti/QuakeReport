package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Lista de locais baseados no Json
        ArrayList<Earthquakes> earthquakes = QueryUtils.extractEarthquakes();

        // Pega a referencia ao ListView "list"
        ListView earthquakeListView = findViewById(R.id.list);

        // Cria o adapter para o ArrayList Earthquake
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes);

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