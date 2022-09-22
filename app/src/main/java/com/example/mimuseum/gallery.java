package com.example.mimuseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class gallery extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        nav = (BottomNavigationView) findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.mnLoc);
        nav.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
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