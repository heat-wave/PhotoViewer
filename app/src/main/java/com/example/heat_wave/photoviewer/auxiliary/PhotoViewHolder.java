package com.example.heat_wave.photoviewer.auxiliary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.heat_wave.photoviewer.PhotoSlideActivity;
import com.example.heat_wave.photoviewer.R;
import com.example.heat_wave.photoviewer.ViewerActivity;

/**
 * Created by heat_wave on 14.01.15.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {
    protected ImageView photo;

    public PhotoViewHolder(View v) {
        super(v);
        photo =  (ImageView) v.findViewById(R.id.photo);
    }
}