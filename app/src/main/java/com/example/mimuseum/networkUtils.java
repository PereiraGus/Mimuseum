package com.example.mimuseum;

import android.net.Uri;
import android.os.Bundle;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class networkUtils {
    private static final String URL_API = "https://localhost:44369/api";
    String artJSON = null;
    static String url = null;

    static Bundle accessArts(String query)
    {
        HttpURLConnection connec = null;
        BufferedReader reader = null;
        String artJSON = null;
        try {
            Uri buildURI = Uri.parse(URL_API).buildUpon()
                    .appendPath(query)
                    .build();
            URL requestUrl = new URL(buildURI.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        {

        }
    }
}
