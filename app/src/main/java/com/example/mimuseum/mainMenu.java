package com.example.mimuseum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class mainMenu extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bundle>, BottomNavigationView.OnNavigationItemSelectedListener {

    private LinearLayout infos;
    private TextView addTit;
    private TextView addCre;
    private TextView addEsti;
    private TextView addAno;
    private TextView txtError;

    private BottomNavigationView nav;

    int artId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        infos = (LinearLayout) findViewById(R.id.artLayout);
        addTit = (TextView) findViewById(R.id.addTit);
        addCre = (TextView) findViewById(R.id.addCre);
        addEsti = (TextView) findViewById(R.id.addEsti);
        addAno = (TextView) findViewById(R.id.addAno);
        txtError = (TextView) findViewById(R.id.txtError);

        nav = (BottomNavigationView) findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.mnMain);
        nav.setOnNavigationItemSelectedListener(this);

        /*if(getSupportLoaderManager().getLoader(0) == null){
            getSupportLoaderManager().initLoader(0,null,this);
        }*/
        fetchArts();
    }

    public void fetchArts()
    {
        ConnectivityManager checkConnec = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netStts = null;
        if(checkConnec != null){
            netStts = checkConnec.getActiveNetworkInfo();
        }
        if(netStts != null && netStts.isConnected()){
            getSupportLoaderManager().restartLoader(0,null,this);
        }
    }

    @NonNull
    @Override
    public Loader<Bundle> onCreateLoader(int id, @Nullable Bundle args) {
        Art art = new Art();
        art.IDArte = 1;
        return new fetchArt(this, art.IDArte);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bundle> loader, Bundle data) {
        Art art = new Art();
        try{
                Bundle allData = data;
                if(allData.getString("arts") == null){
                    txtError.setVisibility(View.VISIBLE);
                    infos.setVisibility(View.GONE);
                }
                else {
                    JSONArray artsData = new JSONArray(data.getString("arts"));
                    JSONObject objArt = artsData.getJSONObject(1);
                    art.IDArte = objArt.getInt("IDArte");
                    art.NomeArte = objArt.getString("NomeArte");
                    art.NomeArtista = objArt.getString("NomeArtista");
                    art.AnoArte = objArt.getInt("AnoArte");
                    art.EstiloArte = objArt.getString("EstiloArte");
                    art.UrlArte = objArt.getString("UrlArte");

                    addTit.setText(art.NomeArte);
                    addCre.setText(art.NomeArtista);
                    addAno.setText(art.AnoArte);
                    addEsti.setText(art.EstiloArte);
                }
            }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<Bundle> loader) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnMain:
                return true;
            case R.id.mnGallery:
                Intent intentG = new Intent(getApplicationContext(), gallery.class);
                startActivity(intentG);
                return true;
            case R.id.mnNew:
                Intent intentN = new Intent(getApplicationContext(), addArt.class);
                startActivity(intentN);
                return  true;
            case R.id.mnOut:
                Intent intentO = new Intent(getApplicationContext(), signin.class);
                startActivity(intentO);
                return true;
        }
        return false;
    }
}