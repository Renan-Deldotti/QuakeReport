package com.example.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            Preference minMag = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMag);
        }

        private void bindPreferenceSummaryToValue(Preference minMag) {
            minMag.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(minMag.getContext());
            String prefString = preferences.getString(minMag.getKey(),"");
            onPreferenceChange(minMag,prefString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            //Toast.makeText(this, "Preference " + preference.toString() + "changed", Toast.LENGTH_LONG);
            preference.setSummary(newValue.toString());
            return true;
        }
    }
}
