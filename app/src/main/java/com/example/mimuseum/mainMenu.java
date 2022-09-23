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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

public class mainMenu extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bundle>, BottomNavigationView.OnNavigationItemSelectedListener {

    Boolean isSearchByName = null;
    String query = null;

    private LinearLayout infos;
    private ImageView imgArt;
    private TextView addTit;
    private TextView addCre;
    private TextView addEsti;
    private TextView addAno;
    private TextView txtError;
    private EditText editSearch;

    DisplayImageOptions options = null;

    private BottomNavigationView nav;

    int artId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent checkIfSearch = getIntent();
        isSearchByName = checkIfSearch.getBooleanExtra("searchByName",false);
        query = checkIfSearch.getStringExtra("query");

        infos = (LinearLayout) findViewById(R.id.artLayout);
        imgArt = (ImageView) findViewById(R.id.imgArt);
        addTit = (TextView) findViewById(R.id.addTit);
        addCre = (TextView) findViewById(R.id.addCre);
        addEsti = (TextView) findViewById(R.id.addEsti);
        addAno = (TextView) findViewById(R.id.addAno);
        txtError = (TextView) findViewById(R.id.txtError);
        editSearch = (EditText) findViewById(R.id.artSearch);

        editSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent searchByName = new Intent(getApplicationContext(),mainMenu.class);
                    searchByName.putExtra("searchByName",true);
                    searchByName.putExtra("query",editSearch.getText());
                    startActivity(searchByName);
                }
                return false;
            }
        });

        //https://github.com/nostra13/Android-Universal-Image-Loader
        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        DisplayImageOptions options = new DisplayImageOptions
                .Builder()
                .showStubImage(R.drawable.loading)
                .showStubImage(R.drawable.unavailable)
                .cacheInMemory()
                .build();
        nav = (BottomNavigationView) findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.mnMain);
        nav.setOnNavigationItemSelectedListener(this);

        startFetching();
    }

    public void refreshArt(View view)
    {
        finish();
        startActivity(getIntent());
    }

    public void startFetching()
    {
        ConnectivityManager checkConnec = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netStts = null;
        if(checkConnec != null){
            netStts = checkConnec.getActiveNetworkInfo();
        }
        if(netStts != null && netStts.isConnected()){
            Bundle args = new Bundle();
            getSupportLoaderManager().initLoader(0,args,this);
        }
    }

    @NonNull
    @Override
    public Loader<Bundle> onCreateLoader(int id, @Nullable Bundle args) {
        Toast toast = Toast.makeText(this, "Carregando...", Toast.LENGTH_SHORT);
        toast.show();
        if (isSearchByName == false) {
            return new fetchArt(this,"id");
        }
        else if (isSearchByName == true){
            return new fetchArt(this,"name");
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bundle> loader, Bundle data) {
        Art art = new Art();
        try{
                Bundle allData = data;
                if(allData == null){
                    txtError.setVisibility(View.VISIBLE);
                    infos.setVisibility(View.GONE);
                }
                else {
                    JSONObject objArt = new JSONObject(data.getString("artData"));
                    art.IDArte = objArt.getInt("IDArte");
                    art.NomeArte = objArt.getString("NomeArte");
                    art.NomeArtista = objArt.getString("NomeArtista");
                    art.AnoArte = objArt.getInt("AnoArte");
                    art.EstiloArte = objArt.getString("EstiloArte");
                    art.UrlArte = objArt.getString("UrlArte");

                    addTit.setText(art.NomeArte);
                    addCre.setText(art.NomeArtista);
                    addAno.setText(Integer.toString(art.AnoArte));
                    addEsti.setText(art.EstiloArte);

                    ImageLoader imgLoader = ImageLoader.getInstance();
                    imgLoader.displayImage(art.UrlArte,imgArt,options);
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
            case R.id.mnLoc:
                Intent intentL = new Intent(getApplicationContext(), LocalizacaoActivity.class);
                startActivity(intentL);
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
    public void TelaGeoLoc(View view){
        Intent intent = new Intent(getApplicationContext(), LocalizacaoActivity.class);
        startActivity(intent);
    }

}