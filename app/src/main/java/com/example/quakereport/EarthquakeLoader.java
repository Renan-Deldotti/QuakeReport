package com.example.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class EarthquakeLoader extends AsyncTaskLoader<List<Earthquakes>> {

    private String urlString;

    public EarthquakeLoader(@NonNull Context context, String urlString) {
        super(context);
        this.urlString = urlString;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
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
