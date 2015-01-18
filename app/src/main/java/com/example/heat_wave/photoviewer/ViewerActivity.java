package com.example.heat_wave.photoviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.heat_wave.photoviewer.auxiliary.PhotoAdapter;
import com.example.heat_wave.photoviewer.auxiliary.SpacesItemDecoration;
import com.example.heat_wave.photoviewer.database.PhotoDatabaseHelper;
import com.example.heat_wave.photoviewer.models.Photo;
import com.example.heat_wave.photoviewer.tasks.DownloadImagesTask;
import com.example.heat_wave.photoviewer.tasks.FiveHundredSearchTask;

import java.util.ArrayList;

public class ViewerActivity extends ActionBarActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView photoView;
    private RecyclerView.Adapter photoAdapter;
    private RecyclerView.LayoutManager photoLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Bitmap> thumbnailList;
    private ArrayList<Bitmap> fullList;
    PhotoDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new PhotoDatabaseHelper(this);
        setContentView(R.layout.activity_viewer);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        photoView = (RecyclerView) findViewById(R.id.cardList);

        photoView.setHasFixedSize(true);

        photoLayoutManager = new GridLayoutManager(this, 3);
        photoView.setLayoutManager(photoLayoutManager);

        thumbnailList = new ArrayList<Bitmap>();
        fullList = new ArrayList<Bitmap>();

        if (db.getPhotosCount() == 0)
            new FiveHundredSearchTask(this).execute();
        for (int i = 1; i <= 20; i++) {
            thumbnailList.add(db.getImageThumbnail(i));
        }
        photoView.addItemDecoration(new SpacesItemDecoration(30));
        PhotoAdapter photoAdapter = new PhotoAdapter(thumbnailList);
        photoView.setAdapter(photoAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSearchFinished(ArrayList<Photo> photos) {
        for (Photo photo : photos) {
            new DownloadImagesTask(this).execute(photo);
        }
    }

    public void onImageDownloaded(Photo photo) {
        db.addPhoto(photo);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        db.onUpgrade(db.getWritableDatabase(), 1, 2);
        thumbnailList.clear();
        new FiveHundredSearchTask(this).execute();
        for (int i = 1; i <= 20; i++) {
            thumbnailList.add(db.getImageThumbnail(i));
        }
        photoAdapter = new PhotoAdapter(thumbnailList);
        photoAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }

    public void watchFull(View v) {
        ImageView photo = (ImageView) v;
        Bitmap compare =((BitmapDrawable)photo.getDrawable()).getBitmap();
        int pos = 0;
        for (int i = 0; i < 20; i++) {
            if (compare.equals(thumbnailList.get(i))) {
                pos = i;
                break;
            }
        }
        Intent i = new Intent(this, PhotoSlideActivity.class);
        i.putExtra("POSITION", pos);
        startActivity(i);
    }
}
