package com.example.mimuseum;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class fetchArt extends AsyncTaskLoader<Bundle> {
    String mType;
    String mQuery;
    fetchArt(Context context, String type, @Nullable String complement){
        super(context);
        mType = type;
        mQuery = complement;
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public Bundle loadInBackground() {
        if(mType == "id") {
            return networkUtils.getRandomArt();
        }
        if (mType == "name") {
            return networkUtils.getArtsByName(mQuery);
        }
        return null;
    }
}
