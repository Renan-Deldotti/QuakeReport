package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquakes> {

    private static final String LOCATION_SEPARATOR = " of ";

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
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        useTextView = listItemView.findViewById(R.id.magnitude);
        useTextView.setText(decimalFormat.format(currentEarthquakes.getMagnitude()));
        // Muda a cor de fundo do icone de acordo com o grau do terremoto
        changeBackgroundIconColor(useTextView,currentEarthquakes.getMagnitude());
        //Divide a String de getLocation
        String[] textToShow;
        if (currentEarthquakes.getLocation().contains(LOCATION_SEPARATOR)){
            textToShow = currentEarthquakes.getLocation().split(LOCATION_SEPARATOR);
            textToShow[0] += LOCATION_SEPARATOR;
        }else {
            textToShow = new String[]{getContext().getString(R.string.near_the), currentEarthquakes.getLocation()};
        }
        // Seta a proximidade
        useTextView =  listItemView.findViewById(R.id.nearby);
        useTextView.setText(textToShow[0]);
        // Seta o nome da cidade
        useTextView =  listItemView.findViewById(R.id.cityName);
        useTextView.setText(textToShow[1]);
        // Seta a data
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate =  simpleDateFormat.format(new Date(currentEarthquakes.getDate()));
        useTextView = listItemView.findViewById(R.id.date);
        useTextView.setText(formattedDate);
        // Seta a hora
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        formattedDate = simpleDateFormat.format(new Date(currentEarthquakes.getDate()));
        useTextView = listItemView.findViewById(R.id.time);
        useTextView.setText(formattedDate);
        // Retorna a view pronta
        return listItemView;
    }
    private void changeBackgroundIconColor(TextView view,Double d){
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        switch ((int) Math.floor(d)){
        //switch ((int) Math.round(d)){
            case 0:
            case 1:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude1));
                break;
            case 2:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude2));
                break;
            case 3:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude3));
                break;
            case 4:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude4));
                break;
            case 5:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude5));
                break;
            case 6:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude6));
                break;
            case 7:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude7));
                break;
            case 8:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude8));
                break;
            case 9:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude9));
                break;
            default:
                gradientDrawable.setColor(ContextCompat.getColor(getContext(),R.color.magnitude10plus));
                break;
        }
    }
}
