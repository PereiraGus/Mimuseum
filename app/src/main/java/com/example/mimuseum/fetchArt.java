package com.example.mimuseum;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class fetchArt extends AsyncTaskLoader<Bundle> {
    String type = null;
    fetchArt(Context context, String type){
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
        if(type == "id") {
            return networkUtils.getRandomArt();
        }
        return null;
    }
}
