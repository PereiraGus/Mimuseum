package com.example.mimuseum;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class fetchArt extends AsyncTaskLoader<Bundle> {
    int id;
    fetchArt(Context context, int idArte){
        super(context);
        id = idArte;
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public Bundle loadInBackground() {
        return networkUtils.accessArts(id);
    }
}
