package com.example.mimuseum;

import android.net.Uri;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class networkUtils {
    private static final String URL_API = "https://othergreytrail96.conveyor.cloud/api/Arte";
    private static HttpURLConnection connec;
    private static BufferedReader reader;
    private static String artJSON;
    static String url = null;

    static HttpURLConnection startConnec(String methName, String parName, String parVal, String httpMeth) {
        HttpURLConnection connec = null;
        try {
            Uri buildURI = Uri.parse(URL_API).buildUpon()
                    .appendPath(methName)
                    .build();
            if (parName != null) {
                buildURI = Uri.parse(String.valueOf(buildURI)).buildUpon()
                        .appendQueryParameter(parName, parVal)
                        .build();
            }
            URL requestUrl = new URL(buildURI.toString());
            url = buildURI.toString();
            connec = (HttpURLConnection) requestUrl.openConnection();
            connec.setRequestMethod(httpMeth);
        } catch (Exception ex) {
        }
        return connec;
    }

    static int countArts() {
        int arts = 0;
        try {
            connec = networkUtils.startConnec("seeNumOfArts", null, null, "GET");
            InputStream inputStream = connec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            arts = Integer.parseInt(builder.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            connec.disconnect();
        }
        return arts;
    }

    static Bundle getRandomArt() {
        Bundle artBundle = new Bundle();

        Random generator = new Random();
        int randomID = generator.nextInt((networkUtils.countArts() - 1) + 1) + 1;

        try {
            connec = networkUtils.startConnec("getArtByID", "id", Integer.toString(randomID), "GET");
            InputStream inputStream = connec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("/n");
            }
            if (builder.length() == 0) {
                return null;
            }
            artJSON = builder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            connec.disconnect();
        }
        artBundle.putString("artData", artJSON);
        artBundle.putBoolean("isArray", false);
        return artBundle;
    }

    static Bundle getArtsByName(String name){
        Bundle artBundle = new Bundle();

        try {
            connec = networkUtils.startConnec("getArtsByName", "name", name, "GET");
            InputStream inputStream = connec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("/n");
            }
            if (builder.length() == 0) {
                return null;
            }
            artJSON = builder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            connec.disconnect();
        }
        artBundle.putString("artData", artJSON);
        artBundle.putBoolean("isArray", true);
        return artBundle;
    }
    static void addArt(Art art) {
        try {
            connec = networkUtils.startConnec("PostNewArt", null, null, "POST");
            connec.setDoOutput(true);

            OutputStream outputStream = connec.getOutputStream();
            outputStream.write(Byte.valueOf(art.toString()));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connec.disconnect();
        }
    }
}