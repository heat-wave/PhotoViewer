package com.example.heat_wave.photoviewer.tasks;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.heat_wave.photoviewer.ViewerActivity;
import com.example.heat_wave.photoviewer.models.Photo;

import java.io.InputStream;
import java.net.URL;

public class DownloadImagesTask extends AsyncTask<Photo, Void, Photo> {
    ViewerActivity activity;
    public static final String TAG = "500px";

    public DownloadImagesTask(ViewerActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Photo doInBackground(Photo... photos) {
        Photo photo = photos[0];
        try {
            String previewURL = photo.getThumbnailURL();
            String fullURL = photo.getFullURL();
            InputStream isFull = (InputStream) new URL(fullURL).getContent();
            InputStream isPreview = (InputStream) new URL(previewURL).getContent();
            String[] pathPreview = previewURL.split("/");
            String[] pathFull = fullURL.split("/");
            photo.setThumbnail(BitmapFactory.decodeStream(isPreview));
            photo.setFull(BitmapFactory.decodeStream(isFull));
            photo.setFull(BitmapFactory.decodeStream(new URL(fullURL).openConnection().getInputStream()));
            return photo;
        } catch (Exception e) {
            Log.e(TAG, "Download Failed.", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Photo photo) {
        super.onPostExecute(photo);
        //activity.onImageDownloaded(photo);
    }
}