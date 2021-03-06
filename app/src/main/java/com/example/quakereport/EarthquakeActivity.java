package com.example.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquakes>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    // Close earthquakes
    //private String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=100&minmagnitude=2&latitude=-15.807825&longitude=-48.049678&maxradius=30&orderby=time";
    private static final String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    EarthquakeAdapter earthquakeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        earthquakeAdapter = new EarthquakeAdapter(this,new ArrayList<Earthquakes>());
        ListView listView = findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.textViewEmpty));
        listView.setAdapter(earthquakeAdapter);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            Log.e("Loader", "initLoader was called.");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
            //EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask();
            //earthquakeAsyncTask.execute(urlString);
        }else {
            ProgressBar progressBar = findViewById(R.id.progress_circular);
            progressBar.setVisibility(View.GONE);
            TextView textView = findViewById(R.id.textViewEmpty);
            textView.setText("NO INTERNET CONNECTION");
        }
    }

    @Override
    public Loader<List<Earthquakes>> onCreateLoader(int id, Bundle args) {
        Log.e("Loader","Loader onCreate was called.");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String min_magnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default)
        );
        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        Uri uri = Uri.parse(urlString);
        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder.appendQueryParameter("format","geojson")
                .appendQueryParameter("limit","100")
                .appendQueryParameter("minmagnitude",min_magnitude)
                .appendQueryParameter("orderby",orderBy);
        //Toast.makeText(this,"URI: "+uriBuilder.toString(),Toast.LENGTH_LONG).show();
        return new EarthquakeLoader(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquakes>> loader, List<Earthquakes> data) {
        Log.e("Loader","Loader onLoadFinished was called.");
        ProgressBar progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.GONE);
        TextView emptyView = findViewById(R.id.textViewEmpty);
        emptyView.setText("No earthquake found.");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}