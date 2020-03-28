package com.example.quakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

class EarthquakeLoader extends AsyncTaskLoader<List<Earthquakes>> {

    public EarthquakeLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<Earthquakes> loadInBackground() {
        return null;
    }
}
