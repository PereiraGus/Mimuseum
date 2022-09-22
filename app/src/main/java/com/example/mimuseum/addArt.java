package com.example.mimuseum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addArt extends AppCompatActivity {

    EditText txtName;
    EditText txtArtist;
    EditText txtStyle;
    EditText txtYear;
    EditText txtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_art);

        txtName = (EditText) findViewById(R.id.addNome);
        txtArtist = (EditText) findViewById(R.id.addCre);
        txtStyle = (EditText) findViewById(R.id.addEsti);
        txtYear = (EditText) findViewById(R.id.addAno);
        txtUrl = (EditText) findViewById(R.id.addImg);
    }

    public void sendInfos(View view)
    {
        Toast toast = Toast.makeText(this,null,Toast.LENGTH_SHORT);

        Art art = new Art();
        art.NomeArte = txtName.getText().toString();
        art.NomeArtista = txtArtist.getText().toString();
        art.EstiloArte = txtStyle.getText().toString();
        art.AnoArte = Integer.parseInt(txtYear.getText().toString());
        art.UrlArte = txtUrl.getText().toString();

        try{
            networkUtils.addArt(art);
            toast.setText("Arte criada!");
        }
        catch (Exception exception){
            toast.setText("Não foi possível criar a arte definida.");
        }
        toast.show();
    }
}