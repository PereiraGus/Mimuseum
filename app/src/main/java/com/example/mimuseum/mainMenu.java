package com.example.mimuseum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class mainMenu extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bundle> {

    private TextView addTit;
    private TextView addCre;
    private TextView addEsti;
    private TextView addAno;

    int artId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addTit = (TextView) findViewById(R.id.addTit);
        addCre = (TextView) findViewById(R.id.addCre);
        addEsti = (TextView) findViewById(R.id.addEsti);
        addAno = (TextView) findViewById(R.id.addAno);

        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
    }

    @NonNull
    @Override
    public Loader<Bundle> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bundle> loader, Bundle data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bundle> loader) {

    }

    public void fetchArts()
    {
        ConnectivityManager checkConnec = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netStts = null;
        if (checkConnec != null){
            getSupportLoaderManager().restartLoader(0,null,this);
        }
        else {
            //PARA FAZER
        }
    }
}