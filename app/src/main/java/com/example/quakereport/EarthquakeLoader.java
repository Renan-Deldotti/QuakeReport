package com.example.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class EarthquakeLoader extends AsyncTaskLoader<List<Earthquakes>> {

    final String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=100&minmagnitude=2&orderby=time";
    public EarthquakeLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<Earthquakes> loadInBackground() {
        // Checa se existe uma String para ser convertida em URL
        if (TextUtils.isEmpty(urlString)){
            return null;
        }
        // Chama o metodo para pegar o JSON da url
        List<Earthquakes> earthquakes = QueryUtils.extractEarthquakes(urlString);
        return earthquakes;
    }
}
