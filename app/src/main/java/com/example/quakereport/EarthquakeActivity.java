package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes);

        // Adiciona o Adapter ao ListView
        earthquakeListView.setAdapter(adapter);
    }
}
