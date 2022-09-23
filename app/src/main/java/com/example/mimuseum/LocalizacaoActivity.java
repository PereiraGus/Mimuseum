package com.example.mimuseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class LocalizacaoActivity extends AppCompatActivity implements BuscarEndereco.OnTaskCompleted, BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView nav;

    public static final String PREFERENCIAS_NAME = "com.example.android.localizacao";
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String LASTDATE_KEY = "data";
    private static String MAPS_URL = "https://www.google.com.br/maps/@LATITUDE,LONGITUDE,15z";
    private String MAP_LAT;
    private String MAP_LONG;
    private String MAP_ADRESS;

    private Button mLocationButton;
    private WebView mapWebView;
    private TextView mLocationTextView;
    private static final String LASTADRESS_KEY = "adress";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    private boolean mTrackingLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);

        nav = (BottomNavigationView) findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.mnLoc);
        nav.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);

        mLocationButton = findViewById(R.id.btnLocalizacao);
        mLocationTextView = findViewById(R.id.edtxtLocalizacao);
        mapWebView = (WebView) findViewById(R.id.mapWebView);

        WebSettings webSttgs = mapWebView.getSettings();
        webSttgs.setJavaScriptEnabled(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mTrackingLocation) {
                    new BuscarEndereco(LocalizacaoActivity.this, LocalizacaoActivity.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };
    }

    public void onClickTest(View view) {
        if (!mTrackingLocation) {
            startTrackingLocation();
        } else {
            stopTrackingLocation();
        }
    }

    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    REQUEST_LOCATION_PERMISSION
            );
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);

            mLocationTextView.setText(getString(R.string.endereco,
                    getString(R.string.loading), null, null,
                    System.currentTimeMillis()));
        }
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
            mLocationButton.setText(R.string.iniciar_busca);
            mLocationTextView.setText(R.string.hint_busca);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation();
                } else {
                    Toast.makeText(this,
                            R.string.permissao_recusada,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onTaskCompleted(String[] result) {
        if (mTrackingLocation) {
            MAP_LAT = result[1];
            MAP_LONG = result[2];
            MAP_ADRESS = result[0];
            mLocationTextView.setText(getString(R.string.endereco,
                    MAP_ADRESS, MAP_LAT, MAP_LONG, System.currentTimeMillis()));
            updateMap(MAP_LAT, MAP_LONG);
            mTrackingLocation = false;
        }
    }

    public void updateMap(String lat, String lng)
    {
        try {
            String uri = MAPS_URL.replace("LATITUDE",MAP_LAT);
            uri = uri.replace("LONGITUDE",MAP_LONG);
            mapWebView.loadUrl(uri);
        }
        catch (Exception exception)
        {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTrackingLocation) {
            stopTrackingLocation();
            mTrackingLocation = true;
        }
    }

    @Override
    protected void onResume() {
        if (mTrackingLocation) {
            startTrackingLocation();

        }
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnMain:
                Intent intentM = new Intent(getApplicationContext(), mainMenu.class);
                startActivity(intentM);
                return true;
            case R.id.mnLoc:
                Intent intentL = new Intent(getApplicationContext(), LocalizacaoActivity.class);
                startActivity(intentL);
                return true;
            case R.id.mnNew:
                Intent intentN = new Intent(getApplicationContext(), addArt.class);
                startActivity(intentN);
                return true;
            case R.id.mnOut:
                Intent intentO = new Intent(getApplicationContext(), signin.class);
                startActivity(intentO);
                return true;
        }
        return false;
    }
}
