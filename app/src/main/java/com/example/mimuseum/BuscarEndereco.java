package com.example.mimuseum;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BuscarEndereco extends AsyncTask<Location, Void, String[]> {
    private Context mContext;
    private OnTaskCompleted mListener;


    BuscarEndereco(Context applicationContext, OnTaskCompleted listener) {
        mContext = applicationContext;
        mListener = listener;
    }

    private final String TAG = BuscarEndereco.class.getSimpleName();

    @Override
    protected String[] doInBackground(Location... params) {
        // Set up the geocoder
        Geocoder geocoder = new Geocoder(mContext,
                Locale.getDefault());

        // Get the passed in location
        Location location = params[0];
        List<Address> addresses = null;
        String[] resultMessage = new String[3];
        resultMessage[0]="";
        resultMessage[1]="";
        resultMessage[2]="";

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address
                    1);
            resultMessage[1]=String.valueOf(location.getLatitude());
            resultMessage[2]=String.valueOf( location.getLongitude());
        } catch (IOException ioException) {
            // Catch network or other I/O problems
            resultMessage[0] = mContext.getString(R.string.servico_indisponivel);
            Log.e(TAG, resultMessage[0], ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values
            resultMessage[0] = mContext.getString(R.string.coordenada_invalida);
            Log.e(TAG, resultMessage[0] + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        // If no addresses found, print an error message.
        if (addresses == null || addresses.size() == 0) {
            if (resultMessage[0].isEmpty()) {
                resultMessage[0] = mContext.getString(R.string.sem_endereco);
                Log.e(TAG, resultMessage[0]);
            }
        } else {
            // If an address is found, read it into resultMessage
            Address address = addresses.get(0);
            ArrayList<String> addressParts = new ArrayList<>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressParts.add(address.getAddressLine(i));
            }

            resultMessage[0] = TextUtils.join(
                    "\n",
                    addressParts);

        }

        return resultMessage;
    }

    @Override
    protected void onPostExecute(String[] address) {
        mListener.onTaskCompleted(address);
        super.onPostExecute(address);
    }

    interface OnTaskCompleted {
        void onTaskCompleted(String[] result);
    }

}
