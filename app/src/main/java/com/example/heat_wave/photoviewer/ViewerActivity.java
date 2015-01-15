package com.example.heat_wave.photoviewer;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.heat_wave.photoviewer.auxiliary.PhotoAdapter;
import com.example.heat_wave.photoviewer.tasks.DownloadImagesTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ViewerActivity extends ActionBarActivity {

    private RecyclerView photoView;
    private RecyclerView.Adapter photoAdapter;
    private RecyclerView.LayoutManager photoLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Debug.waitForDebugger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        photoView = (RecyclerView) findViewById(R.id.cardList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        photoView.setHasFixedSize(true);

        // use a linear layout manager
        photoLayoutManager = new LinearLayoutManager(this);
        photoView.setLayoutManager(photoLayoutManager);

        // specify an adapter (see also next example)


        ArrayList<Bitmap> photoList = new ArrayList<>();
        try {
            photoList = new DownloadImagesTask().execute("recent").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
}
