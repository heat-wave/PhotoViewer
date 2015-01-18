package com.example.heat_wave.photoviewer;

import android.app.Activity;

/**
 * Created by heat_wave on 18.01.15.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.heat_wave.photoviewer.database.PhotoDatabaseHelper;

import java.util.ArrayList;

public class PhotoSlideActivity extends FragmentActivity {

    private static final int NUM_PAGES = 20;

    private ViewPager mPager;
    private LayoutInflater mLayoutInflater;
    private PagerAdapter mPagerAdapter;
    ArrayList<Bitmap> fullPhotos;
    PhotoDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_slide);

        db = new PhotoDatabaseHelper(this);
        fullPhotos = new ArrayList<Bitmap>();
        for (int i = 1; i <= 20; i++) {
            fullPhotos.add(db.getImageFull(i));
        }
        int position = getIntent().getIntExtra("POSITION", 0);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PhotoPagerAdapter(this);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
    }

    private class PhotoPagerAdapter extends PagerAdapter {
        Context context;

        PhotoPagerAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return fullPhotos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageBitmap(fullPhotos.get(position));
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}