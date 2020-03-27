package com.example.quakereport;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class QueryUtils {

    //Bloqueia a criacao de objetos QueryUtils
    private QueryUtils(){}

    static List<Earthquakes> extractEarthquakes(String stringUrl) {
        // Cria a conexao com a Url
        URL url = createUrl(stringUrl);

        // Faz a requisicao HTTP e recebe o arquivo JSON
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(QueryUtils.class.getSimpleName(),"Erro ao fazer a requisicao HTTP");
        }
        // Cria um array List para adicionar os objetos "Earthquakes"
        List<Earthquakes> arrayList = extractDataFromJson(jsonResponse);
        // Retorna o arrayList pronto
        return arrayList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        // Tenta criar a URL
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(QueryUtils.class.getSimpleName(),"Erro criando a Url");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // Verifica se a URL e nula
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        // Tenta criar a conexao para receber os arquivos JSON
        try {
            // Prepara a conexao
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            // Faz a conexao com a Url
            httpURLConnection.connect();
            // Verifica se a conexao foi bem sucedida (codigo 200)
            if (httpURLConnection.getResponseCode() == 200){
                // Pega os dados de entrada no httpUrlConection
                inputStream = httpURLConnection.getInputStream();
                // Le os dados de entrada e coloca na string jsonResponse
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(QueryUtils.class.getSimpleName(),"Erro codigo: "+httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(QueryUtils.class.getSimpleName(),"Erro ao abrir conexao.");
        } finally {
            // Apos as conexoes fecha as conexoes abertas
            // independente de erros
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream){
        // Cria uma nova SringBuilder
        StringBuilder stringBuilder = new StringBuilder();
        // Verifica se a entrada de dados e nula
        if (inputStream != null){
            // Le os dados da entrada
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // Le linha por linha do bufferedReader
            try {
                String line = bufferedReader.readLine();
                // Enquanto tiver linhas adiciona a linha o fm da string line
                while (line != null){
                    stringBuilder.append(line);
                    // Le uma nova linha
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                Log.e(QueryUtils.class.getSimpleName(),"Nao ha linhas para serem lidas. Erro: "+e);
            }
        }
        return stringBuilder.toString();
    }

    private static List<Earthquakes> extractDataFromJson(String jsonResponse) {
        // Verifica se o JSON esta vazio
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        //Cria um array List para adicionar os objetos "Earthquakes"
        List<Earthquakes> arrayList = new ArrayList<>();
        //Cria o block Try Catch para pegar excecoes do tipo "JSONException"
        try {
            //Faz a conversao dos elementos do array
            JSONObject responseJson = new JSONObject(jsonResponse);
            JSONArray featuresArray = responseJson.getJSONArray("features");
            if (featuresArray.length() > 0){
                for(int i = 0; i<featuresArray.length();i++){
                    responseJson = featuresArray.getJSONObject(i);
                    responseJson = responseJson.getJSONObject("properties");
                    arrayList.add(new Earthquakes(responseJson.getDouble("mag"),responseJson.getString("place"),responseJson.getLong("time"),responseJson.getString("url")));
                }
            }
        } catch (JSONException e){
            //Lanca a excecao de erro
            Log.e(QueryUtils.class.getSimpleName(),"Falha ao converter os resultados do Json",e);
        }
        //Retorna o ArrayList pronto
        return arrayList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Convert Json to ArrayList<Earthquakes>";
    }
}
