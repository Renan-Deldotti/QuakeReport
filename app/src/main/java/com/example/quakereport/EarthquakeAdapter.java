package com.example.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquakes> {

    public EarthquakeAdapter(Context context, ArrayList<Earthquakes> arrayList){
        super(context,0,arrayList);
    }

    /**
     * Cria a view para adicioanr na ListView
     * @param position A posicao a ser adicionada
     * @param convertView A view a ser convertida (reciclada) para gerar a nova view
     * @param parent O ViewGroup pai usado para inflar a View
     * @return A view pronta a ser adicionada
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Verifica se a view esta sendo reusada, caso contrario infla a view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list,parent,false);
        }
        // Pega o objeto (Earthquake) na posicao da lista
        Earthquakes currentEarthquakes = getItem(position);
        // Cria a variavel para receber os TextViews
        TextView useTextView;
        // Recebe o valor da magnitude e seta no TextView
        useTextView = listItemView.findViewById(R.id.magnitude);
        useTextView.setText(currentEarthquakes.getMagnitude());
        // Seta o nome da cidade
        useTextView =  listItemView.findViewById(R.id.cityName);
        useTextView.setText(currentEarthquakes.getLocation());
        // Seta a data
        useTextView = listItemView.findViewById(R.id.date);
        useTextView.setText(currentEarthquakes.getDate());
        // Retorna a view pronta
        return listItemView;
    }
}
