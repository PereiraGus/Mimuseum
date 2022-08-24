package com.example.mimuseum;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class fetchArt extends AsyncTaskLoader<Bundle> {
    private String query;
    fetchArt(Context context, String queryString){
        super(context);
        queryString = query;
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public Bundle loadInBackground() {
        return networkUtils.accessArts(query);
    }
}
