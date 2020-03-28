package com.example.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquakes>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    private String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=100&minmagnitude=2&orderby=time";
    EarthquakeAdapter earthquakeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        earthquakeAdapter = new EarthquakeAdapter(this,new ArrayList<Earthquakes>());
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(earthquakeAdapter);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null, this);
        //EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask();
        //earthquakeAsyncTask.execute(urlString);
    }

    @Override
    public Loader<List<Earthquakes>> onCreateLoader(int id, Bundle args) {
        Log.e("Loader","Loader onCreate was called.");
        return new EarthquakeLoader(this,urlString);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquakes>> loader, List<Earthquakes> data) {
        Log.e("Loader","Loader onLoadFinished was called.");
        earthquakeAdapter.clear();
        if (data != null && !data.isEmpty()){
            updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquakes>> loader) {
        Log.e("Loader","Loader onLoaderReset was called.");
        earthquakeAdapter.clear();
    }

    protected void updateUi(List<Earthquakes> earthquakes) {
        // Pega a referencia ao ListView "list"
        ListView earthquakeListView = findViewById(R.id.list);
        // Cria o adapter para o ArrayList Earthquake
        ArrayList<Earthquakes> earthquakesArrayList = (ArrayList<Earthquakes>) earthquakes;
        final EarthquakeAdapter adapter = new EarthquakeAdapter(EarthquakeActivity.this,earthquakesArrayList);
        // Adiciona o Adapter ao ListView
        earthquakeListView.setAdapter(adapter);
        // Adiciona o ClickListener na lista
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EarthquakeActivity.this,"Abrindo a noticia no navegador...",Toast.LENGTH_LONG).show();
                Earthquakes currentEarthquakes = adapter.getItem(position);
                /*Uri uri = Uri.parse(currentEarthquakes.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                Jeito mais pratico*/
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthquakes.getUrl())));
            }
        });
    }
}