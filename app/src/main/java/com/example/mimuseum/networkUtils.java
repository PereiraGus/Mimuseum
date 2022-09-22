package com.example.mimuseum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

public class networkUtils {
    private static final String URL_API = "https://earlygreenglass47.conveyor.cloud/api/Arte";
    private static HttpURLConnection connec;
    private static BufferedReader reader;
    private static String artJSON;
    static String url = null;

    static HttpURLConnection startConnec(String methName, String parName, String parVal, String httpMeth) {
        HttpURLConnection connec = null;
        try
        {
            Uri buildURI = Uri.parse(URL_API).buildUpon()
                    .appendPath(methName)
                    .build();
            if(parName != null)
            {
                buildURI = Uri.parse(String.valueOf(buildURI)).buildUpon()
                        .appendQueryParameter(parName, parVal)
                        .build();
            }
            URL requestUrl = new URL(buildURI.toString());
            url = buildURI.toString();
            connec = (HttpURLConnection) requestUrl.openConnection();
            connec.setRequestMethod(httpMeth);
        }
        catch (Exception ex) {
        }
        return connec;
    }
    static int countArts(){
        int arts = 0;
        try {
            connec = networkUtils.startConnec("seeNumOfArts", null, null, "GET");
            InputStream inputStream = connec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
            arts = Integer.parseInt(builder.toString());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            connec.disconnect();
        }
        return arts;
    }

    static Bundle getRandomArt() {
        Bundle artBundle = new Bundle();

        Random generator = new Random();
        int randomID = generator.nextInt((networkUtils.countArts() - 1)+1)+1;

        try {
            connec = networkUtils.startConnec("getArtByID","id",Integer.toString(randomID),"GET");
            InputStream inputStream = connec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
                builder.append("/n");
            }
            if (builder.length() == 0)
            {
                return null;
            }
            artJSON = builder.toString();
        }
        catch (IOException ex) {
                ex.printStackTrace();
        }
        finally {
            connec.disconnect();
        }
        artBundle.putString("artData", artJSON);
        return artBundle;
    }

    static void addArt(Art art){
        try{
            connec = networkUtils.startConnec("PostNewArt",null,null,"POST");
            connec.setDoOutput(true);

            OutputStream outputStream = connec.getOutputStream();
            outputStream.write(Byte.valueOf(art.toString()));
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connec.disconnect();
        }
    }

    static void updateInfo(Art art){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String atracoesrJSONString = null;



        try {
            Uri builtURI;
            if(art == null){
                builtURI = Uri.parse(URL_API).buildUpon()
                        .build();
            }
            else {
                String url1 = URL_API;
                //Construção da URI de Busca
                builtURI = Uri.parse(url1).buildUpon()
                        .build();


            }

            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            JsonReader jsonReader = new JsonReader(inputStreamReader);
            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String url = jsonReader.nextName(); // Fetch the next key
                if (url.equals("URL_API")) { // Check if desired key
                    Gson gson = new Gson();
                    String atracoesUpdate = gson.toJson(art);
                    // Fetch the value as a String
                    atracoesUpdate = jsonReader.nextString();


                    break; // Break out of the loop
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("SET");
            urlConnection.connect();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void deleteInfo(String arts){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String atracoesJSONString = null;



        try {
            Uri builtURI;
            if(arts == null){
                builtURI = Uri.parse(URL_API).buildUpon()
                        .build();
            }
            else {
                String url1 = URL_API;
                //Construção da URI de Busca
                builtURI = Uri.parse(url1).buildUpon()
                        .build();


            }

            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, "UTF-8");

            JsonReader jsonReader = new JsonReader(inputStreamReader);
            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String url = jsonReader.nextName(); // Fetch the next key
                if (url.equals("URL_API")) { // Check if desired key
                    Gson gson = new Gson();
                    String atracoesUpdate = gson.toJson(arts);
                    // Fetch the value as a String
                    atracoesUpdate = jsonReader.nextString();

                    // Do something with the value
                    // ...

                    break; // Break out of the loop
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.connect();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}