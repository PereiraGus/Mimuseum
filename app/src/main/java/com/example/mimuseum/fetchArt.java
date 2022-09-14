package com.example.mimuseum;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class fetchArt extends AsyncTaskLoader<Bundle> {
    Art art = new Art();
    fetchArt(Context context, Art art){
        super(context);
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public Bundle loadInBackground() {
        return networkUtils.accessArts(art.IDArte);
    }
}
