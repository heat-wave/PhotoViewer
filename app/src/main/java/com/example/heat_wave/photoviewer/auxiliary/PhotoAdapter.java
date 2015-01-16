package com.example.heat_wave.photoviewer.auxiliary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.heat_wave.photoviewer.R;
import com.example.heat_wave.photoviewer.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heat_wave on 14.01.15.
 */
public class PhotoAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public PhotoAdapter(Context context, int layoutResourceId,
                        ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Bitmap item = (Bitmap) data.get(position);
        holder.image.setImageBitmap(item);
        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}