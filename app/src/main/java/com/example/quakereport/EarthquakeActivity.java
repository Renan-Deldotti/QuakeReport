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

        //Lista de locais fakes
        final ArrayList<Earthquakes> earthquakes1 = new ArrayList<>();
        earthquakes1.add(new Earthquakes("4.5","San Francisco","Today"));
        earthquakes1.add(new Earthquakes("3.0","London","Yesterday"));
        earthquakes1.add(new Earthquakes("3.0","Tokyo","Yesterday"));
        earthquakes1.add(new Earthquakes("1.5","Mexico City","20-03-19"));
        earthquakes1.add(new Earthquakes("2.7","Moscow","09-08-18"));
        earthquakes1.add(new Earthquakes("1.3","Rio de Janeiro","05-04-18"));
        earthquakes1.add(new Earthquakes("5.2","Paris","19-03-18"));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        //ArrayAdapter<Earthquakes> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,earthquakes1);
        EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes1);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

    }
}
