package com.example.mimuseum;

import android.net.Uri;
import android.os.Bundle;

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

    static Bundle accessArts(int id)
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
    }
}
