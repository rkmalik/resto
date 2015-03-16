package com.example.rkmalik.resto;

/**
 * Created by rkmalik on 3/11/2015.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rkmalik.data.Restaurant;

import java.util.List;

public class customlistview extends ArrayAdapter<Bitmap>{

    private final Activity context;
    private final Bitmap[] images;

    public customlistview (Activity context, Bitmap[] images)//String [] web, Integer [] imageId)
    {
        super(context, R.layout.list_single, images);
        this.context = context;
        this.images = images;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        imageView.setImageBitmap(images[position]);

        return rowView;
    }

}
