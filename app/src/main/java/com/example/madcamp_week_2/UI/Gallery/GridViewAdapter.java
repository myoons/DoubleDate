package com.example.madcamp_week_2.UI.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.madcamp_week_2.R;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    Context context = null;
    // For Grid View
    ArrayList<ImageInfo> imageIDs = null;


    public GridViewAdapter(Context context, ArrayList<ImageInfo> imageIDs) {
        this.context = context;
        this.imageIDs = imageIDs;
    }

    public int getCount() {
        if (null != imageIDs)
            return imageIDs.size();
        else
            return 0;
    }

    public Object getItem(int position) {
        if (null != imageIDs)
            return imageIDs.get(position);
        else
            return 0;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = null;

        if (null != convertView) {
            imageView = (ImageView) convertView;
        } else {

            Bitmap bmp = imageIDs.get(position).getImage();
            String title = imageIDs.get(position).getTitle();
            String name = imageIDs.get(position).getName();

            bmp = Bitmap.createScaledBitmap(bmp, 320, 320, false);

            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageDrawable(new BitmapDrawable(context.getResources(),bmp));
            ImageClickListener imageViewClickListener = new ImageClickListener(context, bmp, title, name);
            imageView.setOnClickListener(imageViewClickListener);
        }
        return imageView;
    }

}
