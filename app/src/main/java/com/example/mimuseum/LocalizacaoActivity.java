package com.example.mimuseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Date;

public class LocalizacaoActivity extends AppCompatActivity implements BuscarEndereco.OnTaskCompleted, BottomNavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    BottomNavigationView nav;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static String MAPS_URL = "https://www.google.com.br/maps/@LATITUDE,LONGITUDE,15z";
    private String MAP_LAT;
    private String MAP_LONG;
    private String MAP_ADRESS;

    private Button mLocationButton;
    private WebView mapWebView;
    private TextView mLocationTextView;
    private TextView txtTemp;
    private TextView txtTime;
    private Sensor tempSns;
    private SensorManager sensorMng;
    private Boolean isSensorAvailable;
    private String temperature = null;
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
        mLocationTextView = findViewById(R.id.artSearch);
        txtTemp = findViewById(R.id.locTemp);
        txtTime = findViewById(R.id.locTime);
        mapWebView = (WebView) findViewById(R.id.mapWebView);

        sensorMng = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorMng.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempSns = sensorMng.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isSensorAvailable = true;
        }
        else { isSensorAvailable = false; }
        if(isSensorAvailable){
            sensorMng.registerListener(this,tempSns,SensorManager.SENSOR_DELAY_NORMAL);
        }

        WebSettings webSttgs = mapWebView.getSettings();
        webSttgs.setJavaScriptEnabled(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);

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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        temperature = String.valueOf(sensorEvent.values[0]);
        txtTemp.setText(temperature + "°C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onClick(View view) {
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
            mLocationTextView.setText(MAP_ADRESS);
            String crrntTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) +
                    ":" +
                    Calendar.getInstance().get(Calendar.MINUTE);
            txtTime.setText(crrntTime);
            txtTemp.setText(temperature);
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
        if(isSensorAvailable){
            sensorMng.unregisterListener(this);
        }
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
