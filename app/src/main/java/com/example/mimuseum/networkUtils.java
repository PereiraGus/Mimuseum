package com.example.mimuseum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    }/*
    static Bundle accessArtByID(int id) {
        Bundle artInfo = new Bundle();
        String arts = "'arts': [\n" +
                "    {\n" +
                "        \"IDArte\": 1,\n" +
                "        \"NomeArte\": \"A Grande Onda de Kanagawa\",\n" +
                "        \"NomeArtista\": \"Katsushika Hokusai\",\n" +
                "        \"AnoArte\": 1831,\n" +
                "        \"EstiloArte\": \"Gravura\",\n" +
                "        \"UrlArte\": \"https://artsandculture.google.com/asset/the-great-wave-off-the-coast-of-kanagawa/fAFp7yddSAtcTQ?hl=pt-BR\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"IDArte\": 2,\n" +
                "        \"NomeArte\": \"Mona Lisa\",\n" +
                "        \"NomeArtista\": \"Leonardo da Vinci\",\n" +
                "        \"AnoArte\": 1530,\n" +
                "        \"EstiloArte\": \"Pintura a óleo\",\n" +
                "        \"UrlArte\": \"https://cdn.pariscityvision.com/library/image/5449.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"IDArte\": 3,\n" +
                "        \"NomeArte\": \"A Noite Estrelada\",\n" +
                "        \"NomeArtista\": \"Vincent Van Gogh\",\n" +
                "        \"AnoArte\": 1889,\n" +
                "        \"EstiloArte\": \"Pintura a óleo\",\n" +
                "        \"UrlArte\": \"https://artsandculture.google.com/asset/the-starry-night/bgEuwDxel93-Pg?hl=pt-BR\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"IDArte\": 4,\n" +
                "        \"NomeArte\": \"A Persistência da Memória\",\n" +
                "        \"NomeArtista\": \"Salvador Dali\",\n" +
                "        \"AnoArte\": 1931,\n" +
                "        \"EstiloArte\": \"Pintura a óleo/bronze\",\n" +
                "        \"UrlArte\": \"https://cdn.culturagenial.com/imagens/clocks-cke.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"IDArte\": 5,\n" +
                "        \"NomeArte\": \"Meisje met de parel\",\n" +
                "        \"NomeArtista\": \"Johannes Vermeer\",\n" +
                "        \"AnoArte\": 1665,\n" +
                "        \"EstiloArte\": \"Pintura a óleo\",\n" +
                "        \"UrlArte\": \"https://artsandculture.google.com/asset/girl-with-a-pearl-earring/3QFHLJgXCmQm2Q?hl=pt-BR&avm=2\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"IDArte\": 6,\n" +
                "        \"NomeArte\": \"American Gothic\",\n" +
                "        \"NomeArtista\": \"Grant Wood\",\n" +
                "        \"AnoArte\": 1930,\n" +
                "        \"EstiloArte\": \"Pintura a óleo\",\n" +
                "        \"UrlArte\": \"https://artsandculture.google.com/asset/american-gothic/5QEPm0jCc183Aw?hl=pt-BR\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"IDArte\": 10,\n" +
                "        \"NomeArte\": \"Manhã de Primavera no Palácio Han\",\n" +
                "        \"NomeArtista\": \"Qiu Ying\",\n" +
                "        \"AnoArte\": 1552,\n" +
                "        \"EstiloArte\": \"Pergaminho\",\n" +
                "        \"UrlArte\": \"https://www.comuseum.com/wp-content/uploads/2015/10/qiu-ying_spring-morning-in-the-han-palace_part.jpg\"\n" +
                "    }\n" +
                "]";
        try {
            JSONArray artsArray = new JSONArray(arts);
            artInfo.putString("arts", arts);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return artInfo;
    }*/
    static void adicionaInfo(addArt addArt){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String artJSONString = null;
        try {
            Uri builtURI;
            if(addArt == null){
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
                    String artsAdd = gson.toJson(addArt);
                    // Fetch the value as a String
                    artsAdd = jsonReader.nextString();

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
            urlConnection.setRequestMethod("POST");
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