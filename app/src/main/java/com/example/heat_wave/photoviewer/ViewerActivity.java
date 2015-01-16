package com.example.heat_wave.photoviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.heat_wave.photoviewer.auxiliary.PhotoAdapter;
import com.example.heat_wave.photoviewer.tasks.DownloadImagesTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ViewerActivity extends ActionBarActivity {

    private GridView gridView;
    private PhotoAdapter customGridAdapter;
    Context context;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        context = this;

        gridView = (GridView) findViewById(R.id.gridView);
        try {
            customGridAdapter = new PhotoAdapter(this, R.layout.row_grid,
                    new DownloadImagesTask().execute().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(ViewerActivity.this, position + "#Selected",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        return super.onOptionsItemSelected(item);
    }

    public boolean swapContent(MenuItem item) {
        Debug.waitForDebugger();
/*        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Refresh");
                dialog.setIndeterminate(true);
                dialog.show();
            }
        });
        t.start();*/
        //setContentView(R.layout.splash_screen);
                /**/
        customGridAdapter.clear();
        try {
            customGridAdapter = (PhotoAdapter) gridView.getAdapter();
            customGridAdapter.clear();
            customGridAdapter.add(new DownloadImagesTask().execute().get());
            //customGridAdapter.notifyDataSetChanged();
            gridView.setAdapter(customGridAdapter);
            gridView.invalidateViews();
            gridView.scrollBy(0, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //dialog.dismiss();
        //setContentView(R.layout.activity_viewer);
        //return true;
        /*try {
            dialog.dismiss();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return true;
    }
}
