package com.example.heat_wave.photoviewer;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.heat_wave.photoviewer.auxiliary.PhotoAdapter;
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
    private ArrayList<Bitmap> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        Debug.waitForDebugger();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        photoView = (RecyclerView) findViewById(R.id.cardList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        photoView.setHasFixedSize(true);

        // use a linear layout manager
        photoLayoutManager = new GridLayoutManager(this, 3);
        photoView.setLayoutManager(photoLayoutManager);

        // specify an adapter (see also next example)


        photoList = new ArrayList<>();
        new FiveHundredSearchTask(ViewerActivity.this).execute();
        photoAdapter = new PhotoAdapter(photoList);
        photoView.setAdapter(photoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSearchFinished(ArrayList<Photo> photos) {
        for (Photo photo : photos) {
            new DownloadImagesTask(ViewerActivity.this).execute(photo);
        }
    }

    public void onImageDownloaded(Photo photo) {
        photoList.add(photo.getThumbnail());
    }

    @Override
    public void onRefresh() {
        //Toast.makeText(this, R.string.refresh_started, Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                // говорим о том, что собираемся закончить
                //Toast.makeText(MainActivity.this, R.string.refresh_finished, Toast.LENGTH_SHORT).show();
            }
        }, 300);
    }
}
