package com.example.keepitall;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class PhotoGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Uri> photos;
    private LayoutInflater inflater;

    public PhotoGridAdapter(Context context, ArrayList<Uri> photos) {
        this.context = context;
        this.photos = photos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Uri getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.image_grid, parent, false);

        ImageView photoImageView = convertView.findViewById(R.id.imagePhoto);

        Uri photo = getItem(position);
        if (photo != null) {
            photoImageView.setImageURI(photo);
        }

        return convertView;
    }

}



