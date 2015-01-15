package com.example.heat_wave.photoviewer.models;

import android.graphics.drawable.Drawable;
/**
 * Created by heat_wave on 14.01.15.
 */

public class Photo {
    private String url;
    private Drawable drawable;

    public Photo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}