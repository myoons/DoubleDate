package com.example.madcamp_week_2.UI.Myinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.madcamp_week_2.UI.Gallery.ImageClickListener;
import com.example.madcamp_week_2.UI.Gallery.ImageInfo;

import java.util.ArrayList;

public class MyinfoGridViewAdapter extends BaseAdapter {

    Context context = null;
    // For Grid View
    ArrayList<Myinfo_image> imageIDs = null;


    public MyinfoGridViewAdapter(Context context, ArrayList<Myinfo_image> imageIDs) {
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
            bmp = Bitmap.createScaledBitmap(bmp, 320, 320, false);

            String date = imageIDs.get(position).getDate();
            String title = imageIDs.get(position).getTitle();
            String tag = imageIDs.get(position).getTag();
            String score = imageIDs.get(position).getScore();

            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageDrawable(new BitmapDrawable(context.getResources(),bmp));
            MyinfoImageClickListener myinfoImageClickListener = new MyinfoImageClickListener(context,bmp,title,date,tag,score);
            imageView.setOnClickListener(myinfoImageClickListener);
        }
        return imageView;
    }

}
