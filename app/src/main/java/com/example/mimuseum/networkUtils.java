package com.example.mimuseum;

import android.net.Uri;
import android.os.Bundle;

import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class networkUtils {
    private static final String URL_API = "http://10.0.2.2:44369/api/Arte/getArtByID?";
    String artJSON = null;
    static String url = null;

    /*static Bundle accessArts(int id)
    {
        HttpURLConnection connec = null;
        BufferedReader reader = null;
        String artJSON = null;
        try {
            Uri buildURI = Uri.parse(URL_API).buildUpon()
                    .appendQueryParameter("id",String.valueOf(id))
                    .build();
            URL requestUrl = new URL(buildURI.toString());
            url = buildURI.toString();
            connec = (HttpURLConnection) requestUrl.openConnection();
            connec.setRequestMethod("GET");
            connec.connect();

            InputStream inputStream = connec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;
            while((line =reader.readLine()) != null)
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
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connec.disconnect();
        }
        Bundle artInfo = new Bundle();
        artInfo.putString("arts", artJSON);
        return artInfo;
    }*/
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
            arts = JsonPath.(json).get("arts.findAll { arts -> arts.IDArte = 1 }");
            artInfo.putString("arts", arts);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return artInfo;
    }
}